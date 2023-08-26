package uz.perevods.perevod.controller.application;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class TransactionalMoneyDto {

    @NotEmpty
    private String fullName;

    @NotEmpty
    private String telNumber;

    @NotEmpty
    private String moneyCost;

    @NotEmpty
    private String moneyType;

    private String serviceMoney;

    @NotEmpty
    private String sendToAddress;

    private Boolean isDebt;

    private String comment;
}
