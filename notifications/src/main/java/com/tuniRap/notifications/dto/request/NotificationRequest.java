package com.tuniRap.notifications.dto.request;

import jakarta.validation.constraints.NotBlank;

public record NotificationRequest(
        @NotBlank String recipientId,
        @NotBlank String title,
        @NotBlank String content,
        @NotBlank String type,
        String relatedObjectId
) {}
