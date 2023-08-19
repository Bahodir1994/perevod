package uz.perevods.perevod.repository.auth;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;
import uz.perevods.perevod.entitiy.auth.User;

@Repository
public interface UserRepository extends CassandraRepository<User, String> {

    User findByUsername(String username);
}
