package uz.perevods.perevod.entitiy.application;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import uz.perevods.perevod.component.entityComponents.AbstractAuditingEntity;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "transactional_money_log")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class TransactionalMoneyLog extends AbstractAuditingEntity {

    @Id
    @GeneratedValue(generator = "uuid4")
    @GenericGenerator(name = "uuid4", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "ID", columnDefinition = "VARCHAR(50)")
    private String id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "transactional_money_log_id", insertable = false, updatable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private TransactionalMoney transactionalMoney;

    @Column(name = "transactional_money_log_id", length = 50)
    private String transactionalMoneyLogId;

    @Column(name = "payed_cost", length = 50)
    private BigDecimal payedCost;

    @Column(name = "comment", length = 600)
    private String comment;
}
