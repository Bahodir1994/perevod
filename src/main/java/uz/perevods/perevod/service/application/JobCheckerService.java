package uz.perevods.perevod.service.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.perevods.perevod.entitiy.application.TotalMoney;
import uz.perevods.perevod.entitiy.authorization.Users;
import uz.perevods.perevod.repository.application.TotalMoneyRepository;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;

@Service
@RequiredArgsConstructor
public class JobCheckerService {
    private final TotalMoneyRepository totalMoneyRepository;

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

}
