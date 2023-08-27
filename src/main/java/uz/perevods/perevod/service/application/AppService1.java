package uz.perevods.perevod.service.application;

import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import uz.perevods.perevod.controller.application.TransactionalMoneyDto;
import uz.perevods.perevod.entitiy.application.TotalMoney;
import uz.perevods.perevod.entitiy.application.TotalMoneyLog;
import uz.perevods.perevod.entitiy.application.TransactionalMoney;
import uz.perevods.perevod.entitiy.authorization.Users;
import uz.perevods.perevod.repository.application.TotalMoneyRepository;
import uz.perevods.perevod.repository.application.TransactionalMoneyRepository;
import uz.perevods.perevod.service.helperClass.MessageCLassDto;
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
    public DataTablesOutput<TransactionalMoney> getData1(DataTablesInput tablesInput, HttpServletRequest request){

        List<TransactionalMoney> transactionalMonies = transactionalMoneyRepository.findAll();

        DataTablesOutput<TransactionalMoney> output = new DataTablesOutput<>();
        output.setData(transactionalMonies);
        output.setRecordsTotal(transactionalMonies.size());
        output.setRecordsFiltered(transactionalMonies.size());
        output.setDraw(tablesInput.getDraw());

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


    /**Out Money**/
    public MessageCLassDtoSimple setData1(TransactionalMoneyDto trMDto, Users users){
        String totalMoneyStatusActive = "1";
        String totalMoneyLogStatusActive = "1";
        String message = null;
        Boolean status = false;
        Specification<TotalMoney> specification1= (root, query, criteriaBuilder) -> {
            Fetch<TotalMoney, TotalMoneyLog> fetch1 = root.fetch("TotalMoneyLog", JoinType.LEFT);
            Join<TotalMoney, TotalMoneyLog> join1 = (Join<TotalMoney, TotalMoneyLog>) fetch1;

            Predicate predicate1 = criteriaBuilder.equal(root.get("insLocationCode"), users.getLocationCode());
            Predicate predicate2 = criteriaBuilder.equal(root.get("status"), totalMoneyStatusActive);
            Predicate predicate3 = criteriaBuilder.equal(join1.get("status"), totalMoneyLogStatusActive);

            return criteriaBuilder.and(predicate1, predicate2, predicate3);
        };
        TotalMoney totalMoney = totalMoneyRepository.findOne(specification1);
        if (totalMoney != null
                && trMDto.getMoneyType().equals("uzs")
                && totalMoney.getTotalUzs().compareTo(new BigDecimal(trMDto.getMoneyCost())) > 0){
                /**1**/
            saveTransactionalMoney(trMDto);
            message = "Bajarilid (uzs)!";
            status = true;
        } else if (totalMoney != null
                && trMDto.getMoneyType().equals("usd")
                && totalMoney.getTotalUzs().compareTo(new BigDecimal(trMDto.getMoneyCost())) > 0) {
                /**2**/
            saveTransactionalMoney(trMDto);
            message = "Bajarilid (usd)!";
            status = true;
        }else {
            message = "Xisobda mablag' yetarli emas!";
        }
        return new MessageCLassDtoSimple(message, status);
    }

    public void saveTransactionalMoney(TransactionalMoneyDto trMDto){
        TransactionalMoney transactionalMoney = new TransactionalMoney();
        transactionalMoney.setTotalMoneyId(trMDto.getFullName());
        transactionalMoney.setFullName(trMDto.getFullName());
        transactionalMoney.setPhone(trMDto.getTelNumber());
        transactionalMoney.setPaymentCost(new BigDecimal(trMDto.getMoneyCost()));
        transactionalMoney.setPaymentCostType(trMDto.getMoneyType());
        transactionalMoney.setServiceUzs(new BigDecimal(trMDto.getServiceMoney()));
        transactionalMoney.setOutTime(new Date(System.currentTimeMillis()));
        transactionalMoney.setDebt(trMDto.getIsDebt().toString());
        transactionalMoneyRepository.save(transactionalMoney);
    }
}
