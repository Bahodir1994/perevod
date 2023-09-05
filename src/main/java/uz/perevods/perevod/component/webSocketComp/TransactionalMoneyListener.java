package uz.perevods.perevod.component.webSocketComp;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import uz.perevods.perevod.entitiy.application.TransactionalMoney;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import javax.persistence.PostUpdate;

@Component
@RequiredArgsConstructor
public class TransactionalMoneyListener {


//    private final SimpMessagingTemplate simpMessagingTemplate;
//
//    @PostUpdate
//    public void handleUpdate(TransactionalMoney transactionalMoney){
//        simpMessagingTemplate.convertAndSend("/topic/myentity-updates", "Entity updated: " + transactionalMoney.getId());
//    }
}
