package uz.perevods.perevod.service.application;

import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import uz.perevods.perevod.entitiy.application.TotalMoney;
import uz.perevods.perevod.entitiy.application.TotalMoneyLog;
import uz.perevods.perevod.entitiy.authorization.Users;
import uz.perevods.perevod.repository.application.TotalMoneyLogRepository;
import uz.perevods.perevod.repository.application.TotalMoneyRepository;
import uz.perevods.perevod.service.helperClass.MessageCLassDto;

import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

@Service
@RequiredArgsConstructor
public class JobCheckerService {
    private final TotalMoneyRepository totalMoneyRepository;
    private final TotalMoneyLogRepository totalMoneyLogRepository;

    public boolean getJobStatus(Users users){
        AtomicBoolean isActiveTodayJob = new AtomicBoolean(false);
        String userLocation = users.getLocationCode();
        Optional<TotalMoney> totalMoney = totalMoneyRepository.findByInsLocationCodeAndStatus(userLocation, "1");
        totalMoney.ifPresentOrElse(
                totalMoney1 -> {
                    isActiveTodayJob.set(true);
                },
                () -> {
                    isActiveTodayJob.set(false);
                }
        );
        return isActiveTodayJob.get();
    }

    /**Get day data if work not start (status == 0)**/
    public TotalMoney getJobData1(Users users){
        String statusNotStart = "0";
        Specification<TotalMoney> specification = (root, query, criteriaBuilder) -> {
            root.fetch("totalMoneyLogs", JoinType.LEFT);
            root.fetch("transactionalMoneyList", JoinType.LEFT);
            Predicate predicate1 = criteriaBuilder.equal(root.get("insLocationCode"), users.getLocationCode());
            Predicate predicate2 = criteriaBuilder.equal(root.get("status"), statusNotStart);

            return criteriaBuilder.and(predicate1, predicate2);
        };
        Optional<TotalMoney> totalMoney = totalMoneyRepository.findOne(specification);
        Map<String, BigDecimal> bigDecimalMap = new HashMap<>();
        bigDecimalMap.put("totalUzs", BigDecimal.ZERO);
        bigDecimalMap.put("totalUsd", BigDecimal.ZERO);
        if (totalMoney.isPresent()){
            return totalMoney.get();
        }else {
            TotalMoney totalMoney1 = new TotalMoney();
            totalMoney1.setTotalUzs(BigDecimal.ZERO);
            totalMoney1.setTotalUsd(BigDecimal.ZERO);
            return totalMoney1;
        }
    }

    /**Get day data if work not start for Admin (status == 0)**/
    public TotalMoney getJobData2(String locationId){
        String statusNotStart = "0";
        String statusStart = "1";
        Specification<TotalMoney> specification = (root, query, criteriaBuilder) -> {
            root.fetch("totalMoneyLogs", JoinType.LEFT);
            root.fetch("transactionalMoneyList", JoinType.LEFT);
            Predicate predicate1 = criteriaBuilder.equal(root.get("insLocationCode"), locationId);
            Predicate predicate2 = criteriaBuilder.equal(root.get("status"), statusNotStart);
            Predicate predicate3 = criteriaBuilder.equal(root.get("status"), statusStart);
            Predicate predicateOr = criteriaBuilder.or(predicate2, predicate3);

            return criteriaBuilder.and(predicate1, predicateOr);
        };
        Optional<TotalMoney> totalMoney = totalMoneyRepository.findOne(specification);
        return totalMoney.orElseGet(TotalMoney::new);
    }

    /**Start job day cash**/
    public MessageCLassDto setTotalMoney(Users users){
        AtomicReference<String> message = new AtomicReference<>("Hisob mavjud emas!");
        AtomicReference<Boolean> success = new AtomicReference<>(false);
        String statusNotStart = "0";
        String statusStart = "1";
        totalMoneyRepository.findByInsLocationCodeAndStatus(users.getLocationCode(), statusNotStart).ifPresent(
                totalMoney -> {
                    if (totalMoney.getTotalUzs().compareTo(new BigDecimal(50000)) > 0 || totalMoney.getTotalUsd().compareTo(new BigDecimal(50)) > 0){
                        totalMoney.setStatus(statusStart); /*job starting*/
                        totalMoney.setStartTime(new Date()); /*job starting*/
                        totalMoney.setInsLocationCode(users.getLocationCode()); /*set job loc code*/
                        totalMoney.setInsLocationName(users.getLocationName()); /*set job loc name*/
                        totalMoneyRepository.save(totalMoney);

                        TotalMoneyLog totalMoneyLog = new TotalMoneyLog();
                        totalMoneyLog.setTotalMoneyId(totalMoney.getId()); /*relating with totalMoney*/
                        totalMoneyLog.setStatus("1"); /*relating with totalMoney*/ /*1-active, 2-finished*/
                        totalMoneyLog.setTotalMoneyUzs(totalMoney.getTotalUzs()); /*set money uzs from strt job*/
                        totalMoneyLog.setTotalMoneyUzsGive(new BigDecimal(0)); /*set money uzs kash*/
                        totalMoneyLog.setTotalMoneyUsd(totalMoney.getTotalUsd()); /*set money usd from strt job*/
                        totalMoneyLog.setTotalMoneyUsdGive(new BigDecimal(0)); /*set money usd kash*/
                        totalMoneyLog.setWorkDate(new Date()); /*set work day*/
                        totalMoneyLogRepository.save(totalMoneyLog);

                        success.set(true);
                        message.set("Kunlik mablag' yetarli va ish boshlandi!");
                    }else {
                        message.set("Hisobda yetarli mablag' mavjud emas!");
                    }
                }
        );
        return new MessageCLassDto(
                message,
                success
        );
    }

    /**Finish job day cash**/
    public MessageCLassDto finishJobDay(Users users){
        MessageCLassDto messageCLassDto = new MessageCLassDto();
        String statusStart = "1";
        Specification<TotalMoney> specification = (root, query, criteriaBuilder) -> {
            root.fetch("totalMoneyLogs", JoinType.LEFT);
            root.fetch("transactionalMoneyList", JoinType.LEFT);
            Predicate predicate1 = criteriaBuilder.equal(root.get("insLocationCode"), users.getLocationCode());
            Predicate predicate2 = criteriaBuilder.equal(root.get("status"), statusStart);

            return criteriaBuilder.and(predicate1, predicate2);
        };
        Optional<TotalMoney> totalMoney = totalMoneyRepository.findOne(specification);
        totalMoney.ifPresentOrElse(
                totalMoney1 -> {
                    totalMoney1.setStatus("2");
                    totalMoney1.setFinishTime(new Date(System.currentTimeMillis()));
                    totalMoneyRepository.save(totalMoney1);

                    TotalMoneyLog totalMoneyLog = totalMoney1.getTotalMoneyLogs().get(0);
                    totalMoneyLog.setStatus("2");
                    totalMoneyLogRepository.save(totalMoneyLog);

                    TotalMoney totalMoney2 = new TotalMoney();
                    totalMoney2.setTotalUzs(totalMoneyLog.getTotalMoneyUzs());
                    totalMoney2.setTotalUsd(totalMoneyLog.getTotalMoneyUsd());
                    totalMoney2.setInsLocationCode(totalMoney1.getInsLocationCode());
                    totalMoney2.setInsLocationName(totalMoney1.getInsLocationName());
                    totalMoneyRepository.save(totalMoney2);

                    messageCLassDto.setSuccess(new AtomicReference<>(true));
                    messageCLassDto.setMessage(new AtomicReference<>("Mazkur hisob raqam yopildi!"));
                },
                () -> {
                    messageCLassDto.setSuccess(new AtomicReference<>(false));
                    messageCLassDto.setMessage(new AtomicReference<>("Yopish uchun hisob raqam mavjud emas!"));
                }
        );
        return messageCLassDto;
    }

}
