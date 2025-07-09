package com.tuniRap.notifications.dto.response;

import java.util.Date;

public record NotificationResponse(
        Long id,
        String recipientId,
        String title,
        String content,
        String type,
        String status,
        Date createdDate,
        Date readAt,
        String relatedObjectId
) {
}
