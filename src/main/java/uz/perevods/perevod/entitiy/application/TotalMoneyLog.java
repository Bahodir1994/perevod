package uz.perevods.perevod.entitiy.application;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.format.annotation.DateTimeFormat;
import uz.perevods.perevod.component.entityComponents.AbstractAuditingEntity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "total_money_log")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class TotalMoneyLog extends AbstractAuditingEntity {
    @Id
    @GeneratedValue(generator = "uuid4")
    @GenericGenerator(name = "uuid4", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "ID", columnDefinition = "VARCHAR(50)")
    private String id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "total_money_id", insertable = false, updatable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private TotalMoney totalMoney;

    @Column(name = "total_money_id", length = 50)
    private String totalMoneyId;

    @Column(name = "total_money_uzs", length = 50)
    private BigDecimal totalMoneyUzs;

    @Column(name = "total_money_uzs_give", length = 50)
    private BigDecimal totalMoneyUzsGive;

    @Column(name = "total_money_usd", length = 50)
    private BigDecimal totalMoneyUsd;

    @Column(name = "total_money_usd_give", length = 50)
    private BigDecimal totalMoneyUsdGive;

    @Column(name = "work_date", columnDefinition = "date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.DATE)
    private Date workDate;

    @Column(name = "status", length = 5)
    private String status;
}
