package uz.perevods.perevod.service.helperClass;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class TransactionalMoneyDto {

    @NotBlank(message = "Bo'sh bo'lishi mumkun emas")
    @Size(max = 60, message = "Ma'lumot xajmi katta")
    private String fullName;

    @Size(min = 12, max = 12, message = "Xato to'ldirilgan")
    private String telNumber;

    @Pattern(regexp = "^\\d{1,15}(\\.\\d{1,2})?$|.{1,12}", message = "Format yoki xajm xato")
    private String moneyCost;

    @NotBlank
    private String moneyType;

    @Pattern(regexp = "^\\d{0,15}(\\.\\d{1,2})?$|.{1,12}", message = "Format yoki xajm xato")
    private String serviceMoney;

    @NotBlank
    private String sendToAddress;

    private Boolean isDebt;

    private String comment;

}
