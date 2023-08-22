package uz.perevods.perevod.repository.application;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.perevods.perevod.entitiy.application.TransactionalMoney;

@Repository
public interface TransactionalMoneyRepository extends JpaRepository<TransactionalMoney, String> {

}
