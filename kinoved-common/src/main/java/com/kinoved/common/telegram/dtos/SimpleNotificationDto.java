package com.kinoved.common.telegram.dtos;

import com.kinoved.common.telegram.enums.NotificationType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SimpleNotificationDto {
    private String message;
    private NotificationType notificationType;
    private Integer replyToMessageId;
}
