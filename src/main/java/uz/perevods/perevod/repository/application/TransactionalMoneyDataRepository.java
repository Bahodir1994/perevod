package uz.perevods.perevod.repository.application;

import org.springframework.data.jpa.datatables.repository.DataTablesRepository;
import org.springframework.stereotype.Repository;
import uz.perevods.perevod.entitiy.application.TransactionalMoney;

@Repository
public interface TransactionalMoneyDataRepository extends DataTablesRepository<TransactionalMoney, String> {
}
