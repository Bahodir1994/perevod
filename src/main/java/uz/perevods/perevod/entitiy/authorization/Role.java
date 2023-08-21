package uz.perevods.perevod.entitiy.authorization;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;

@Entity
@Table(name = "UROLE", schema = "PUBLIC", indexes = {@Index(columnList = "id", unique = true)})
@OnDelete(action = OnDeleteAction.CASCADE)
public class Role implements GrantedAuthority {
    @Id
    private String id;
    @Column(name = "NAME", columnDefinition = "VARCHAR(600) CCSID 1208")
    private String name;
    @Column(name = "CODE", columnDefinition = "SMALLINT DEFAULT 0")
    private Integer code;

    public Role() {
    }

    public Role(String id, String name, Integer code) {
        this.id = id;
        this.name = name;
        this.code = code;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    @Override
    public String getAuthority() {
        return getName();
    }
}
