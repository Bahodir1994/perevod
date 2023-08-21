package uz.perevods.perevod.entitiy.authorization;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import uz.perevods.perevod.component.entityComponents.AbstractAuditingEntity;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table(name = "PERSONS", schema = "PUBLIC",
        indexes = {
                @Index(columnList = "id", unique = true),
                @Index(columnList = "insuser", unique = false),
                @Index(columnList = "isdeleted", unique = false),
        })
@SQLDelete(sql = "update persons set isdeleted = 1 where id = ?")
@Where(clause = "isdeleted = 0")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Persons extends AbstractAuditingEntity {
    @Id
    @GeneratedValue(generator = "uuid4")
    @GenericGenerator(name = "uuid4", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "ID", columnDefinition = "VARCHAR(50)")
    private String id;

    @Column(name = "firstName", columnDefinition = "VARCHAR(180) CCSID 1208")
    @NotBlank(message = "Исм майдони бўш бўлиши мумкин эмас")
    @Size(max = 60, message = "Устун маълумоти катталиги чекланган")
    private String firstName;

    @Column(name = "surName", columnDefinition = "VARCHAR(180) CCSID 1208")
    @NotBlank(message = "Отасининг исми майдони бўш бўлиши мумкин эмас")
    @Size(max = 60, message = "Устун маълумоти катталиги чекланган")
    private String surName;

    @Column(name = "lastName", columnDefinition = "VARCHAR(180) CCSID 1208")
    @NotBlank(message = "Фамилия бўш бўлиши мумкин эмас")
    @Size(max = 60, message = "Фамилия маълумоти катталиги чекланган")
    private String lastName;

    @Column(name = "email", length = 30)
    @NotBlank(message = "Электрон почта майдони тўлдирилиши лозим")
    @Email(message = "Электрон почта хато киритилди")
    private String email;

    @Column(name = "pin", length = 14, unique = true)
    @Size(max = 14, message = "Фойдаланувчи ЖШШИР рақами 14 белгидан иборат бўлиши керак")
    private String pin;

    @Column(name = "tin", length = 9, unique = true)
    @NotBlank(message = "Фойдаланувчи СТИР рақами тўлдирилиши лозим")
    @Size(min = 9, max = 9, message = "Фойдаланувчи СТИР рақами 9 белгидан иборат бўлиши керак")
    private String tin;

    @Column(name = "per_adr", columnDefinition = "VARCHAR(765) CCSID 1208")
    @NotBlank(message = "per_adr - исми майдони бўш бўлиши мумкин эмас")
    @Size(max = 255, message = "Устун маълумоти катталиги чекланган")
    private String perAdr;

    @Column(name = "phone", length = 20)
    @NotBlank(message = "Устун тўлдирилмаган")
    @Size(max = 20, message = "Устун маълумоти катталиги чекланган")
    private String phone;

    @Column(name = "PERSON_TYPE", length = 1)
    @NotBlank(message = "Устун тўлдирилмаган")
    @Size(min = 1, max = 1, message = "Устун маълумоти катталиги чекланган")
    private String personType;

    @Column(name = "LEGAL_NAME", columnDefinition = "VARCHAR(600) CCSID 1208")
    @Size(max = 200, message = "Устун маълумоти катталиги чекланган")
    private String legalName;
}
