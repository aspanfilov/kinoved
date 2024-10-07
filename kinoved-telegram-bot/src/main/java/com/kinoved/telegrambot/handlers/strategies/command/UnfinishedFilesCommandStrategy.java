package com.kinoved.telegrambot.handlers.strategies.command;

import com.kinoved.common.enums.MovieFileStatus;
import com.kinoved.common.filemanager.dtos.MovieFileInfoDto;
import com.kinoved.telegrambot.client.KinovedCoreClient;
import com.kinoved.telegrambot.senders.MessageSender;
import com.kinoved.telegrambot.utils.MessageBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.kinoved.telegrambot.constants.Constants.COMMAND_UNFINISHED_FILES;

@Component(COMMAND_UNFINISHED_FILES)
@RequiredArgsConstructor
public class UnfinishedFilesCommandStrategy implements CommandStrategy {

    private final KinovedCoreClient kinovedCoreClient;

    private final MessageBuilder messageBuilder;

    private final MessageSender messageSender;

    @Override
    public void handleCommand(Long chatId, Long userId) {
        List<MovieFileStatus> unfinishedStatuses = List.of(
                MovieFileStatus.NEW,
                MovieFileStatus.MATCHED,
                MovieFileStatus.REJECTED,
                MovieFileStatus.MOVE_ERROR);

        List<MovieFileInfoDto> movieFileInfoDtos = kinovedCoreClient.getMovieFilesInfo(unfinishedStatuses);

        messageSender.sendMessage(
                chatId,
                messageBuilder.getUnfinishedFilesMsg(movieFileInfoDtos));
    }
}
