package com.tuniRap.collaborationManagement.service;

import com.tuniRap.collaborationManagement.model.Collaboration;
import com.tuniRap.collaborationManagement.model.Message;
import com.tuniRap.collaborationManagement.model.enums.Type;

import java.util.List;
import java.util.Optional;

public interface MessageService {

    Message sendMessage(Long collaborationId, Long senderId, String content);
    List<Message> getMessageByCollaboration(Long collaborationId);
    List<Message> getRecentMessages(Long collaborationId, int limit);

}
