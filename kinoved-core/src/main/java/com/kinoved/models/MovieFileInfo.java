package com.kinoved.models;

import com.kinoved.enums.MovieFileStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "movie_files_info")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MovieFileInfo {
    @Id
    private String id;
    private String fileName;
    private MovieFileStatus status;
    private String title;
    private String translatedTitle;
    private String year;
    private String resolution;
    private Integer fileSizeInMb;
    @DBRef
    private MovieData movieData;
    @CreatedDate
    private LocalDateTime createdAt;
    @LastModifiedDate
    private LocalDateTime updatedAt;
}
