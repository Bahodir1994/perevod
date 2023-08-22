package uz.perevods.perevod.service.application;

import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.stereotype.Service;
import uz.perevods.perevod.entitiy.application.TransactionalMoney;
import uz.perevods.perevod.repository.application.TransactionalMoneyRepository;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AppService1 {
    private final TransactionalMoneyRepository transactionalMoneyRepository;
    public DataTablesOutput<TransactionalMoney> getData1(DataTablesInput tablesInput, HttpServletRequest request){

        List<TransactionalMoney> transactionalMonies = transactionalMoneyRepository.findAll();

        DataTablesOutput<TransactionalMoney> output = new DataTablesOutput<>();
        output.setData(transactionalMonies);
        output.setRecordsTotal(transactionalMonies.size());
        output.setRecordsFiltered(transactionalMonies.size());
        output.setDraw(tablesInput.getDraw());

        return output;
    }
}
