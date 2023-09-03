package uz.perevods.perevod.service.application;


import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.stereotype.Service;
import uz.perevods.perevod.entitiy.application.TransactionalMoneyLog;
import uz.perevods.perevod.repository.application.TransactionalMoneyLogDataRepository;
import uz.perevods.perevod.repository.application.TransactionalMoneyLogRepository;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class TransactionalMoneyLogService {
    private final TransactionalMoneyLogRepository transactionalMoneyLogRepository;
    private final TransactionalMoneyLogDataRepository transactionalMoneyLogDataRepository;

    public void loggerForTransactionalMoneyLog(String transactionalMoneyLogId, String payedCost, String comment){
        TransactionalMoneyLog transactionalMoneyLog = new TransactionalMoneyLog();
        transactionalMoneyLog.setTransactionalMoneyLogId(transactionalMoneyLogId);
        transactionalMoneyLog.setPayedCost(new BigDecimal(payedCost));
        transactionalMoneyLog.setComment(comment);
        transactionalMoneyLogRepository.save(transactionalMoneyLog);
    }

    public DataTablesOutput<TransactionalMoneyLog> getData1(DataTablesInput tablesInput) {
        return transactionalMoneyLogDataRepository.findAll(tablesInput);
    }
}
