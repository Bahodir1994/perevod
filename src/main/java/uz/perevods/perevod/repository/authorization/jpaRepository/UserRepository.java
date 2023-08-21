package uz.perevods.perevod.repository.authorization.jpaRepository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.perevods.perevod.entitiy.authorization.User;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

    User findByUsername(String username);

    List<User> findByLocationAndPostAndRole(String userLocation, String userPost, Integer userRole);

    User findByUserid(String userid);
}
