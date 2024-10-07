package com.kinoved.filemanager.handlers.impl;

import com.kinoved.common.filemanager.dtos.MovieFileMoveTask;
import com.kinoved.filemanager.exceptions.UnsupportedMessageTypeException;
import com.kinoved.filemanager.handlers.FileTaskHandler;
import com.kinoved.filemanager.handlers.MessageHandler;
import com.kinoved.common.kafka.messages.TaskKafkaMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MessageHandlerImpl implements MessageHandler {

    private final FileTaskHandler fileTaskHandler;

    @Override
    public void processMessage(TaskKafkaMessage msg) {

        switch (msg.getMessageType()) {
            case MOVIE_FILE_MOVE_TASK -> handleMovieFileMoveTask(msg);
            default -> throw new UnsupportedMessageTypeException(
                    "task kafka message unsupported message type: " + msg.getMessageType());
        }
    }

    private void handleMovieFileMoveTask(TaskKafkaMessage msg) {
        MovieFileMoveTask task = (MovieFileMoveTask) msg.getBody();
        fileTaskHandler.moveFileToGenreDirectory(task);
    }

}
