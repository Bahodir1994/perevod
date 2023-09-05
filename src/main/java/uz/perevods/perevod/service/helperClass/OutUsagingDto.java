package uz.perevods.perevod.service.helperClass;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class OutUsagingDto {
    private String transactionalMoneyId;
    private String moneyCost;
    private Boolean payAll;
    private String comment;
}
