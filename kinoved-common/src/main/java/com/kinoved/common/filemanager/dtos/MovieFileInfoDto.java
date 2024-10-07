package com.kinoved.common.filemanager.dtos;

import com.kinoved.common.kafka.MessageBody;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MovieFileInfoDto implements MessageBody {
    private String id;
    private String fileName;
    private String status;
    private String title;
    private String translatedTitle;
    private String year;
    private String resolution;
    private Integer fileSizeInMb;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
