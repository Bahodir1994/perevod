package uz.perevods.perevod.entitiy.authorization;

import org.hibernate.annotations.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import uz.perevods.perevod.component.entityComponents.AbstractAuditingEntity;

import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;
import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Collection;
import java.util.Set;

@Entity
@Table(name = "USER_E", schema = "PUBLIC", indexes = {@Index(columnList = "id", unique = true)})
@SQLDelete(sql = "update user_e set isdeleted = '1' where id = ?")
@Where(clause = "isdeleted = '0'")
public class User extends AbstractAuditingEntity implements UserDetails {
    @Id
    @GeneratedValue(generator = "uuid4")
    @GenericGenerator(name = "uuid4", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "ID", columnDefinition = "VARCHAR(50)")
    private String id;

    @Column(name = "USERID", unique = true, columnDefinition = "VARCHAR(50)")
    private String userid = "";

    @Column(name = "USERNAME", unique = true, columnDefinition = "VARCHAR(50)")
    @Size(min = 1, max = 50, message = "Логин киритилмаган")
    private String username = "";

    @Column(name = "PASSWORD", columnDefinition = "VARCHAR(100)")
    @Size(min = 1, message = "Илтимос паролни киритинг")
    private String password = "";

    @Transient
    private String passwordConfirm;

    @Column(name = "FULLNAME", unique = true, columnDefinition = "VARCHAR(300) CCSID 1208")
    private String fullname = "";

    @Column(name = "LOCATION", columnDefinition = "VARCHAR(5)")
    private String location = "";

    @Column(name = "LOCATIONNAME", columnDefinition = "VARCHAR(600) CCSID 1208")
    private String locationname = "";

    @Column(name = "POST", columnDefinition = "VARCHAR(10)")
    private String post = "";

    @Column(name = "POSTNAME", columnDefinition = "VARCHAR(600) CCSID 1208")
    private String postname = "";

    @Column(name = "NOTIFICATION")
    private Integer notification = -1;

    @Column(name = "LANG")
    @Size(max = 2, message = "")
    private String lang = "OZ";

    @Column(name = "ROLE", columnDefinition = "SMALLINT DEFAULT 0")
    private Integer role = 0;

    @ManyToMany(fetch = FetchType.EAGER)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Set<Role> roles;

    @Column(name = "INN", columnDefinition = "VARCHAR(9)")
    private String inn = "";

    @Column(name = "PNFL", columnDefinition = "VARCHAR(14)")
    private String pnfl = "";

    public User() {
    }

    public User(String id, String userid, String username, String password, String passwordConfirm, String fullname, String location, String locationname, String post, String postname, Integer notification, String lang, Integer role, Set<Role> roles, String inn, String pnfl) {
        this.id = id;
        this.userid = userid;
        this.username = username;
        this.password = password;
        this.passwordConfirm = passwordConfirm;
        this.fullname = fullname;
        this.location = location;
        this.locationname = locationname;
        this.post = post;
        this.postname = postname;
        this.notification = notification;
        this.lang = lang;
        this.role = role;
        this.roles = roles;
        this.inn = inn;
        this.pnfl = pnfl;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getRoles();
    }

    @Override
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasswordConfirm() {
        return passwordConfirm;
    }

    public void setPasswordConfirm(String passwordConfirm) {
        this.passwordConfirm = passwordConfirm;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getLocationname() {
        return locationname;
    }

    public void setLocationname(String locationname) {
        this.locationname = locationname;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public String getPostname() {
        return postname;
    }

    public void setPostname(String postname) {
        this.postname = postname;
    }

    public Integer getNotification() {
        return notification;
    }

    public void setNotification(Integer notification) {
        this.notification = notification;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public Integer getRole() {
        return role;
    }

    public void setRole(Integer role) {
        this.role = role;
    }

    public String getInn() {
        return inn;
    }

    public void setInn(String inn) {
        this.inn = inn;
    }

    public String getPnfl() {
        return pnfl;
    }

    public void setPnfl(String pnfl) {
        this.pnfl = pnfl;
    }
}
