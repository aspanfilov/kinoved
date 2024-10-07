package com.kinoved.common.filemanager.dtos;

import com.kinoved.common.kafka.MessageBody;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MovieFileMoveTask implements MessageBody {
    private String movieFileInfoId;
    private String fileName;
    private String genre;
}
