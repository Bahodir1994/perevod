package uz.perevods.perevod.service.helperClass;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class TransactionalMoneyDto {

    @NotBlank(message = "Bo'sh bo'lishi mumkun emas")
    private String fullName;

    @NotBlank(message = "Bo'sh bo'lishi mumkun emas")
    private String telNumber;

    @Pattern(regexp = "\\d+(\\.\\d{1,2})?", message = "Format xato")
    private String moneyCost;

    @NotBlank
    private String moneyType;

    @Pattern(regexp = "\\d+(\\.\\d{1,2})?", message = "Format xato")
    private String serviceMoney;

    @NotBlank
    private String sendToAddress;

    private Boolean isDebt;

    private String comment;

}
