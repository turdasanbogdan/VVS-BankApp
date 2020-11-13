package ro.sd.a2.mapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ro.sd.a2.controller.FirstController;
import ro.sd.a2.dto.AccountDetailsDto;
import ro.sd.a2.dto.AccountOverviewDto;
import ro.sd.a2.entity.Account;

public class AccountDetailsMapper {
    private static final Logger log = LoggerFactory.getLogger(FirstController.class);
    public static AccountDetailsDto convertToDto(Account account)
    {
        log.info("Map from account to AccountDetailsDto requested.");
        AccountDetailsDto accountDetailsDto = new AccountDetailsDto();
        AccountOverviewDto accountOverviewDto = AccountOverviewMapper.convertToDto(account);
        accountDetailsDto.setAccountOverviewDto(accountOverviewDto);
        accountDetailsDto.setCreationDate(account.getCreationDate());
        accountDetailsDto.setLastModificationDate(account.getLastModificationDate());
        return accountDetailsDto;
    }
}
