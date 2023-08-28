package uz.perevods.perevod.service.application;

import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import uz.perevods.perevod.repository.application.TransactionalMoneyDataRepository;
import uz.perevods.perevod.service.helperClass.TransactionalMoneyDto;
import uz.perevods.perevod.entitiy.application.TotalMoney;
import uz.perevods.perevod.entitiy.application.TotalMoneyLog;
import uz.perevods.perevod.entitiy.application.TransactionalMoney;
import uz.perevods.perevod.entitiy.authorization.Users;
import uz.perevods.perevod.repository.application.TotalMoneyLogRepository;
import uz.perevods.perevod.repository.application.TotalMoneyRepository;
import uz.perevods.perevod.repository.application.TransactionalMoneyRepository;
import uz.perevods.perevod.service.helperClass.MessageCLassDtoSimple;

import javax.persistence.criteria.Fetch;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AppService1 {
    private final TotalMoneyRepository totalMoneyRepository;
    private final TransactionalMoneyRepository transactionalMoneyRepository;
    private final TransactionalMoneyDataRepository transactionalMoneyDataRepository;
    private final TotalMoneyLogRepository totalMoneyLogRepository;

    public DataTablesOutput<TransactionalMoney> getData1(DataTablesInput tablesInput){

        DataTablesOutput<TransactionalMoney> output = transactionalMoneyDataRepository.findAll(tablesInput);
        return output;
    }
    public TotalMoney getData2(Users users){
        String statusTotalMoneyStarted = "1";
        String statusTotalMoneyLogActive = "1";
        String statusTransactionalMoneyDebt = "1";
        Specification<TotalMoney> specification = (root, query, criteriaBuilder) -> {
            Fetch<TotalMoney, TotalMoneyLog> fetch1 = root.fetch("totalMoneyLogs", JoinType.LEFT);
            Join<TotalMoney, TotalMoneyLog> join1 = (Join<TotalMoney, TotalMoneyLog>) fetch1;

            Predicate predicate1 = criteriaBuilder.equal(root.get("status"), statusTotalMoneyStarted);
            Predicate predicate2 = criteriaBuilder.equal(root.get("insLocationCode"), users.getLocationCode());
            Predicate predicate3 = criteriaBuilder.equal(join1.get("status"), statusTotalMoneyLogActive);

            return criteriaBuilder.and(predicate1, predicate2, predicate3);
        };

        Specification<TransactionalMoney> specification1 = (root, query, criteriaBuilder) -> {
            Predicate predicate4 = criteriaBuilder.equal(root.get("debt"), statusTransactionalMoneyDebt);
            return criteriaBuilder.and(predicate4);
        };
        Set<TransactionalMoney> transactionalMoney = transactionalMoneyRepository.findAll(specification1);
        TotalMoney totalMoney = totalMoneyRepository.findOne(specification);
        totalMoney.setTransactionalMoneyList(transactionalMoney);

        return totalMoney;
    }

    /**in Money**/
    public MessageCLassDtoSimple setData1(TransactionalMoneyDto trMDto, Users users){
        String totalMoneyStatusActive = "1";
        String totalMoneyLogStatusActive = "1";
        String message = null;
        Boolean status = false;
        Specification<TotalMoney> specification1= (root, query, criteriaBuilder) -> {
            Fetch<TotalMoney, TotalMoneyLog> fetch1 = root.fetch("totalMoneyLogs", JoinType.LEFT);
            Join<TotalMoney, TotalMoneyLog> join1 = (Join<TotalMoney, TotalMoneyLog>) fetch1;

            Predicate predicate1 = criteriaBuilder.equal(root.get("insLocationCode"), users.getLocationCode());
            Predicate predicate2 = criteriaBuilder.equal(root.get("status"), totalMoneyStatusActive);
            Predicate predicate3 = criteriaBuilder.equal(join1.get("status"), totalMoneyLogStatusActive);

            return criteriaBuilder.and(predicate1, predicate2, predicate3);
        };
        TotalMoney totalMoney = totalMoneyRepository.findOne(specification1);
        if (totalMoney != null){
            /**1**/
            saveTransactionalMoney(trMDto);
            saveTotalMoneyLog(totalMoney, trMDto);
            message = "Bajarilid (uzs)!";
            status = true;
        }else {
            message = "Xisobda mablag' yetarli emas!";
        }
        return new MessageCLassDtoSimple(message, status);
    }
    public void saveTransactionalMoney(TransactionalMoneyDto trMDto){
        TransactionalMoney transactionalMoney = new TransactionalMoney();
        transactionalMoney.setFullName(trMDto.getFullName());
        transactionalMoney.setPhone(trMDto.getTelNumber());
        transactionalMoney.setPaymentCost(new BigDecimal(trMDto.getMoneyCost()));
        transactionalMoney.setPaymentCostType(trMDto.getMoneyType());
        transactionalMoney.setServiceUzs(new BigDecimal(trMDto.getServiceMoney()));
        transactionalMoney.setInTime(new Date(System.currentTimeMillis()));
        transactionalMoney.setDebt(trMDto.getIsDebt().toString());
        transactionalMoneyRepository.save(transactionalMoney);
    }
    /**plus money**/
    public void saveTotalMoneyLog(TotalMoney totalMoney, TransactionalMoneyDto trMDto){
        TotalMoneyLog totalMoneyLog = totalMoneyLogRepository.findByTotalMoneyId(totalMoney.getId());

        BigDecimal totalMoneyCost;
        BigDecimal moneyUsage;

        if (trMDto.getMoneyType().equals("uzs")){
            totalMoneyCost = totalMoneyLog.getTotalMoneyUzs().add(new BigDecimal(trMDto.getMoneyCost()));
            moneyUsage = totalMoneyLog.getTotalMoneyUzsGive().add(new BigDecimal(trMDto.getMoneyCost()));

            totalMoneyLog.setTotalMoneyUzs(totalMoneyCost);
            totalMoneyLog.setTotalMoneyUzsGive(moneyUsage);
        } else { /*else usd*/
            totalMoneyCost = totalMoneyLog.getTotalMoneyUsd().add(new BigDecimal(trMDto.getMoneyCost()));
            moneyUsage = totalMoneyLog.getTotalMoneyUsdGive().add(new BigDecimal(trMDto.getMoneyCost()));

            totalMoneyLog.setTotalMoneyUsd(totalMoneyCost);
            totalMoneyLog.setTotalMoneyUsdGive(moneyUsage);
        }

        totalMoneyLogRepository.save(totalMoneyLog);
    }


    /**out Money**/


    private String escapeContent(String content) {
        return content
                .replaceAll("~", "~~")
                .replaceAll("%", "~%")
                .replaceAll("_", "~_")
                .trim()
                .toUpperCase();
    }
}
