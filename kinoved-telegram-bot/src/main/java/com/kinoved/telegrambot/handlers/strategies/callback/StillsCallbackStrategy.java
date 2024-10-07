package com.kinoved.telegrambot.handlers.strategies.callback;

import com.kinoved.common.enums.kinopoisk.KinopoiskImageType;
import com.kinoved.common.telegram.dtos.MovieImageDto;
import com.kinoved.telegrambot.client.KinovedCoreClient;
import com.kinoved.telegrambot.config.AppProps;
import com.kinoved.telegrambot.converters.MovieDataMapper;
import com.kinoved.common.telegram.dtos.MovieDto;
import com.kinoved.telegrambot.senders.MessageSender;
import com.kinoved.telegrambot.utils.CallbackDataUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.media.InputMediaPhoto;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.kinoved.telegrambot.constants.Constants.CALLBACK_STILLS;

@Component(CALLBACK_STILLS)
@RequiredArgsConstructor
public class StillsCallbackStrategy implements CallbackStrategy {

    private static final int MAX_IMAGE_SIZE = 10 * 1024 * 1024;

    private final MessageSender messageSender;

    private final KinovedCoreClient kinovedCoreClient;

    private final MovieDataMapper movieMapper;

    private final CallbackDataUtil callbackDataUtil;

    private final AppProps appProps;

    @Override
    public void handleCallback(Long chatId, CallbackQuery callbackQuery) {
        var msgId = callbackQuery.getMessage().getMessageId();

        String kpDevId = callbackDataUtil.getParamFromCallbackData(callbackQuery.getData(), 1);

        List<InputMediaPhoto> photos = getFilteredMediaPhotos(kpDevId);

        if (photos.isEmpty()) {
            messageSender.sendReplyMessage(chatId, msgId, "Кадры по данному фильму отсутствуют");
            return;
        }
        setCaptionForMedias(kpDevId, photos);
        messageSender.sendReplyPhotos(chatId, msgId, photos);
    }

    private List<InputMediaPhoto> getFilteredMediaPhotos(String kpDevId) {
        List<MovieImageDto> movieImageDtos = kinovedCoreClient.getMovieImages(
                Long.parseLong(kpDevId),
                List.of(KinopoiskImageType.STILL.getType(),
                        KinopoiskImageType.SCREENSHOT.getType()));

        Set<String> processedIds = new HashSet<>();

        List<InputMediaPhoto> medias = new java.util.ArrayList<>(movieImageDtos.stream()
                .filter(this::isImageSizeValid)
                .peek(dto -> processedIds.add(dto.getExternalId()))
                .map(dto -> new InputMediaPhoto(dto.getUrl()))
                .limit(appProps.getMaxImagesPerMessage())
                .toList());

        return fillToMaxCapacityByPreview(medias, movieImageDtos, processedIds, appProps.getMaxImagesPerMessage());
    }

    private List<InputMediaPhoto> fillToMaxCapacityByPreview(
            List<InputMediaPhoto> medias,
            List<MovieImageDto> movieImageDtos,
            Set<String> processedIds,
            int maxImages) {

        if (medias.size() < maxImages) {
            List<InputMediaPhoto> additionalMedias = movieImageDtos.stream()
                    .filter(dto -> !processedIds.contains(dto.getExternalId()))
                    .map(dto -> new InputMediaPhoto(dto.getPreviewUrl()))
                    .limit(maxImages - medias.size())
                    .toList();

            medias.addAll(additionalMedias);
        }

        return medias;
    }


    private boolean isImageSizeValid(MovieImageDto dto) {
        return dto.getHeight() * dto.getWidth() < MAX_IMAGE_SIZE;
    }

    private void setCaptionForMedias(String kpDevId, List<InputMediaPhoto> mediaPhotos) {
        MovieDto movieDto = kinovedCoreClient.getMovieById(kpDevId);
        String shortOverview = movieMapper.convertToShortOverview(movieDto);
        mediaPhotos.get(0).setCaption(shortOverview);
    }
}
