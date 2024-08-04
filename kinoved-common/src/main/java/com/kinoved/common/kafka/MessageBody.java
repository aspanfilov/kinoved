package com.kinoved.common.kafka;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.kinoved.common.filemanager.dtos.MovieFileInfoDto;
import com.kinoved.common.filemanager.dtos.MovieFileMoveResult;
import com.kinoved.common.filemanager.dtos.MovieFileMoveTask;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = MovieFileInfoDto.class, name = "MOVIE_FILE_INFO_DTO"),
        @JsonSubTypes.Type(value = MovieFileMoveResult.class, name = "MOVIE_FILE_MOVE_RESULT"),
        @JsonSubTypes.Type(value = MovieFileMoveTask.class, name = "MOVIE_FILE_MOVE_TASK")
})
public interface MessageBody {
}
