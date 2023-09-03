package uz.perevods.perevod.service.application;

import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import uz.perevods.perevod.entitiy.application.TotalMoney;
import uz.perevods.perevod.entitiy.application.TotalMoneyLog;
import uz.perevods.perevod.entitiy.application.TransactionalMoney;
import uz.perevods.perevod.entitiy.authorization.Users;
import uz.perevods.perevod.repository.application.*;
import uz.perevods.perevod.service.helperClass.DebtUsagingDto;
import uz.perevods.perevod.service.helperClass.MessageCLassDto;
import uz.perevods.perevod.service.helperClass.TransactionalMoneyDebtDto;

import javax.persistence.criteria.Fetch;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

@Service
@RequiredArgsConstructor
public class DebtService {
    private final TransactionalMoneyRepository transactionalMoneyRepository;
    private final TransactionalMoneyDataRepository transactionalMoneyDataRepository;
    private final TransactionalMoneyLogService transactionalMoneyLogService;
    private final TotalMoneyRepository totalMoneyRepository;
    private final TotalMoneyLogRepository totalMoneyLogRepository;

    public List<TransactionalMoneyDebtDto> getData2(Users users, UserDetails userDetails, Boolean seeAll){
        String toLocation = "";

        if (
//                userDetails.getAuthorities().stream()
//                .anyMatch(role -> role.getAuthority().equals("ROLE_admin") || role.getAuthority().equals("ROLE_programmer")) &&
                        seeAll
        ) {
            toLocation = null;
        }
        else {
            toLocation = users.getLocationCode();
        }
        List<TransactionalMoneyDebtDto> transactionalMoneyDebtDtos = new ArrayList<>();

        Optional<TransactionalMoneyDebtDto> transactionalMoneyUzs = transactionalMoneyRepository.findByQuery1(toLocation, "uzs");
        Optional<TransactionalMoneyDebtDto> transactionalMoneyUsd = transactionalMoneyRepository.findByQuery1(toLocation, "usd");
        transactionalMoneyDebtDtos.add(transactionalMoneyUzs.orElseGet(() -> new TransactionalMoneyDebtDto(0L, new BigDecimal(0), new BigDecimal(0), new BigDecimal(0))));
        transactionalMoneyDebtDtos.add(transactionalMoneyUsd.orElseGet(() ->  new TransactionalMoneyDebtDto(0L, new BigDecimal(0), new BigDecimal(0), new BigDecimal(0))));
        return transactionalMoneyDebtDtos;
    }

    public DataTablesOutput<TransactionalMoney> getData3(
            DataTablesInput tablesInput,
            UserDetails userDetails,
            Users users,
            Boolean seeAll,
            String seeAllDebtType,
            String costType
            ) {
        return transactionalMoneyDataRepository.findAll(tablesInput, (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            predicates.add(criteriaBuilder.equal(root.get("debt"), true));

            if (seeAllDebtType.equals("11")) {
                predicates.add(criteriaBuilder.equal(root.get("paymentCost"), root.get("payedCost")));
            } else if (seeAllDebtType.equals("12")) {
                predicates.add(criteriaBuilder.equal(root.get("payedCost"), BigDecimal.ZERO));
            } else if (seeAllDebtType.equals("13")) {
                predicates.add(criteriaBuilder.and(
                        criteriaBuilder.greaterThan(root.get("paymentCost"), root.get("payedCost")),
                        criteriaBuilder.greaterThan(root.get("payedCost"), BigDecimal.ZERO)
                )); }

            if (!costType.equals("0")){
                predicates.add(criteriaBuilder.equal(root.get("paymentCostType"), costType));
            }

            if (
//                    userDetails.getAuthorities().stream()
//                    .anyMatch(role -> role.getAuthority().equals("ROLE_admin") || role.getAuthority().equals("ROLE_programmer")) &&
                            seeAll) {
                return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
            }

            predicates.add(criteriaBuilder.equal(root.get("insUser"), users.getUsername()));
            predicates.add(criteriaBuilder.notEqual(root.get("insLocationCode"), users.getLocationCode()));

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        });
    }

    public MessageCLassDto setData1(@Valid DebtUsagingDto debtUsagingDto, Users users) {
        Specification<TransactionalMoney> specification1 = (root, query, criteriaBuilder) -> {
            Fetch<TransactionalMoney, TotalMoney> totalMoneyFetch = root.fetch("totalMoney", JoinType.LEFT);
            totalMoneyFetch.fetch("totalMoneyLogs", JoinType.LEFT);
            Predicate predicate1 = criteriaBuilder.equal(root.get("id"), debtUsagingDto.getTransactionalMoneyId());
            Predicate predicate2 = criteriaBuilder.equal(root.get("debt"), true);
            return criteriaBuilder.and(predicate1, predicate2);
        };

        Optional<TransactionalMoney> transactionalMoney = transactionalMoneyRepository.findOne(specification1);
        MessageCLassDto messageCLassDto = new MessageCLassDto();
        transactionalMoney.ifPresentOrElse(
                transactionalMoney1 -> {
                    if (transactionalMoney1.getTotalMoney().getStatus().equals("2")){
                        Specification<TotalMoney> specification2 = (root, query, criteriaBuilder) -> {
                            root.fetch("totalMoneyLogs", JoinType.LEFT);
                            Predicate predicate21 = criteriaBuilder.equal(root.get("insLocationCode"), users.getLocationCode());
                            Predicate predicate22 = criteriaBuilder.equal(root.get("status"), "1"); // status 1 = active
                            return criteriaBuilder.and(predicate21, predicate22);
                        };
                        Optional<TotalMoney> totalMoney = totalMoneyRepository.findOne(specification2);
                        totalMoney.ifPresentOrElse(
                                totalMoney1 -> {
                                    transactionalMoney1.setTotalMoney(totalMoney1);
                                    messageCLassDto.setSuccess(new AtomicReference<>(true));
                                },
                                () -> {
                                    messageCLassDto.setSuccess(new AtomicReference<>(false));
                                    messageCLassDto.setMessage(new AtomicReference<>("Qarz so'ndirish uchun aktiv kassa mavjud emas!"));
                                }
                        );
                    }else {
                        messageCLassDto.setSuccess(new AtomicReference<>(true));
                    }
                    if (messageCLassDto.getSuccess().get()){
                        if (transactionalMoney1.getTotalMoney().getInsLocationCode().equals(users.getLocationCode())){
                            if (debtUsagingDto.getPayAll()){
                                TotalMoneyLog totalMoneyLog = transactionalMoney1.getTotalMoney().getTotalMoneyLogs().get(0);
                                if (transactionalMoney1.getPaymentCostType().equals("uzs")){
                                    totalMoneyLog.setTotalMoneyUzs(totalMoneyLog.getTotalMoneyUzs().add(transactionalMoney1.getPaymentCost().subtract(transactionalMoney1.getPayedCost())));
                                    totalMoneyLog.setTotalMoneyUzsGive(totalMoneyLog.getTotalMoneyUzsGive().add(transactionalMoney1.getPaymentCost().subtract(transactionalMoney1.getPayedCost())));
                                }else { // else usd
                                    totalMoneyLog.setTotalMoneyUsd(totalMoneyLog.getTotalMoneyUsd().add(transactionalMoney1.getPaymentCost().subtract(transactionalMoney1.getPayedCost())));
                                    totalMoneyLog.setTotalMoneyUsdGive(totalMoneyLog.getTotalMoneyUsdGive().add(transactionalMoney1.getPaymentCost().subtract(transactionalMoney1.getPayedCost())));
                                }
                                totalMoneyLogRepository.save(totalMoneyLog);
                                transactionalMoneyLogService
                                        .loggerForTransactionalMoneyLog(
                                                transactionalMoney1.getId(),
                                                transactionalMoney1.getPaymentCost().compareTo(transactionalMoney1.getPayedCost()) == 0 ? String.valueOf(transactionalMoney1.getPayedCost()) : String.valueOf(transactionalMoney1.getPaymentCost().subtract(transactionalMoney1.getPayedCost())),
                                                debtUsagingDto.getComment()
                                        );
                                transactionalMoney1.setPayedCost(transactionalMoney1.getPaymentCost());
                                transactionalMoneyRepository.save(transactionalMoney1);

                                messageCLassDto.setSuccess(new AtomicReference<>(true));
                                messageCLassDto.setMessage(new AtomicReference<>("Qarz to'liq so'ndirildi"));
                            }else { // else payAll false
                                if (transactionalMoney1.getPaymentCost().compareTo(transactionalMoney1.getPayedCost().add(new BigDecimal(debtUsagingDto.getMoneyCost()))) > 0){
                                    transactionalMoney1.setPayedCost(transactionalMoney1.getPayedCost().add(new BigDecimal(debtUsagingDto.getMoneyCost())));
                                    TotalMoneyLog totalMoneyLog = transactionalMoney1.getTotalMoney().getTotalMoneyLogs().get(0);
                                    if (transactionalMoney1.getPaymentCostType().equals("uzs")){
                                        totalMoneyLog.setTotalMoneyUzs(totalMoneyLog.getTotalMoneyUzs().add(new BigDecimal(debtUsagingDto.getMoneyCost())));
                                        totalMoneyLog.setTotalMoneyUzsGive(totalMoneyLog.getTotalMoneyUzsGive().add(new BigDecimal(debtUsagingDto.getMoneyCost())));
                                    }else { // else usd
                                        totalMoneyLog.setTotalMoneyUsd(totalMoneyLog.getTotalMoneyUsd().add(new BigDecimal(debtUsagingDto.getMoneyCost())));
                                        totalMoneyLog.setTotalMoneyUsdGive(totalMoneyLog.getTotalMoneyUsdGive().add(new BigDecimal(debtUsagingDto.getMoneyCost())));
                                    }
                                    transactionalMoneyRepository.save(transactionalMoney1);
                                    totalMoneyLogRepository.save(totalMoneyLog);
                                    transactionalMoneyLogService.loggerForTransactionalMoneyLog(transactionalMoney1.getId(), debtUsagingDto.getMoneyCost(), debtUsagingDto.getComment());

                                    messageCLassDto.setSuccess(new AtomicReference<>(true));
                                    messageCLassDto.setMessage(new AtomicReference<>("Qarz qisman so'ndirildi"));
                                }else if (transactionalMoney1.getPaymentCost().compareTo(transactionalMoney1.getPayedCost().add(new BigDecimal(debtUsagingDto.getMoneyCost()))) == 0){
                                    transactionalMoney1.setPayedCost(transactionalMoney1.getPayedCost().add(new BigDecimal(debtUsagingDto.getMoneyCost())));
                                    TotalMoneyLog totalMoneyLog = transactionalMoney1.getTotalMoney().getTotalMoneyLogs().get(0);
                                    if (transactionalMoney1.getPaymentCostType().equals("uzs")){
                                        totalMoneyLog.setTotalMoneyUzs(totalMoneyLog.getTotalMoneyUzs().add(transactionalMoney1.getPaymentCost()));
                                        totalMoneyLog.setTotalMoneyUzsGive(totalMoneyLog.getTotalMoneyUzsGive().add(transactionalMoney1.getPaymentCost()));
                                    }else { // else usd
                                        totalMoneyLog.setTotalMoneyUsd(totalMoneyLog.getTotalMoneyUsd().add(transactionalMoney1.getPaymentCost()));
                                        totalMoneyLog.setTotalMoneyUsdGive(totalMoneyLog.getTotalMoneyUsdGive().add(transactionalMoney1.getPaymentCost()));
                                    }
                                    transactionalMoneyRepository.save(transactionalMoney1);
                                    totalMoneyLogRepository.save(totalMoneyLog);
                                    transactionalMoneyLogService.loggerForTransactionalMoneyLog(transactionalMoney1.getId(), debtUsagingDto.getMoneyCost(), debtUsagingDto.getComment());

                                    messageCLassDto.setSuccess(new AtomicReference<>(true));
                                    messageCLassDto.setMessage(new AtomicReference<>("Qarz to'liq so'ndirildi"));
                                }else { //else equal less
                                    messageCLassDto.setSuccess(new AtomicReference<>(false));
                                    messageCLassDto.setMessage(new AtomicReference<>("Kiritilgan qiymat qarz mablag'idan katta!"));
                                }
                            }
                        }else {
                            messageCLassDto.setSuccess(new AtomicReference<>(false));
                            messageCLassDto.setMessage(new AtomicReference<>("Siz ushbu qarizdorlikni undira olmaysiz!"));
                        }
                    }
                },
                () -> {
                    messageCLassDto.setSuccess(new AtomicReference<>(false));
                    messageCLassDto.setMessage(new AtomicReference<>("Bunday qarzdorlik topilmadi!"));
                }
        );

        return messageCLassDto;
    }


}
