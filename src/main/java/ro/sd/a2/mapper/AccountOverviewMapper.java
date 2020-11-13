package ro.sd.a2.mapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ro.sd.a2.controller.FirstController;
import ro.sd.a2.dto.AccountOverviewDto;
import ro.sd.a2.entity.Account;

public class AccountOverviewMapper {
    private static final Logger log = LoggerFactory.getLogger(AccountOverviewMapper.class);
    public static AccountOverviewDto convertToDto(Account account)
    {
        log.info("Map from account to AccountOverviewDto requested.");
        AccountOverviewDto accountOverviewDto = new AccountOverviewDto();
        accountOverviewDto.setIban(account.getIban());
        accountOverviewDto.setId(account.getId());
        accountOverviewDto.setSum(account.getSum());
        accountOverviewDto.setType(account.getType());
        accountOverviewDto.setValute(account.getValute().getName());
        return accountOverviewDto;
    }
}
