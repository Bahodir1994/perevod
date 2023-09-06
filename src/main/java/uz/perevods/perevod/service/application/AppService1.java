package uz.perevods.perevod.service.application;

import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import uz.perevods.perevod.repository.application.TransactionalMoneyDataRepository;
import uz.perevods.perevod.service.helperClass.*;
import uz.perevods.perevod.entitiy.application.TotalMoney;
import uz.perevods.perevod.entitiy.application.TotalMoneyLog;
import uz.perevods.perevod.entitiy.application.TransactionalMoney;
import uz.perevods.perevod.entitiy.authorization.Users;
import uz.perevods.perevod.repository.application.TotalMoneyLogRepository;
import uz.perevods.perevod.repository.application.TotalMoneyRepository;
import uz.perevods.perevod.repository.application.TransactionalMoneyRepository;

import javax.persistence.criteria.Fetch;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.validation.Valid;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

@Service
@RequiredArgsConstructor
public class AppService1 {
    private final TotalMoneyRepository totalMoneyRepository;
    private final TransactionalMoneyRepository transactionalMoneyRepository;
    private final TransactionalMoneyDataRepository transactionalMoneyDataRepository;
    private final TotalMoneyLogRepository totalMoneyLogRepository;
    private final TransactionalMoneyLogService transactionalMoneyLogService;

    public DataTablesOutput<TransactionalMoney> getData1(DataTablesInput tablesInput, Users users){
        String outOrIn = tablesInput.getColumn("insLocationCode").getSearch().getValue();
        String giveType = tablesInput.getColumn("outTime").getSearch().getValue();
        String costType = tablesInput.getColumn("id").getSearch().getValue();
        String inTime = tablesInput.getColumn("inTime").getSearch().getValue();
        Date[] dates = dateRange(inTime);

        tablesInput.getColumn("insLocationCode").getSearch().setValue("");
        tablesInput.getColumn("outTime").getSearch().setValue("");
        tablesInput.getColumn("id").getSearch().setValue("");
        tablesInput.getColumn("inTime").getSearch().setValue("");

        Specification<TransactionalMoney> specification = (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            Predicate predicate1_only_out = criteriaBuilder.equal(root.get("insLocationCode"), users.getLocationCode());
            Predicate predicate2_only_in = criteriaBuilder.notEqual(root.get("insLocationCode"), users.getLocationCode());
            Predicate predicate21_only_in_and_by_date = criteriaBuilder.between(root.get("inTime"), dates[0], dates[1]);

            Predicate predicate3_only_give = criteriaBuilder.equal(root.get("paymentCost"), root.get("giveCost"));
            Predicate predicate4_only_not_give = criteriaBuilder.equal(root.get("giveCost"), BigDecimal.ZERO);

            Predicate predicate41_only_not_give_and_part_give = criteriaBuilder.equal(root.get("giveCost"), BigDecimal.ZERO);
            Predicate predicate42_only_not_give_and_part_give = criteriaBuilder.greaterThan(root.get("paymentCost"), root.get("giveCost"));
            Predicate predicate43_only_not_give_and_part_give = criteriaBuilder.greaterThan(root.get("giveCost"), BigDecimal.ZERO);

            Predicate predicate51_only_part_give = criteriaBuilder.greaterThan(root.get("giveCost"), BigDecimal.ZERO);
            Predicate predicate52_only_part_give = criteriaBuilder.greaterThan(root.get("paymentCost"), root.get("giveCost"));

            Predicate predicate6_only_uzs = criteriaBuilder.equal(root.get("paymentCostType"), "uzs");
            Predicate predicate7_only_usd = criteriaBuilder.equal(root.get("paymentCostType"), "usd");

            if (outOrIn.equals("11")){
                predicates.add(predicate1_only_out);
            }
            if (outOrIn.equals("12")){
                predicates.add(predicate2_only_in);
                predicates.add(predicate21_only_in_and_by_date);
            }

            if (giveType.equals("11")){
                predicates.add(predicate3_only_give);
            }
            if (giveType.equals("12")){
                predicates.add(predicate4_only_not_give);
            }
            if (giveType.equals("13")){
                predicates.add(predicate51_only_part_give);
                predicates.add(predicate52_only_part_give);
            }
            if (giveType.equals("14")){
                Predicate predicateAnd2 = criteriaBuilder.and(predicate42_only_not_give_and_part_give, predicate43_only_not_give_and_part_give);
                Predicate predicateAnd = criteriaBuilder.or(predicate41_only_not_give_and_part_give, predicateAnd2);
                predicates.add(predicateAnd);
            }

            if (costType.equals("uzs")){
                predicates.add(predicate6_only_uzs);
            }
            if (costType.equals("usd")){
                predicates.add(predicate7_only_usd);
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
        DataTablesOutput<TransactionalMoney> transactionalMonies = transactionalMoneyDataRepository.findAll(tablesInput, specification);
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
            Predicate predicate5 = criteriaBuilder.isNull(root.get("debtPaid"));
            return criteriaBuilder.and(predicate4, predicate5);
        };
        Set<TransactionalMoney> transactionalMoney = transactionalMoneyRepository.findAll(specification1);
        TotalMoney totalMoney = totalMoneyRepository.findOne(specification).get();
        totalMoney.setTransactionalMoneyList(transactionalMoney);

        return totalMoney;
    }

    /*todo--> in Money >>*/
    public MessageCLassDtoSimple setData1In(TransactionalMoneyDto trMDto, Users users){
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
        TotalMoney totalMoney = totalMoneyRepository.findOne(specification1).get();
        if (totalMoney != null){
            /**1**/
            saveTransactionalMoneyIn(trMDto, totalMoney.getId());
            if (!trMDto.getIsDebt()){
                saveTotalMoneyLog(users, totalMoney, trMDto, null, "in");
            }
            message = "Bajarilid (uzs)!";
            status = true;
        }else {
            message = "Hisobda mablag' yetarli emas!";
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
        transactionalMoney.setServiceUzs(trMDto.getServiceMoney().equals("") ? BigDecimal.ZERO : new BigDecimal(trMDto.getServiceMoney()));
        transactionalMoney.setInTime(new Date(System.currentTimeMillis()));
        transactionalMoney.setDebt(trMDto.getIsDebt());
        transactionalMoney.setInsLocationCode(trMDto.getSendToAddress());
        transactionalMoney.setInsLocationName(getLocationName(trMDto.getSendToAddress()));
        transactionalMoney.setComment(trMDto.getComment());
        transactionalMoneyRepository.save(transactionalMoney);
    }

    public MessageCLassDto setData1InChange(String type, TransactionalMoneyDto moneyDto, Users users) {
        MessageCLassDto messageCLassDto = new MessageCLassDto();

        Specification<TransactionalMoney> specification1 = (root, query, criteriaBuilder) -> {
            Fetch<TransactionalMoney, TotalMoney> fetch1 = root.fetch("totalMoney", JoinType.LEFT);
            fetch1.fetch("totalMoneyLogs", JoinType.LEFT);
            Predicate predicate1 = criteriaBuilder.equal(root.get("id"), moneyDto.getId());
            return criteriaBuilder.and(predicate1);
        };
        Optional<TransactionalMoney> transactionalMoney = transactionalMoneyRepository.findOne(specification1);
        transactionalMoney.ifPresentOrElse(
                transactionalMoney1 -> {
                    Optional<TotalMoney> totalMoney;
                    if (transactionalMoney1.getTotalMoney().getStatus().equals("1")){
                        totalMoney = totalMoneyRepository.findById(transactionalMoney1.getTotalMoney().getId());
                    }else {
                        totalMoney = totalMoneyRepository.findByInsLocationCodeAndStatus(transactionalMoney1.getTotalMoney().getInsLocationCode(), "1");
                    }
                    totalMoney.ifPresentOrElse(
                            totalMoney1 -> {
                                if (!totalMoney1.getInsLocationCode().equals(users.getLocationCode())){
                                    messageCLassDto.setSuccess(new AtomicReference<>(false));
                                    messageCLassDto.setMessage(new AtomicReference<>("Siz ushbu ma'lumotlarni o'zgartira olmaysiz!"));
                                }else {
                                    TotalMoneyLog totalMoneyLog = totalMoneyLogRepository.findByTotalMoneyId(totalMoney1.getId());
                                    if (transactionalMoney1.getPaymentCostType().equals("uzs")
                                            && transactionalMoney1.getPayedCost().compareTo(BigDecimal.ZERO) == 0
                                            && transactionalMoney1.getGiveCost().compareTo(BigDecimal.ZERO) == 0
                                    ) {
                                        totalMoneyLog.setTotalMoneyUzs(totalMoneyLog.getTotalMoneyUzs().subtract(transactionalMoney1.getPaymentCost()));
                                        totalMoneyLog.setTotalMoneyUzsGive(totalMoneyLog.getTotalMoneyUzsGive().subtract(transactionalMoney1.getPaymentCost()));
                                        totalMoneyLogRepository.save(totalMoneyLog);
                                        if (type.equals("put")) {
                                            if (moneyDto.getIsDebt()) {
                                                transactionalMoney1.setFullName(moneyDto.getFullName());
                                                transactionalMoney1.setPaymentCost(new BigDecimal(moneyDto.getMoneyCost()));
                                                transactionalMoney1.setPaymentCostType(moneyDto.getMoneyType());
                                                transactionalMoney1.setInsLocationCode(moneyDto.getSendToAddress());
                                                transactionalMoney1.setInsLocationName(moneyDto.getSendToAddress().equals("01") ? "Toshkent" : "Mang'it");
                                                transactionalMoney1.setServiceUzs(new BigDecimal(moneyDto.getServiceMoney()));
                                                transactionalMoney1.setPhone(moneyDto.getTelNumber());
                                                transactionalMoney1.setDebt(moneyDto.getIsDebt());
                                                transactionalMoney1.setComment(moneyDto.getComment());
                                                transactionalMoneyRepository.save(transactionalMoney1);

                                                messageCLassDto.setSuccess(new AtomicReference<>(true));
                                                messageCLassDto.setMessage(new AtomicReference<>("O'zgartirildi!"));
                                            } else { // else not debt
                                                transactionalMoney1.setFullName(moneyDto.getFullName());
                                                transactionalMoney1.setPaymentCost(new BigDecimal(moneyDto.getMoneyCost()));
                                                transactionalMoney1.setPaymentCostType(moneyDto.getMoneyType());
                                                transactionalMoney1.setInsLocationCode(moneyDto.getSendToAddress());
                                                transactionalMoney1.setInsLocationName(moneyDto.getSendToAddress().equals("01") ? "Toshkent" : "Mang'it");
                                                transactionalMoney1.setServiceUzs(new BigDecimal(moneyDto.getServiceMoney()));
                                                transactionalMoney1.setPhone(moneyDto.getTelNumber());
                                                transactionalMoney1.setDebt(moneyDto.getIsDebt());
                                                transactionalMoney1.setComment(moneyDto.getComment());
                                                transactionalMoneyRepository.save(transactionalMoney1);

                                                totalMoneyLog.setTotalMoneyUzs(totalMoneyLog.getTotalMoneyUzs().add(transactionalMoney1.getPaymentCost()));
                                                totalMoneyLog.setTotalMoneyUzsGive(totalMoneyLog.getTotalMoneyUzsGive().add(transactionalMoney1.getPaymentCost()));
                                                totalMoneyLogRepository.save(totalMoneyLog);

                                                messageCLassDto.setSuccess(new AtomicReference<>(true));
                                                messageCLassDto.setMessage(new AtomicReference<>("O'zgartirildi!"));
                                            }
                                        } else { // else delete
                                            transactionalMoneyRepository.deleteById(transactionalMoney1.getId());
                                            messageCLassDto.setSuccess(new AtomicReference<>(true));
                                            messageCLassDto.setMessage(new AtomicReference<>("O'chirildi!"));
                                        }
                                    } else if (transactionalMoney1.getPaymentCostType().equals("usd")
                                            && transactionalMoney1.getPayedCost().compareTo(BigDecimal.ZERO) == 0
                                            && transactionalMoney1.getGiveCost().compareTo(BigDecimal.ZERO) == 0
                                    ) {
                                        totalMoneyLog.setTotalMoneyUsd(totalMoneyLog.getTotalMoneyUsd().subtract(transactionalMoney1.getPaymentCost()));
                                        totalMoneyLog.setTotalMoneyUsdGive(totalMoneyLog.getTotalMoneyUsdGive().subtract(transactionalMoney1.getPaymentCost()));
                                        totalMoneyLogRepository.save(totalMoneyLog);
                                        if (type.equals("put")) {
                                            if (moneyDto.getIsDebt()) {
                                                transactionalMoney1.setFullName(moneyDto.getFullName());
                                                transactionalMoney1.setPaymentCost(new BigDecimal(moneyDto.getMoneyCost()));
                                                transactionalMoney1.setPaymentCostType(moneyDto.getMoneyType());
                                                transactionalMoney1.setInsLocationCode(moneyDto.getSendToAddress());
                                                transactionalMoney1.setInsLocationName(moneyDto.getSendToAddress().equals("01") ? "Toshkent" : "Mang'it");
                                                transactionalMoney1.setServiceUzs(new BigDecimal(moneyDto.getServiceMoney()));
                                                transactionalMoney1.setPhone(moneyDto.getTelNumber());
                                                transactionalMoney1.setDebt(moneyDto.getIsDebt());
                                                transactionalMoney1.setComment(moneyDto.getComment());
                                                transactionalMoneyRepository.save(transactionalMoney1);

                                                messageCLassDto.setSuccess(new AtomicReference<>(true));
                                                messageCLassDto.setMessage(new AtomicReference<>("O'zgartirildi!"));
                                            } else { // else not debt
                                                transactionalMoney1.setFullName(moneyDto.getFullName());
                                                transactionalMoney1.setPaymentCost(new BigDecimal(moneyDto.getMoneyCost()));
                                                transactionalMoney1.setPaymentCostType(moneyDto.getMoneyType());
                                                transactionalMoney1.setInsLocationCode(moneyDto.getSendToAddress());
                                                transactionalMoney1.setInsLocationName(moneyDto.getSendToAddress().equals("01") ? "Toshkent" : "Mang'it");
                                                transactionalMoney1.setServiceUzs(new BigDecimal(moneyDto.getServiceMoney()));
                                                transactionalMoney1.setPhone(moneyDto.getTelNumber());
                                                transactionalMoney1.setDebt(moneyDto.getIsDebt());
                                                transactionalMoney1.setComment(moneyDto.getComment());
                                                transactionalMoneyRepository.save(transactionalMoney1);

                                                totalMoneyLog.setTotalMoneyUsd(totalMoneyLog.getTotalMoneyUsd().add(transactionalMoney1.getPaymentCost()));
                                                totalMoneyLog.setTotalMoneyUsdGive(totalMoneyLog.getTotalMoneyUsdGive().add(transactionalMoney1.getPaymentCost()));
                                                totalMoneyLogRepository.save(totalMoneyLog);

                                                messageCLassDto.setSuccess(new AtomicReference<>(true));
                                                messageCLassDto.setMessage(new AtomicReference<>("O'zgartirildi!"));
                                            }
                                        } else { // else delete
                                            transactionalMoneyRepository.deleteById(transactionalMoney1.getId());
                                            messageCLassDto.setSuccess(new AtomicReference<>(true));
                                            messageCLassDto.setMessage(new AtomicReference<>("O'chirildi!"));
                                        }
                                    } else {
                                        messageCLassDto.setSuccess(new AtomicReference<>(false));
                                        messageCLassDto.setMessage(new AtomicReference<>("Kirim chiqim operatsiyalari amalga oshirilgan! (O'zgartirish ta'qiqlangan!)"));
                                    }
                                }
                            },
                            () -> {
                                messageCLassDto.setSuccess(new AtomicReference<>(false));
                                messageCLassDto.setMessage(new AtomicReference<>("Aktiv kassa topilmadi!"));
                            }
                    );
                },
                () -> {
                    messageCLassDto.setSuccess(new AtomicReference<>(false));
                    messageCLassDto.setMessage(new AtomicReference<>("Siz jo'natgan parametrlar bo'yicha ma'lumot topilmadi!"));
                }
        );
        return messageCLassDto;
    }
    /*todo--> in Money <<*/

    /*todo--> out Money >>*/
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
        TotalMoney totalMoney = totalMoneyRepository.findOne(specification1).get();

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
                            messageCLassDto.setMessage(new AtomicReference<>("Hisobda yetarli mablag' mavjud emas!"));
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
    } /* deprecated */
    public void saveTransactionalMoneyOut(TransactionalMoney trMDto){
        trMDto.setOutTime(new Date(System.currentTimeMillis()));
        transactionalMoneyRepository.save(trMDto);
    } /* deprecated */

    public MessageCLassDto setData1Out(@Valid OutUsagingDto outUsagingDto, Users users) {
        MessageCLassDto messageCLassDto = new MessageCLassDto();

        Specification<TotalMoney> specification2 = (root, query, criteriaBuilder) -> {
            root.fetch("totalMoneyLogs", JoinType.LEFT);
            Predicate predicate1 = criteriaBuilder.equal(root.get("insLocationCode"), users.getLocationCode());
            Predicate predicate2 = criteriaBuilder.equal(root.get("status"), "1"); // status 1 = active
            return criteriaBuilder.and(predicate1, predicate2);
        };
        Optional<TotalMoney> totalMoney = totalMoneyRepository.findOne(specification2);
        totalMoney.ifPresentOrElse(
                totalMoney1 -> {
                    TotalMoneyLog totalMoneyLog = totalMoney1.getTotalMoneyLogs().get(0);
                    Optional<TransactionalMoney> transactionalMoney = transactionalMoneyRepository.findById(outUsagingDto.getTransactionalMoneyId());
                    transactionalMoney.ifPresentOrElse(
                            transactionalMoney1 -> {
                                if (!transactionalMoney1.getInsLocationCode().equals(users.getLocationCode())){
                                    messageCLassDto.setSuccess(new AtomicReference<>(false));
                                    messageCLassDto.setMessage(new AtomicReference<>("Siz chiqimni amalga oshira olmaysiz!"));
                                } else if (transactionalMoney1.getPaymentCostType().equals("uzs")
                                        && totalMoneyLog.getTotalMoneyUzs()
                                        .compareTo(transactionalMoney1.getPaymentCost()) >= 0){
                                    /*todo**->do subtract uzs*/
                                    MessageCLassDto messageCLassDto1 = payAllOrNotAll(outUsagingDto, totalMoneyLog, transactionalMoney1);
                                    messageCLassDto.setMessage(messageCLassDto1.getMessage());
                                    messageCLassDto.setSuccess(messageCLassDto1.getSuccess());
                                } else if (transactionalMoney1.getPaymentCostType().equals("usd")
                                        && totalMoneyLog.getTotalMoneyUsd()
                                        .compareTo(transactionalMoney1.getPaymentCost()) >= 0) {
                                    /*todo**->do subtract usd*/
                                    MessageCLassDto messageCLassDto1 = payAllOrNotAll(outUsagingDto, totalMoneyLog, transactionalMoney1);
                                    messageCLassDto.setMessage(messageCLassDto1.getMessage());
                                    messageCLassDto.setSuccess(messageCLassDto1.getSuccess());
                                } else {
                                    messageCLassDto.setSuccess(new AtomicReference<>(false));
                                    messageCLassDto.setMessage(new AtomicReference<>("Chiqim qilish uchun mablag' yetarli emas!"));
                                }
                            },
                            () -> {
                                messageCLassDto.setSuccess(new AtomicReference<>(false));
                                messageCLassDto.setMessage(new AtomicReference<>("Bunday chiqim mablag'i topilmadi!"));
                            }
                    );
                },
                () -> {
                    messageCLassDto.setSuccess(new AtomicReference<>(false));
                    messageCLassDto.setMessage(new AtomicReference<>("Chiqim qilish uchun aktiv kassa mavjud emas!"));
                }
        );
        return messageCLassDto;
    }
    public MessageCLassDto payAllOrNotAll(OutUsagingDto outUsagingDto, TotalMoneyLog totalMoneyLog, TransactionalMoney transactionalMoney) {
        MessageCLassDto messageCLassDto = new MessageCLassDto();
        if (outUsagingDto.getPayAll()) {
            if (transactionalMoney.getPaymentCostType().equals("uzs")) {
                totalMoneyLog.setTotalMoneyUzs(totalMoneyLog.getTotalMoneyUzs().subtract(transactionalMoney.getPaymentCost().subtract(transactionalMoney.getGiveCost())));
                totalMoneyLog.setTotalMoneyUzsGive(totalMoneyLog.getTotalMoneyUzsGive().add(transactionalMoney.getPaymentCost().subtract(transactionalMoney.getGiveCost())));
            } else { // else usd
                totalMoneyLog.setTotalMoneyUsd(totalMoneyLog.getTotalMoneyUsd().subtract(transactionalMoney.getPaymentCost().subtract(transactionalMoney.getGiveCost())));
                totalMoneyLog.setTotalMoneyUsdGive(totalMoneyLog.getTotalMoneyUsdGive().add(transactionalMoney.getPaymentCost().subtract(transactionalMoney.getGiveCost())));
            }
            totalMoneyLogRepository.save(totalMoneyLog);
            transactionalMoneyLogService
                    .loggerForTransactionalMoneyLogForOut(
                            transactionalMoney.getId(),
                            transactionalMoney.getPaymentCost().compareTo(transactionalMoney.getGiveCost()) == 0 ? String.valueOf(transactionalMoney.getGiveCost()) : String.valueOf(transactionalMoney.getPaymentCost().subtract(transactionalMoney.getGiveCost())),
                            outUsagingDto.getComment()
                    );
            transactionalMoney.setGiveCost(transactionalMoney.getPaymentCost());
            transactionalMoneyRepository.save(transactionalMoney);

            messageCLassDto.setSuccess(new AtomicReference<>(true));
            messageCLassDto.setMessage(new AtomicReference<>("Chiqim to'liq bajarildi"));
        } else { // else payAll false
            if (transactionalMoney.getPaymentCost().compareTo(transactionalMoney.getGiveCost().add(new BigDecimal(outUsagingDto.getMoneyCost()))) > 0) {
                transactionalMoney.setGiveCost(transactionalMoney.getGiveCost().add(new BigDecimal(outUsagingDto.getMoneyCost())));
                if (transactionalMoney.getPaymentCostType().equals("uzs")) {
                    totalMoneyLog.setTotalMoneyUzs(totalMoneyLog.getTotalMoneyUzs().subtract(new BigDecimal(outUsagingDto.getMoneyCost())));
                    totalMoneyLog.setTotalMoneyUzsGive(totalMoneyLog.getTotalMoneyUzsGive().add(new BigDecimal(outUsagingDto.getMoneyCost())));
                } else { // else usd
                    totalMoneyLog.setTotalMoneyUsd(totalMoneyLog.getTotalMoneyUsd().subtract(new BigDecimal(outUsagingDto.getMoneyCost())));
                    totalMoneyLog.setTotalMoneyUsdGive(totalMoneyLog.getTotalMoneyUsdGive().add(new BigDecimal(outUsagingDto.getMoneyCost())));
                }
                transactionalMoneyRepository.save(transactionalMoney);
                totalMoneyLogRepository.save(totalMoneyLog);
                transactionalMoneyLogService
                        .loggerForTransactionalMoneyLogForOut(
                                transactionalMoney.getId(),
                                outUsagingDto.getMoneyCost(),
                                outUsagingDto.getComment()
                        );
                messageCLassDto.setSuccess(new AtomicReference<>(true));
                messageCLassDto.setMessage(new AtomicReference<>("Chiqim qisman berildi"));
            } else if (transactionalMoney.getPaymentCost().compareTo(transactionalMoney.getGiveCost().add(new BigDecimal(outUsagingDto.getMoneyCost()))) == 0) {
                transactionalMoney.setGiveCost(transactionalMoney.getGiveCost().add(new BigDecimal(outUsagingDto.getMoneyCost())));
                if (transactionalMoney.getPaymentCostType().equals("uzs")) {
                    totalMoneyLog.setTotalMoneyUzs(totalMoneyLog.getTotalMoneyUzs().subtract(transactionalMoney.getPaymentCost()));
                    totalMoneyLog.setTotalMoneyUzsGive(totalMoneyLog.getTotalMoneyUzsGive().add(transactionalMoney.getPaymentCost()));
                } else { // else usd
                    totalMoneyLog.setTotalMoneyUsd(totalMoneyLog.getTotalMoneyUsd().subtract(transactionalMoney.getPaymentCost()));
                    totalMoneyLog.setTotalMoneyUsdGive(totalMoneyLog.getTotalMoneyUsdGive().add(transactionalMoney.getPaymentCost()));
                }
                transactionalMoneyRepository.save(transactionalMoney);
                totalMoneyLogRepository.save(totalMoneyLog);
                transactionalMoneyLogService.loggerForTransactionalMoneyLog(transactionalMoney.getId(), outUsagingDto.getMoneyCost(), outUsagingDto.getComment());

                messageCLassDto.setSuccess(new AtomicReference<>(true));
                messageCLassDto.setMessage(new AtomicReference<>("Chiqim to'liq berildi"));
            } else { //else equal less
                messageCLassDto.setSuccess(new AtomicReference<>(false));
                messageCLassDto.setMessage(new AtomicReference<>("Kiritilgan qiymat chiqim mablag'idan katta!"));
            }
        }
        return messageCLassDto;
    }
    /*todo--> out Money <<*/
    
    /*todo--> plus money or minus money by inOrOut param >>*/
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
    /*todo--> plus money or minus money by inOrOut param <<*/

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
    private Date[] dateRange(String dateString) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date = dateFormat.parse(dateString);

            // Set the time to 00:00:00 for the start of the day
            date.setHours(0);
            date.setMinutes(0);
            date.setSeconds(0);

            // Create the end date by setting the time to 23:59:59
            Date endDate = new Date(date.getTime());
            endDate.setHours(23);
            endDate.setMinutes(59);
            endDate.setSeconds(59);

            // Return an array containing the start and end dates
            return new Date[] { date, endDate };
        } catch (ParseException e) {
            e.printStackTrace();
            return new Date[] { new Date(System.currentTimeMillis()) };
        }
    }

    /**add total money or subtract total money**/
    public MessageCLassDto changeTotalMoney(CashRegister cashRegister){
        MessageCLassDto messageCLassDto = new MessageCLassDto();
        String locationCode = cashRegister.getCashRegister();
        String statusTotalMoneyActive = "1";
        String statusTotalMoneyNotActive = "0";

        Specification<TotalMoney> specification1 = (root, query, criteriaBuilder) -> {
            root.fetch("totalMoneyLogs", JoinType.LEFT);
            Predicate predicate1 = criteriaBuilder.equal(root.get("status"), statusTotalMoneyActive);
            Predicate predicate2 = criteriaBuilder.equal(root.get("status"), statusTotalMoneyNotActive);
            Predicate predicate3 = criteriaBuilder.equal(root.get("insLocationCode"), locationCode);
            Predicate predicateOr = criteriaBuilder.or(predicate1, predicate2);
            return criteriaBuilder.and(predicate3, predicateOr);
        };
        Optional<TotalMoney> totalMoney = totalMoneyRepository.findOne(specification1);

        Boolean isPlus_uzs = !cashRegister.getMinusUzs();
        Boolean isPlus_usd = !cashRegister.getMinusUsd();

        Boolean isComFromTm_uzs = false;
        Boolean isComFromTm_usd = false;

        Boolean isComFromTmLog_uzs = false;
        Boolean isComFromTmLog_usd = false;

        Boolean isTotalMoneyActive_uzs = false;
        Boolean isTotalMoneyActive_usd = false;

        Boolean haveTotalMoney = false;
        Boolean haveTotalMoneyLog = false;

        if (totalMoney.isPresent()){
            TotalMoney totalMoneyOrg = totalMoney.get();
            System.out.println(totalMoneyOrg.getTotalUzs().compareTo(new BigDecimal(cashRegister.getMoneyCostUzs())) > 0);
            isComFromTm_uzs = totalMoneyOrg.getTotalUzs().compareTo(new BigDecimal(cashRegister.getMoneyCostUzs())) > 0;
            isComFromTm_usd = totalMoneyOrg.getTotalUsd().compareTo(new BigDecimal(cashRegister.getMoneyCostUsd())) > 0;
            if (totalMoneyOrg.getStatus().equals("1")){
                isComFromTmLog_uzs = totalMoneyOrg.getTotalMoneyLogs().get(0).getTotalMoneyUzs().compareTo(new BigDecimal(cashRegister.getMoneyCostUzs())) > 0;
                isComFromTmLog_usd = totalMoneyOrg.getTotalMoneyLogs().get(0).getTotalMoneyUsd().compareTo(new BigDecimal(cashRegister.getMoneyCostUsd())) > 0;
                haveTotalMoneyLog = true;
            }
            haveTotalMoney = true;
        }else {
            TotalMoney totalMoneyNew = new TotalMoney();
            totalMoneyNew.setInsLocationCode(cashRegister.getCashRegister());
            totalMoneyNew.setInsLocationName(cashRegister.getCashRegister().equals("01") ? "Toshkent" :  /*else 95*/ "Mang'it");
            totalMoneyNew.setTotalUzs(new BigDecimal(cashRegister.getMoneyCostUzs()));
            totalMoneyNew.setTotalUsd(new BigDecimal(cashRegister.getMoneyCostUsd()));
            totalMoneyRepository.save(totalMoneyNew);

            messageCLassDto.setSuccess(new AtomicReference<>(true));
            messageCLassDto.setMessage(new AtomicReference<>("Kassa ma'lumotlari yangi kiritildi"));
        }

        /**************************************************************************************************************/

        if (haveTotalMoney){
            if (isPlus_uzs && isPlus_usd){
                if (haveTotalMoneyLog) {
                    TotalMoneyLog totalMoneyLog = totalMoney.get().getTotalMoneyLogs().get(0);
                    TotalMoney totalMoneyUpdate = totalMoney.get();

                    totalMoneyLog.setTotalMoneyUzs(totalMoneyLog.getTotalMoneyUzs().add(new BigDecimal(cashRegister.getMoneyCostUzs())));
                    totalMoneyLog.setTotalMoneyUsd(totalMoneyLog.getTotalMoneyUsd().add(new BigDecimal(cashRegister.getMoneyCostUsd())));

                    totalMoneyUpdate.setTotalUzs(totalMoneyUpdate.getTotalUzs().add(new BigDecimal(cashRegister.getMoneyCostUzs())));
                    totalMoneyUpdate.setTotalUsd(totalMoneyUpdate.getTotalUsd().add(new BigDecimal(cashRegister.getMoneyCostUsd())));

                    totalMoneyLogRepository.save(totalMoneyLog);
                    totalMoneyRepository.save(totalMoneyUpdate);

                    messageCLassDto.setSuccess(new AtomicReference<>(true));
                    messageCLassDto.setMessage(new AtomicReference<>("Kassa ma'lumotlari o'zgartirildi"));
                }else {
                    TotalMoney totalMoneyUpdate = totalMoney.get();

                    totalMoneyUpdate.setTotalUzs(totalMoneyUpdate.getTotalUzs().add(new BigDecimal(cashRegister.getMoneyCostUzs())));
                    totalMoneyUpdate.setTotalUsd(totalMoneyUpdate.getTotalUsd().add(new BigDecimal(cashRegister.getMoneyCostUsd())));

                    totalMoneyRepository.save(totalMoneyUpdate);

                    messageCLassDto.setSuccess(new AtomicReference<>(true));
                    messageCLassDto.setMessage(new AtomicReference<>("Kassa ma'lumotlari o'zgartirildi"));
                }
            }else {
                if (haveTotalMoneyLog) {
                    if (isComFromTm_uzs && isComFromTm_usd && isComFromTmLog_uzs && isComFromTmLog_usd){
                        TotalMoneyLog totalMoneyLog = totalMoney.get().getTotalMoneyLogs().get(0);
                        TotalMoney totalMoneyUpdate = totalMoney.get();

                        if (isPlus_uzs){
                            totalMoneyUpdate.setTotalUzs(totalMoneyUpdate.getTotalUzs().add(new BigDecimal(cashRegister.getMoneyCostUzs())));
                            totalMoneyLog.setTotalMoneyUzs(totalMoneyLog.getTotalMoneyUzs().add(new BigDecimal(cashRegister.getMoneyCostUzs())));
                        }else {
                            totalMoneyUpdate.setTotalUzs(totalMoneyUpdate.getTotalUzs().subtract(new BigDecimal(cashRegister.getMoneyCostUzs())));
                            totalMoneyLog.setTotalMoneyUzs(totalMoneyLog.getTotalMoneyUzs().subtract(new BigDecimal(cashRegister.getMoneyCostUzs())));
                        }

                        if (isPlus_usd){
                            totalMoneyUpdate.setTotalUsd(totalMoneyUpdate.getTotalUsd().add(new BigDecimal(cashRegister.getMoneyCostUsd())));
                            totalMoneyLog.setTotalMoneyUsd(totalMoneyLog.getTotalMoneyUsd().add(new BigDecimal(cashRegister.getMoneyCostUsd())));
                        }else {
                            totalMoneyUpdate.setTotalUsd(totalMoneyUpdate.getTotalUsd().subtract(new BigDecimal(cashRegister.getMoneyCostUsd())));
                            totalMoneyLog.setTotalMoneyUsd(totalMoneyLog.getTotalMoneyUsd().subtract(new BigDecimal(cashRegister.getMoneyCostUsd())));
                        }

                        totalMoneyRepository.save(totalMoneyUpdate);
                        totalMoneyLogRepository.save(totalMoneyLog);

                        messageCLassDto.setSuccess(new AtomicReference<>(true));
                        messageCLassDto.setMessage(new AtomicReference<>("Kassa ma'lumotlari o'zgartirildi"));
                    }else {
                        messageCLassDto.setSuccess(new AtomicReference<>(false));
                        messageCLassDto.setMessage(new AtomicReference<>("Kassa mablag'i yetarli emas!"));
                    }
                } else {
                    if (isComFromTm_uzs && isComFromTm_usd){
                        TotalMoney totalMoneyUpdate = totalMoney.get();

                        if (isPlus_uzs){
                            totalMoneyUpdate.setTotalUzs(totalMoneyUpdate.getTotalUzs().add(new BigDecimal(cashRegister.getMoneyCostUzs())));
                        }else {
                            totalMoneyUpdate.setTotalUzs(totalMoneyUpdate.getTotalUzs().subtract(new BigDecimal(cashRegister.getMoneyCostUzs())));
                        }

                        if (isPlus_usd){
                            totalMoneyUpdate.setTotalUsd(totalMoneyUpdate.getTotalUsd().add(new BigDecimal(cashRegister.getMoneyCostUsd())));
                        }else {
                            totalMoneyUpdate.setTotalUsd(totalMoneyUpdate.getTotalUsd().subtract(new BigDecimal(cashRegister.getMoneyCostUsd())));
                        }

                        totalMoneyRepository.save(totalMoneyUpdate);

                        messageCLassDto.setSuccess(new AtomicReference<>(true));
                        messageCLassDto.setMessage(new AtomicReference<>("Kassa ma'lumotlari o'zgartirildi"));
                    }else {
                        messageCLassDto.setSuccess(new AtomicReference<>(false));
                        messageCLassDto.setMessage(new AtomicReference<>("Kassa mablag'i yetarli emas!"));
                    }
                }
            }
        }

        return messageCLassDto;
    }
}
