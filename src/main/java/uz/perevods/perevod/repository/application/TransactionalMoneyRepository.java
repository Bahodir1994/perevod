package uz.perevods.perevod.repository.application;


import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import uz.perevods.perevod.entitiy.application.TransactionalMoney;
import uz.perevods.perevod.service.helperClass.TransactionalMoneyDebtDto;

import java.util.Optional;
import java.util.Set;

@Repository
public interface TransactionalMoneyRepository extends JpaRepository<TransactionalMoney, String> {
    Set<TransactionalMoney> findAll(Specification<TransactionalMoney> specification1);

    Optional<TransactionalMoney> findOne(Specification<TransactionalMoney> specification1);

    @Query("select new uz.perevods.perevod.service.helperClass.TransactionalMoneyDebtDto(" +
            " sum(case when tm.paymentCost > tm.payedCost then 1 else 0 end) as soni," +
            " COALESCE(sum(tm.paymentCost), 0.00) as jami," +
            " COALESCE(sum(tm.payedCost), 0.00) as undirildi," +
            " COALESCE(sum(tm.paymentCost - tm.payedCost), 0.00) as qoldiq)" +
            " from TransactionalMoney as tm where tm.debt = true and (:toLocation is null or :toLocation = '' or tm.insLocationCode != :toLocation) and tm.paymentCostType = :costTypes")
    Optional<TransactionalMoneyDebtDto> findByQuery1(
            @Param("toLocation") String toLocation,
            @Param("costTypes") String costTypes
    );
}
