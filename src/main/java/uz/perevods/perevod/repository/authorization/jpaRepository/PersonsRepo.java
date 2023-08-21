package uz.perevods.perevod.repository.authorization.jpaRepository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.perevods.perevod.entitiy.authorization.Persons;

import java.util.Optional;

@Repository
public interface PersonsRepo extends JpaRepository<Persons, String> {
    Persons findByPin(String pin);

    Persons findByTin(String tin);

    Persons findByEmail(String email);

    Optional<Persons> findById(String id);

    Persons findByEmailAndPinAndTin(String email, String pin, String tin);

    Optional<Persons> findByPinOrTin(String tin, String pin);
}

