package uz.perevods.perevod.service.application;


import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import uz.perevods.perevod.entitiy.application.TransactionalMoneyLog;
import uz.perevods.perevod.repository.application.TransactionalMoneyLogDataRepository;
import uz.perevods.perevod.repository.application.TransactionalMoneyLogRepository;

import javax.persistence.criteria.Predicate;
import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class TransactionalMoneyLogService {
    private final TransactionalMoneyLogRepository transactionalMoneyLogRepository;
    private final TransactionalMoneyLogDataRepository transactionalMoneyLogDataRepository;

    /**for debt*/
    public void loggerForTransactionalMoneyLog(String transactionalMoneyLogId, String payedCost, String comment){
        TransactionalMoneyLog transactionalMoneyLog = new TransactionalMoneyLog();
        transactionalMoneyLog.setTransactionalMoneyLogId(transactionalMoneyLogId);
        transactionalMoneyLog.setPayedCost(new BigDecimal(payedCost));
        transactionalMoneyLog.setComment(comment);
        transactionalMoneyLogRepository.save(transactionalMoneyLog);
    }

    /**for out*/
    public void loggerForTransactionalMoneyLogForOut(String transactionalMoneyLogId, String payedCost, String comment){
        TransactionalMoneyLog transactionalMoneyLog = new TransactionalMoneyLog();
        transactionalMoneyLog.setTransactionalMoneyLogId(transactionalMoneyLogId);
        transactionalMoneyLog.setPayedCost(new BigDecimal(payedCost));
        transactionalMoneyLog.setComment(comment);
        transactionalMoneyLog.setIsDebtHistory(false);
        transactionalMoneyLogRepository.save(transactionalMoneyLog);
    }

    /**for debt*/
    public DataTablesOutput<TransactionalMoneyLog> getData1(DataTablesInput tablesInput) {
        Specification<TransactionalMoneyLog> specification = (root, query, criteriaBuilder) -> {
            Predicate predicate1 = criteriaBuilder.equal(root.get("isDebtHistory"), true);
            return criteriaBuilder.and(predicate1);
        };
        return transactionalMoneyLogDataRepository.findAll(tablesInput, specification);
    }

    /**for out*/
    public DataTablesOutput<TransactionalMoneyLog> getData2(DataTablesInput tablesInput) {
        Specification<TransactionalMoneyLog> specification = (root, query, criteriaBuilder) -> {
            Predicate predicate1 = criteriaBuilder.equal(root.get("isDebtHistory"), false);
            return criteriaBuilder.and(predicate1);
        };
        return transactionalMoneyLogDataRepository.findAll(tablesInput, specification);
    }
}
