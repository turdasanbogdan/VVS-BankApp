package ro.sd.a2.mapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ro.sd.a2.dto.BillDetailsDto;
import ro.sd.a2.entity.Bill;

public class BillDetailsMapper {
    private static final Logger log = LoggerFactory.getLogger(AccountOverviewMapper.class);
    public static BillDetailsDto convertToDto(Bill bill)
    {
        log.info("Map from Bill to BillDetailsDto requested");
        BillDetailsDto billDetailsDto = new BillDetailsDto();
        billDetailsDto.setCompany(bill.getCompany().getName());
        billDetailsDto.setEmissionDate(bill.getEmissionDate());
        billDetailsDto.setId(bill.getId());
        billDetailsDto.setSum(bill.getSum());
        billDetailsDto.setValute(bill.getCompany().getValute().getSymbol());
        return billDetailsDto;
    }
}
