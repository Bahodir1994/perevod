package uz.perevods.perevod.entitiy.application;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.data.annotation.CreatedDate;
import uz.perevods.perevod.component.entityComponents.AbstractAuditingEntity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


@Entity
@Table(name = "transactional_money")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class TransactionalMoney extends AbstractAuditingEntity {
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

    @Column(name = "payment_cost_type", length = 30)
    private String paymentCostType;

    @Column(name = "payment_cost", length = 50)
    private BigDecimal paymentCost;

    @Column(name = "service_uzs", length = 50)
    private BigDecimal serviceUzs;

    @Column(name = "full_name", unique = true, length = 300)
    private String fullName;

    @Column(name = "phone", length = 20)
    private String phone;

    @Column(name = "in_time", columnDefinition = "timestamp")
    @Temporal(TemporalType.TIMESTAMP)
    private Date inTime;

    @Column(name = "out_time", columnDefinition = "timestamp")
    @Temporal(TemporalType.TIMESTAMP)
    private Date outTime;

    @Column(name = "debt")
    private Boolean debt;

    @Column(name = "comment", length = 600)
    private String comment;

    @Column(name = "to_locationCode", length = 50)
    private String insLocationCode;

    @Column(name = "to_locationName", length = 50)
    private String insLocationName;

}
