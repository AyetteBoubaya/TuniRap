package com.tuniRap.collaborationManagement.controller;


import com.tuniRap.collaborationManagement.dto.request.CollaborationRequest;
import com.tuniRap.collaborationManagement.model.Collaboration;
import com.tuniRap.collaborationManagement.service.CollaborationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("collaborationChat")
@RequiredArgsConstructor
public class CollaborationController {
    private final CollaborationService collaborationService;

    @PostMapping("Collaboration")
    public ResponseEntity<Collaboration> createCollaboration(@RequestBody CollaborationRequest request) {
        Collaboration collaboration= collaborationService.createCollaborationRequest(
                request.senderId(), request.receiverId(), request.collaborationType() ,
                request.subject() , request.description()
        );
        return ResponseEntity.ok(collaboration);
    }

    @PutMapping("/{id}/response")
    public ResponseEntity<Collaboration> respond(@PathVariable Long id ,
                                                 @RequestParam boolean accepted ,
                                                 @RequestParam(required = false) String message){
        return  ResponseEntity.ok(collaborationService.respondToCollaboration(id, accepted, message));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Collaboration> getCollaboration(@PathVariable Long id){
        return collaborationService.getCollaboration(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
