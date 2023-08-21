package uz.perevods.perevod.entitiy.authorization;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.util.Date;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "log", schema = "PUBLIC", indexes = {@Index(columnList = "ses_id")})
public class Log {

    @Id
    @Column(name = "SES_ID", length = 100)
    private String sesId = "";

    @Column(name = "NAME")
    private String name = "";

    @Column(name = "IP")
    private String ip = "";

    @CreatedDate
    @Column(name = "ENTER", columnDefinition = " timestamp default current_timestamp")
    @Temporal(TemporalType.TIMESTAMP)
    private Date enter = new Date(System.currentTimeMillis());

    @LastModifiedDate
    @Column(name = "LAST")
    @Temporal(TemporalType.TIMESTAMP)
    private Date last;

    @LastModifiedDate
    @Column(name = "EXIT", columnDefinition = " timestamp default null")
    @Temporal(TemporalType.TIMESTAMP)
    private Date exit = null;

    @Column(name = "USER_ID")
    private String userId = "";
}
