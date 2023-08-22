package uz.perevods.perevod.entitiy.application;

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
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "total_money")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class TotalMoney extends AbstractAuditingEntity {
    @Id
    @GeneratedValue(generator = "uuid4")
    @GenericGenerator(name = "uuid4", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "ID", columnDefinition = "VARCHAR(50)")
    private String id;

    @OneToMany(mappedBy = "totalMoney", cascade = CascadeType.ALL)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<TotalMoneyLog> totalMoneyLogs;

    @OneToMany(mappedBy = "totalMoney", cascade = CascadeType.ALL)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<TransactionalMoney> transactionalMoneyList;

    @Column(name = "total_uzs", length = 50)
    private BigDecimal totalUzs;

    @Column(name = "total_usd", length = 50)
    private BigDecimal totalUsd;

    @Column(name = "ins_locationCode", length = 50)
    private String insLocationCode;

    @Column(name = "ins_locationName", length = 50)
    private String insLocationName;
}
