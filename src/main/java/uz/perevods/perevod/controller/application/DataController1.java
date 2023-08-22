package uz.perevods.perevod.controller.application;

import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.perevods.perevod.entitiy.application.TransactionalMoney;
import uz.perevods.perevod.repository.application.TransactionalMoneyRepository;
import uz.perevods.perevod.service.application.AppService1;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/route_v1")
@RequiredArgsConstructor
public class DataController1 {
    private final AppService1 appService1;

    @GetMapping("/dataV1")
    public DataTablesOutput<TransactionalMoney> home(DataTablesInput tablesInput, HttpServletRequest request){

        return appService1.getData1(tablesInput, request);
    }
}
