package uz.perevods.perevod.entitiy.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.cassandra.core.mapping.CassandraType;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.util.Set;
import java.util.UUID;

@Table
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class User {

    @PrimaryKey
    @CassandraType(type = CassandraType.Name.UUID)
    @Column
    private String id;

    @CassandraType(type = CassandraType.Name.TEXT)
    @Column
    private String username;

    @CassandraType(type = CassandraType.Name.TEXT)
    @Column
    private String password;

    @CassandraType(type = CassandraType.Name.BOOLEAN)
    private boolean enabled;

    @CassandraType(type = CassandraType.Name.TEXT)
    @Column
    private String firstName;

    @CassandraType(type = CassandraType.Name.TEXT)
    @Column
    private String lastName;

    @CassandraType(type = CassandraType.Name.TEXT)
    @Column
    private String surName;

    @CassandraType(type = CassandraType.Name.TEXT)
    @Column
    private String fullName;

    @CassandraType(type = CassandraType.Name.TEXT)
    @Column
    private String passport;

    @CassandraType(type = CassandraType.Name.TEXT)
    @Column
    private String pin;

    @CassandraType(type = CassandraType.Name.TEXT)
    @Column
    private String tin;

    @CassandraType(type = CassandraType.Name.TEXT)
    @Column
    private String phone;

    @CassandraType(type = CassandraType.Name.TEXT)
    @Column
    private String email;


    @Column
    private Set<UUID> roles;
}
