package uz.perevods.perevod.service.helperClass;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.concurrent.atomic.AtomicReference;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class MessageCLassDtoSimple {
    private String message;
    private Boolean success;
}
