package uz.perevods.perevod.repository.messaging;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.perevods.perevod.entitiy.messaging.MessagingPermission;

import java.util.Optional;

@Repository
public interface MessagingPermissionRepository extends JpaRepository<MessagingPermission, String> {
    Optional<MessagingPermission> findByUserId(String id);
}
