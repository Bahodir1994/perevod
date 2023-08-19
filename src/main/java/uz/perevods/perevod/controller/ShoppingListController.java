package uz.perevods.perevod.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import uz.perevods.perevod.entitiy.auth.User;
import uz.perevods.perevod.entitiy.test.ShoppingList;
import uz.perevods.perevod.repository.ShoppingListRepository;
import uz.perevods.perevod.service.ShopListService;
import uz.perevods.perevod.service.auth.UserService;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "/shopping")
@RequiredArgsConstructor
public class ShoppingListController {

    private final ShopListService shoppe;
    private final ShoppingListRepository shoppingListRepository;
    private final UserService userService;

    @GetMapping("/list")
    public List<ShoppingList> findAll(@RequestParam UUID id) {
        shoppingListRepository.deleteByUid(id);
        return shoppe.findAll();
    }

    @GetMapping("/list_user")
    public List<User> findAllUser() {
        shoppe.insert();
        return userService.getAll();
    }
}