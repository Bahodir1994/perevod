package uz.perevods.perevod.entitiy.test;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.cassandra.core.mapping.CassandraType;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Table
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ShoppingList {

    @PrimaryKey
    @CassandraType(type = CassandraType.Name.UUID)
    private UUID uid = UUID.randomUUID();

    private String title;

    private boolean completed = false;

    @Column
    private List<String> items = new ArrayList<>();

}