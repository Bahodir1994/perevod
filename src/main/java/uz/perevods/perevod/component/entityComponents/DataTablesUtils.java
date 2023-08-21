package uz.perevods.perevod.component.entityComponents;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;

public class DataTablesUtils {

    public static Pageable getPageable(DataTablesInput input) {
        int pageNumber = input.getStart() / input.getLength();
        return PageRequest.of(pageNumber, input.getLength());
    }
}

