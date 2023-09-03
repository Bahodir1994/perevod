package uz.perevods.perevod.component.mappers;

import org.springframework.jdbc.core.RowMapper;
import uz.perevods.perevod.service.helperClass.TransactionalMoneyDebtDto;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;

//public class TransactionalMoneyDebtDtoRowMapper implements RowMapper<TransactionalMoneyDebtDto> {

//    @Override
//    public TransactionalMoneyDebtDto mapRow(ResultSet rs, int rowNum) throws SQLException {
//        Long soni = rs.getLong("soni");
//        BigDecimal jami = rs.getBigDecimal("jami");
//        BigDecimal undirildi = rs.getBigDecimal("undirildi");
//        BigDecimal qoldiq = rs.getBigDecimal("qoldiq");
//
//        return new TransactionalMoneyDebtDto(soni, jami, undirildi, qoldiq);
//    }
//}

