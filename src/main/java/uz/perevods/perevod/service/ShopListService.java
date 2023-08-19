package uz.perevods.perevod.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.cassandra.core.query.CassandraPageRequest;
import org.springframework.stereotype.Service;
import uz.perevods.perevod.entitiy.test.ShoppingList;
import uz.perevods.perevod.repository.ShoppingListRepository;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ShopListService {

    private final ShoppingListRepository shoppingListRepository;

    public List<ShoppingList> findAll() {
        return shoppingListRepository.findAll(CassandraPageRequest.first(10)).toList();
    }

    public ShoppingList findByTitle(String title) {
        return shoppingListRepository.findByTitleAllIgnoreCase(title);
    }

    public ShoppingList deleteById(String id) {
        shoppingListRepository.deleteById(UUID.fromString(id));
        return (ShoppingList) shoppingListRepository.findAll();
    }

    @PostConstruct
    public void insert() {
        ShoppingList groceries = new ShoppingList();
        groceries.setItems(Arrays.asList("Bread", "Milk, Apples"));

        ShoppingList pharmacy = new ShoppingList();
        pharmacy.setCompleted(true);
        pharmacy.setItems(Arrays.asList("Nappies", "Suncream, Aspirin"));

        shoppingListRepository.save(groceries);
        shoppingListRepository.save(pharmacy);
    }

}