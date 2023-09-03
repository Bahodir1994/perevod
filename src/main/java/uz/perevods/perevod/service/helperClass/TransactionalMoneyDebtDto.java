package uz.perevods.perevod.service.helperClass;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class TransactionalMoneyDebtDto {
    private Long soni;
    private BigDecimal jami;
    private BigDecimal undirildi;
    private BigDecimal qoldiq;
}
