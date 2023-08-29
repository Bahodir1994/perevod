package uz.perevods.perevod.service.application;

import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import uz.perevods.perevod.repository.application.TransactionalMoneyDataRepository;
import uz.perevods.perevod.service.helperClass.CashRegister;
import uz.perevods.perevod.service.helperClass.MessageCLassDto;
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
import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

@Service
@RequiredArgsConstructor
public class AppService1 {
    private final TotalMoneyRepository totalMoneyRepository;
    private final TransactionalMoneyRepository transactionalMoneyRepository;
    private final TransactionalMoneyDataRepository transactionalMoneyDataRepository;
    private final TotalMoneyLogRepository totalMoneyLogRepository;

    public DataTablesOutput<TransactionalMoney> getData1(DataTablesInput tablesInput){

        DataTablesOutput<TransactionalMoney> transactionalMonies = transactionalMoneyDataRepository.findAll(tablesInput);
        return transactionalMonies;
    }
    public TotalMoney getData2(Users users){
        String statusTotalMoneyStarted = "1";
        String statusTotalMoneyLogActive = "1";
        boolean statusTransactionalMoneyDebt = true;
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
            saveTransactionalMoneyIn(trMDto, totalMoney.getId());
            if (!trMDto.getIsDebt()){
                saveTotalMoneyLog(users, totalMoney, trMDto, null, "in");
            }
            message = "Bajarilid (uzs)!";
            status = true;
        }else {
            message = "Xisobda mablag' yetarli emas!";
        }
        return new MessageCLassDtoSimple(message, status);
    }
    public void saveTransactionalMoneyIn(TransactionalMoneyDto trMDto, String totalMoneyId){
        TransactionalMoney transactionalMoney = new TransactionalMoney();
        transactionalMoney.setTotalMoneyId(totalMoneyId);
        transactionalMoney.setFullName(trMDto.getFullName());
        transactionalMoney.setPhone(trMDto.getTelNumber());
        transactionalMoney.setPaymentCost(new BigDecimal(trMDto.getMoneyCost()));
        transactionalMoney.setPaymentCostType(trMDto.getMoneyType());
        transactionalMoney.setServiceUzs(new BigDecimal(trMDto.getServiceMoney()));
        transactionalMoney.setInTime(new Date(System.currentTimeMillis()));
        transactionalMoney.setDebt(trMDto.getIsDebt());
        transactionalMoney.setInsLocationCode(trMDto.getSendToAddress());
        transactionalMoney.setInsLocationName(getLocationName(trMDto.getSendToAddress()));
        transactionalMoneyRepository.save(transactionalMoney);
    }

    /**out Money**/
    public MessageCLassDto setCheckOutMoney(Users users, String value1) {
        AtomicBoolean thatIsForUserLocation = new AtomicBoolean(false); //this user by location can not check out money
        AtomicBoolean noShortageOfMoney = new AtomicBoolean(false); // not enough money to transfer
        AtomicBoolean thatIsDebt = new AtomicBoolean(false); // this money was issued for debt

        MessageCLassDto messageCLassDto = new MessageCLassDto();
        Specification<TransactionalMoney> specification = (root, query, criteriaBuilder) -> {
            Fetch<TransactionalMoney, TotalMoney> fetch1 = root.fetch("totalMoney", JoinType.LEFT);
            Fetch<TotalMoney, TotalMoneyLog> fetch2 = fetch1.fetch("totalMoneyLogs", JoinType.LEFT);
            Predicate predicate1 = criteriaBuilder.equal(root.get("id"), value1);
            return criteriaBuilder.and(predicate1);
        };
        Optional<TransactionalMoney> transactionalMoney = transactionalMoneyRepository.findOne(specification);

        /*todo-->*Chqim qilayotgan kassa ma'lumotlari*/
        String statusChechOutUserTotalMoney = "1";
        Specification<TotalMoney> specification1 = (root, query, criteriaBuilder) -> {
            root.fetch("totalMoneyLogs", JoinType.LEFT);
            root.fetch("transactionalMoneyList", JoinType.LEFT);
            Predicate predicate1 = criteriaBuilder.equal(root.get("insLocationCode"), users.getLocationCode());
            Predicate predicate2 = criteriaBuilder.equal(root.get("status"), statusChechOutUserTotalMoney);
            return criteriaBuilder.and(predicate1, predicate2);
        };
        TotalMoney totalMoney = totalMoneyRepository.findOne(specification1);

        transactionalMoney.ifPresentOrElse(
                transactionalMoney1 -> {
                    thatIsForUserLocation.set(Objects.equals(transactionalMoney1.getInsLocationCode(), users.getLocationCode()));
                    if (transactionalMoney1.getPaymentCostType().equals("uzs")){
                        noShortageOfMoney.set(totalMoney.getTotalMoneyLogs().get(0).getTotalMoneyUzs().compareTo(transactionalMoney1.getPaymentCost()) > 0);
                    }
                    if (transactionalMoney1.getPaymentCostType().equals("usd")){
                        noShortageOfMoney.set(totalMoney.getTotalMoneyLogs().get(0).getTotalMoneyUsd().compareTo(transactionalMoney1.getPaymentCost()) > 0);
                    }
                    thatIsDebt.set(transactionalMoney1.getDebt());

                    if (thatIsForUserLocation.get() && noShortageOfMoney.get()){
                        messageCLassDto.setMessage(new AtomicReference<>("Mablag' chiqarildi!"));
                        messageCLassDto.setSuccess(new AtomicReference<>(true));

                        saveTransactionalMoneyOut(transactionalMoney1);
                        saveTotalMoneyLog(users, totalMoney, null, transactionalMoney1, "out");
                        /*todo**->Bu yerda berish amalga  oshiriladi / pul yetarli berishga**/
                    }else {
                        if (!thatIsForUserLocation.get()){
                            messageCLassDto.setMessage(new AtomicReference<>("Siz chiqim qila olmaysiz!"));
                            messageCLassDto.setSuccess(new AtomicReference<>(false));
                            /*todo**->Locationi togri kemagani uchun berlomaydi**/
                        } else if(!noShortageOfMoney.get()){
                            messageCLassDto.setMessage(new AtomicReference<>("Xisobda yetarli mablag' mavjud emas!"));
                            messageCLassDto.setSuccess(new AtomicReference<>(false));
                            /*todo**->Puli yetmagani uchun berolmaydi**/
                        }
                    }
                },
                () -> {
                    messageCLassDto.setMessage(new AtomicReference<>("Ko'zda tutilmagan xatolik!"));
                    messageCLassDto.setSuccess(new AtomicReference<>(false));
                }
        );
        return messageCLassDto;
    }
    public void saveTransactionalMoneyOut(TransactionalMoney trMDto){
        trMDto.setOutTime(new Date(System.currentTimeMillis()));
        transactionalMoneyRepository.save(trMDto);
    }

    /**plus money or minus money by inOrOut param**/
    public void saveTotalMoneyLog(Users users, TotalMoney totalMoney, TransactionalMoneyDto trMDto, TransactionalMoney transactionalMoney, String inOrOut){
        if (inOrOut.equals("in")){
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
        else { //out
            TotalMoneyLog totalMoneyLog = totalMoneyLogRepository.findByTotalMoneyId(totalMoney.getId());

            BigDecimal oldTotalMoneyCost;
            BigDecimal oldMoneyUsage;

            BigDecimal totalMoneyCost;
            BigDecimal moneyUsage;

            if (transactionalMoney.getPaymentCostType().equals("uzs")){
                oldTotalMoneyCost = totalMoneyLog.getTotalMoneyUzs();
                oldMoneyUsage = totalMoneyLog.getTotalMoneyUzsGive();

                totalMoneyCost = oldTotalMoneyCost.subtract(transactionalMoney.getPaymentCost());
                moneyUsage = oldMoneyUsage.add(transactionalMoney.getPaymentCost());

                totalMoneyLog.setTotalMoneyUzs(totalMoneyCost);
                totalMoneyLog.setTotalMoneyUzsGive(moneyUsage);

                totalMoneyLogRepository.save(totalMoneyLog);
            }else { //usd
                oldTotalMoneyCost = totalMoneyLog.getTotalMoneyUsd();
                oldMoneyUsage = totalMoneyLog.getTotalMoneyUsdGive();

                totalMoneyCost = oldTotalMoneyCost.subtract(transactionalMoney.getPaymentCost());
                moneyUsage = oldMoneyUsage.add(transactionalMoney.getPaymentCost());

                totalMoneyLog.setTotalMoneyUsd(totalMoneyCost);
                totalMoneyLog.setTotalMoneyUsdGive(moneyUsage);

                totalMoneyLogRepository.save(totalMoneyLog);
            }
        }
    }

    private String escapeContent(String content) {
        return content
                .replaceAll("~", "~~")
                .replaceAll("%", "~%")
                .replaceAll("_", "~_")
                .trim()
                .toUpperCase();
    }
    private String getLocationName(String locationCode) {
        if (locationCode.equals("01")){
            return "Toshkent";
        }
        if (locationCode.equals("95")){
            return "Mang'it";
        }
        return null;
    }

    /**add total money or subtract total money**/
    public void changeTotalMoney(CashRegister cashRegister, Users users){
        String locationCode = cashRegister.getCashRegister();
        String statusTotalMoneyActive = "1";
        String statusTotalMoneyNotActive = "0";

        Specification<TotalMoney> specification1 = (root, query, criteriaBuilder) -> {
            root.fetch("totalMoneyLogs", JoinType.LEFT);
            root.fetch("transactionalMoneyList", JoinType.LEFT);
            Predicate predicate1 = criteriaBuilder.equal(root.get("status"), statusTotalMoneyActive);
            Predicate predicate2 = criteriaBuilder.equal(root.get("status"), statusTotalMoneyNotActive);
            Predicate predicate3 = criteriaBuilder.equal(root.get("insLocationCode"), locationCode);
            Predicate predicateOr = criteriaBuilder.or(predicate1, predicate2);
            return criteriaBuilder.and(predicate3, predicateOr);
        };
        Optional<TotalMoney> totalMoney = totalMoneyRepository.findSpecific(specification1);

        totalMoney.ifPresentOrElse(
                totalMoney1 -> {
                    if (!cashRegister.getMinusUzs()){

                    }else {

                    }

                    if (!cashRegister.getMinusUsd()){

                    }else {

                    }
                },
                () -> {}
        );

        System.out.println("calling");
    }
}
