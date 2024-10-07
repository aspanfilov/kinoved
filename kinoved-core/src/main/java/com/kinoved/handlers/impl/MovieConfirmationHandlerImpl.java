package com.kinoved.handlers.impl;

import com.kinoved.clients.KinopoiskClient;
import com.kinoved.clients.KinovedTelegramBotClient;
import com.kinoved.common.telegram.dtos.ConfirmMovieIdRequestDto;
import com.kinoved.common.telegram.dtos.ConfirmMovieIdResponseDto;
import com.kinoved.common.filemanager.dtos.MovieFileInfoDto;
import com.kinoved.common.filemanager.dtos.MovieFileMoveTask;
import com.kinoved.enums.MovieFileStatus;
import com.kinoved.exceptions.EntityNotFoundException;
import com.kinoved.handlers.MovieConfirmationHandler;
import com.kinoved.kafka.MessageSender;
import com.kinoved.kafka.TaskKafkaMessageFabric;
import com.kinoved.mappers.MovieFileInfoMapper;
import com.kinoved.models.MovieData;
import com.kinoved.models.MovieFileInfo;
import com.kinoved.services.MovieDataLoaderService;
import com.kinoved.services.MovieDataService;
import com.kinoved.services.MovieFileInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MovieConfirmationHandlerImpl implements MovieConfirmationHandler {

    private final MovieFileInfoService movieFileInfoService;

    private final MovieFileInfoMapper movieFileInfoMapper;

    private final MovieDataService movieDataService;

    private final MovieDataLoaderService movieDataLoaderService;

    private final KinopoiskClient kinopoiskClient;

    private final KinovedTelegramBotClient kinovedTelegramBotClient;

    private final MessageSender messageSender;

    private final TaskKafkaMessageFabric taskKafkaMessageFabric;

    @Override
    public void saveAndRequestMovieIdConfirmation(MovieFileInfoDto movieFileInfoDto) {

        var movieFileInfo = movieFileInfoMapper.toNewEntity(movieFileInfoDto);
        var savedMovieFileInfo = movieFileInfoService.insert(movieFileInfo);

        var searchMovieResponseDto = kinopoiskClient.searchMovie(
                getMovieTitleAndYear(movieFileInfoDto),
                1, 10);

        var movieIdConfirmationRequestDto = new ConfirmMovieIdRequestDto(
                getMovieTitleAndYear(movieFileInfoDto),
                searchMovieResponseDto.getDocs(),
                movieFileInfoMapper.toDto(savedMovieFileInfo));
        kinovedTelegramBotClient.sendMovieIdConfirm(movieIdConfirmationRequestDto);
    }

    @Override
    public void handleMovieIdConfirmationResponse(ConfirmMovieIdResponseDto confirmMovieIdResponseDto) {

        var movieKpDevId = confirmMovieIdResponseDto.getMovieKpDevId();
        var movieFileInfoId = confirmMovieIdResponseDto.getMovieFileInfoId();
        var isConfirmed = confirmMovieIdResponseDto.isConfirmed();

        var movieFileInfo = movieFileInfoService.findById(movieFileInfoId)
                .orElseThrow(() -> new EntityNotFoundException("MovieFileInfo with id %s not found"
                        .formatted(movieFileInfoId)));

        if (!isConfirmed) {
            movieFileInfo.setStatus(MovieFileStatus.REJECTED);
            movieFileInfoService.update(movieFileInfo);
            return;
        }

        //Если загружается фильм по ранее загруженному описанию.
        //todo сделать в описании фильма ссылку на файл чтобы выводить наименование файла в полном представлении
        var movieData = movieDataService.getMovieByKpDevId(movieKpDevId)
                .orElseGet(() -> movieDataLoaderService.loadAndSaveMovieDataByKpDevId(movieKpDevId));

        movieFileInfoService.updateMatchMovieData(movieFileInfo, movieData);

        sendMovieFileMoveTask(movieFileInfo, movieData);
    }

    private String getMovieTitleAndYear(MovieFileInfoDto movieFileInfoDto) {
        return String.join(" ",
                movieFileInfoDto.getTitle(),
                movieFileInfoDto.getYear());
    }

    private void sendMovieFileMoveTask(MovieFileInfo movieFileInfo, MovieData movieData) {
        var movieFileMoveTask = MovieFileMoveTask.builder()
                .movieFileInfoId(movieFileInfo.getId())
                .fileName(movieFileInfo.getFileName())
                .genre(movieData.getGenres().stream().findFirst().orElse(""))
                .build();

        var taskMessage = taskKafkaMessageFabric.createMessage(movieFileMoveTask);
        messageSender.send(taskMessage);
    }

}
