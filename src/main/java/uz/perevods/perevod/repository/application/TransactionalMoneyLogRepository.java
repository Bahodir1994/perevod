package uz.perevods.perevod.repository.application;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.perevods.perevod.entitiy.application.TransactionalMoneyLog;

@Repository
public interface TransactionalMoneyLogRepository extends JpaRepository<TransactionalMoneyLog, String> {

}
