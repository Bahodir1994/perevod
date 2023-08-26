package uz.perevods.perevod.service.application;

import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import uz.perevods.perevod.entitiy.application.TotalMoney;
import uz.perevods.perevod.entitiy.application.TotalMoneyLog;
import uz.perevods.perevod.entitiy.authorization.Users;
import uz.perevods.perevod.repository.application.TotalMoneyLogRepository;
import uz.perevods.perevod.repository.application.TotalMoneyRepository;

import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import java.math.BigDecimal;
import java.util.Date;
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
        TotalMoney totalMoney = totalMoneyRepository.findOne(specification);
        return totalMoney;
    }


    public void setTotalMoney(Users users){
        AtomicReference<String> message = null;
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
                        message.set("Kunlik mablag' yetarli va ish boshlandi");
                    }
                }
        );
    }

}
