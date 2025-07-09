package org.example.serviceeventmanagement.Repository;

import org.example.serviceeventmanagement.Entity.Cart;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface CartRepository extends MongoRepository<Cart, String> {
    Optional<Cart> findByUserIdAndIsCheckedOutFalse(Long userId);
}

