package ro.sd.a2.utils.strategies;

import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import ro.sd.a2.dto.TransactionTableDto;
import ro.sd.a2.entity.Transaction;

import java.io.IOException;
import java.util.List;

public interface Strategy {
    public String generate(List<TransactionTableDto> transactionList) throws IOException, CsvDataTypeMismatchException, CsvRequiredFieldEmptyException, Exception;
}
