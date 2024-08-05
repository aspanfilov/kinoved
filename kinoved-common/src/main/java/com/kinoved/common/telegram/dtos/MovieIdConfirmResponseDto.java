package com.kinoved.common.telegram.dtos;

import com.kinoved.common.telegram.enums.ConfirmationStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MovieIdConfirmResponseDto {
    private Long movieKpDevId;
    private String movieFileInfoId;
    private ConfirmationStatus confirmationStatus;
    private Integer messageId;
}
