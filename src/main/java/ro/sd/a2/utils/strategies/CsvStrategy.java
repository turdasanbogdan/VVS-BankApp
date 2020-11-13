package ro.sd.a2.utils.strategies;

import com.opencsv.CSVWriter;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import ro.sd.a2.dto.TransactionTableDto;
import ro.sd.a2.entity.Transaction;

import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

/**
 * Strategy class of building a csv file.
 */
public class CsvStrategy implements Strategy {
    /**
     * Build a csv file using the given transaction list.
     * @param transactionList The transactions we want to put in the csv.
     * @return The list of transactions.

     */
    @Override
    public String generate(List<TransactionTableDto> transactionList) throws IOException, CsvDataTypeMismatchException, CsvRequiredFieldEmptyException {

        String filename = "csv\\"+UUID.randomUUID().toString()+".csv";

        Writer writer = Files.newBufferedWriter(Paths.get(filename));

        //create a csv writer
        StatefulBeanToCsv<TransactionTableDto> csvBuilder = new StatefulBeanToCsvBuilder<TransactionTableDto>(writer)
                .withQuotechar(CSVWriter.NO_QUOTE_CHARACTER)
                .withSeparator(CSVWriter.DEFAULT_SEPARATOR)
                .withOrderedResults(false)
                .build();

        //write all users to csv file
        csvBuilder.write(transactionList);
        writer.close();
        return filename;
    }
}
