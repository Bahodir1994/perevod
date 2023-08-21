package uz.perevods.perevod.service.application;

import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
@RequiredArgsConstructor
public class AppService1 {

    public DataTablesOutput<?> getData1(DataTablesInput tablesInput, HttpServletRequest request){

        DataTablesOutput<?> output = new DataTablesOutput<>();
        return output;
    }
}
