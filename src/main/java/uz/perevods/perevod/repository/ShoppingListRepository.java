package uz.perevods.perevod.repository;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;
import uz.perevods.perevod.entitiy.test.ShoppingList;

import java.util.UUID;

@Repository
public interface ShoppingListRepository extends CassandraRepository<ShoppingList, UUID> {
    ShoppingList findByTitleAllIgnoreCase(String title);

    void deleteByUid(UUID uid);

}