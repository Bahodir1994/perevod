package uz.perevods.perevod.repository.application;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.perevods.perevod.entitiy.application.TotalMoney;
import uz.perevods.perevod.entitiy.application.TransactionalMoney;

import java.util.Optional;

@Repository
public interface TotalMoneyRepository extends JpaRepository<TotalMoney, String> {

    Optional<TotalMoney> findByInsLocationCodeAndStatus(String insLocationCode, String status);

    TotalMoney findOne(Specification<TotalMoney> specification);

    Optional<TotalMoney> findSpecific(Specification<TotalMoney> specification);
}
