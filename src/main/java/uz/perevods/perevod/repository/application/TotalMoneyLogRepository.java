package uz.perevods.perevod.repository.application;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.perevods.perevod.entitiy.application.TotalMoneyLog;

@Repository
public interface TotalMoneyLogRepository extends JpaRepository<TotalMoneyLog, String> {
    TotalMoneyLog findByTotalMoney_TotalMoneyLogs_TotalMoneyId(String totalMoneyId);

    TotalMoneyLog findByTotalMoneyId(String totalMoneyId);
}
