package uz.perevods.perevod.repository.application;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.perevods.perevod.entitiy.application.TransactionalMoney;

import java.util.List;
import java.util.Set;

@Repository
public interface TransactionalMoneyRepository extends JpaRepository<TransactionalMoney, String> {

    Set<TransactionalMoney> findAll(Specification<TransactionalMoney> specification1);
}
