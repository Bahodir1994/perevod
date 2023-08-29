package uz.perevods.perevod.service.helperClass;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@NoArgsConstructor
@AllArgsConstructor                         
@Getter
@Setter
public class CashRegister {
    @Pattern(regexp = "\\d{1,15}(\\.\\d{1,2})?", message = "Format yoki xajm xato!")
    private String moneyCostUzs;

    @Pattern(regexp = "\\d{1,15}(\\.\\d{1,2})?", message = "Format yoki xajm xato!")
    private String moneyCostUsd;

    @NotNull(message = "Xato ma'lumot")
    private Boolean minusUzs;

    @NotNull(message = "Xato ma'lumot")
    private Boolean minusUsd;

    @NotBlank
    private String cashRegister;

    @Size(max = 250, message = "Ma'lumot xajmi katta!")
    private String comment;
}
