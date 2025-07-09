package com.tuniRap.collaborationManagement.controller;

import com.tuniRap.collaborationManagement.model.Message;
import com.tuniRap.collaborationManagement.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("tunirap/collaborationChat/messages")
@RequiredArgsConstructor
public class MessageController {
    private final MessageService messageService;

    @PostMapping
    public ResponseEntity<Message> sendMessage(@RequestParam Long collaborationId,
                                               @RequestParam Long senderId ,
                                               @RequestParam String content) {
        return ResponseEntity.ok(messageService.sendMessage(collaborationId, senderId, content));
    }

    @GetMapping("/{collaborationId}")
    public ResponseEntity<List<Message>> getAll(@PathVariable Long collaborationId){
        return ResponseEntity.ok(messageService.getMessageByCollaboration(collaborationId));
    }

    @GetMapping("/{collaborationId}/recent")
    public ResponseEntity<List<Message>> getRecent(@PathVariable Long collaborationId,
                                                   @RequestParam(defaultValue = "5") int limit) {
        return ResponseEntity.ok(messageService.getRecentMessages(collaborationId, limit));
    }
}
