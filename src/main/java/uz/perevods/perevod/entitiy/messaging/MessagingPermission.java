package uz.perevods.perevod.entitiy.messaging;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import uz.perevods.perevod.component.entityComponents.AbstractAuditingEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class MessagingPermission{
    @Id
    @GeneratedValue(generator = "uuid4")
    @GenericGenerator(name = "uuid4", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", columnDefinition = "VARCHAR(50)")
    private String id;

    @Column(name = "user_id", columnDefinition = "VARCHAR(50)")
    private String userId;

    @Column(name = "send_log")
    private Boolean sendLog;

    @Column(name = "send_log_chat_id")
    private String sendLogChatId;

    @Column(name = "send_change_location")
    private Boolean sendChangeLocation;

    @Column(name = "send_change_chat_id")
    private String sendChangeChatId;

    @Column(name = "send_new_message")
    private Boolean sendNewMessage;

    @Column(name = "send_new_chat_id")
    private String sendNewChatId;

    @Column(name = "send_debt_reminder")
    private Boolean sendDebtReminder;

    @Column(name = "send_debt_chat_id")
    private String sendDebtChatId;
}
