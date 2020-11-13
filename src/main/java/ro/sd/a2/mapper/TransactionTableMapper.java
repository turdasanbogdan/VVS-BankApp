package ro.sd.a2.mapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ro.sd.a2.dto.TransactionTableDto;
import ro.sd.a2.entity.Transaction;

public class TransactionTableMapper {
    private static final Logger log = LoggerFactory.getLogger(TransactionTableMapper.class);
    public static TransactionTableDto convertToDto(Transaction transaction)
    {
        log.info("Map from Transaction to TransactionTableDto requested.");
        TransactionTableDto transactionTableDto = new TransactionTableDto();
        transactionTableDto.setIban(transaction.getAccount().getIban());
        transactionTableDto.setPaymentDate(transaction.getPaymentDate());
        transactionTableDto.setProvider(transaction.getBill().getCompany().getName());
        transactionTableDto.setSum(transaction.getSum());
        transactionTableDto.setValute(transaction.getBill().getCompany().getValute().getName());
        return transactionTableDto;
    }
}
