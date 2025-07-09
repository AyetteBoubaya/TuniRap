package com.tuniRap.collaborationManagement.service.implementation;

import com.tuniRap.collaborationManagement.exception.AlreadyExistException;
import com.tuniRap.collaborationManagement.model.Collaboration;
import com.tuniRap.collaborationManagement.model.Message;
import com.tuniRap.collaborationManagement.model.enums.Status;
import com.tuniRap.collaborationManagement.repository.CollaborationRepository;
import com.tuniRap.collaborationManagement.repository.MessageRepository;
import com.tuniRap.collaborationManagement.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MessageImpl implements MessageService {

    private final MessageRepository messageRepository;
    private final CollaborationRepository collaborationRepository;

    @Override
    public Message sendMessage(Long collaborationId, Long senderId, String content) {
        Collaboration collaboration = collaborationRepository.getCollaborationById(collaborationId);
        if (collaboration == null) {
            throw new AlreadyExistException("Collaboration not found");
        }

        if (collaboration.getCollaborationStatus() != Status.ACCEPTED) {
            throw new AlreadyExistException("Collaboration is not accepted");
        }

        Message message = new Message();
        message.setCollaboration(collaboration);
        message.setSenderId(senderId);
        message.setContent(content);
        message.setTimestamp(LocalDateTime.now());

        return messageRepository.save(message);
    }

    @Override
    public List<Message> getMessageByCollaboration(Long collaborationId) {
        return messageRepository.findByCollaboration_IdOrderByTimestampAsc(collaborationId);
    }

    @Override
    public List<Message> getRecentMessages(Long collaborationId, int limit) {
        Pageable pageable = PageRequest.of(0, limit, Sort.by(Sort.Direction.DESC, "timestamp"));
        return messageRepository.findByCollaboration_Id(collaborationId, pageable).getContent();
    }
}
