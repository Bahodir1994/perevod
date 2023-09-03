package uz.perevods.perevod.component.mappers;

import org.springframework.stereotype.Component;
import uz.perevods.perevod.service.helperClass.TransactionalMoneyDebtDto;

import javax.persistence.*;
import java.math.BigDecimal;

@Component
@SqlResultSetMappings({
        @SqlResultSetMapping(
                name = "TransactionalMoneyDebtDtoMapping",
                classes = @ConstructorResult(
                        targetClass = TransactionalMoneyDebtDto.class,
                        columns = {
                                @ColumnResult(name = "soni", type = Long.class),
                                @ColumnResult(name = "jami", type = BigDecimal.class),
                                @ColumnResult(name = "undirildi", type = BigDecimal.class),
                                @ColumnResult(name = "qoldiq", type = BigDecimal.class)
                        }
                )
        )
})
public class SqlResultMappings {
}

