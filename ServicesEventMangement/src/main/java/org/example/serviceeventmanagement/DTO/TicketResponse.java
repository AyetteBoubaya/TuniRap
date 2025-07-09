package org.example.serviceeventmanagement.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.serviceeventmanagement.Entity.Ticket;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TicketResponse {
    private Ticket ticket;
    private UserDTO user;

    @Override
    public String toString() {
        return "TicketResponse{" +
                "ticket=" + ticket +
                ", user=" + user +
                '}';
    }

}



