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
public class MessageCLassDto {
    private AtomicReference<String> message;
    private AtomicReference<Boolean> success;
    private AtomicReference<String> messageSecond;
}
