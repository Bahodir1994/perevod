package uz.perevods.perevod.repository.authorization;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import uz.perevods.perevod.entitiy.authorization.Users;

import javax.transaction.Transactional;

@Repository
public interface UserRepository extends JpaRepository<Users, Long> {
    Users findByUsername(String username);

    Users findOne(Specification<Users> specification);

    @Transactional
    @Modifying
    @Query("UPDATE Users " +
            "SET locationCode = CASE " +
            "WHEN locationCode = '95' THEN '01' " +
            "WHEN locationCode = '01' THEN '95' " +
            "ELSE locationCode " +
            "END, " +
            "locationName = CASE " +
            "WHEN locationCode = '95' THEN 'Toshkent' " +
            "WHEN locationCode = '01' THEN 'Mang''it' " +
            "ELSE locationName " +
            "END, " +
            "updTime = CURRENT_TIMESTAMP") // Set the updateTime to the current timestamp
    void updateLocationCodesAndNames();

}
