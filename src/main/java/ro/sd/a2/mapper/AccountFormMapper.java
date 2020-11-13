package ro.sd.a2.mapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ro.sd.a2.controller.FirstController;
import ro.sd.a2.dto.AccountFormDto;
import ro.sd.a2.entity.Account;
import ro.sd.a2.entity.User;
import ro.sd.a2.entity.Valute;
import ro.sd.a2.utils.factory.AccountFactory;
import ro.sd.a2.utils.factory.FactoryProvider;
import ro.sd.a2.utils.generators.IbanGenerator;

public class AccountFormMapper {
    private static final Logger log = LoggerFactory.getLogger(AccountFormMapper.class);

    public static Account convertAccountFormDtoToAccount(AccountFormDto accountFormDto, User user, Valute valute)
    {
        log.info("Map from accountFormDto to account requested.");
        AccountFactory accountFactory = (AccountFactory) FactoryProvider.getFactory("Account");
        Account account = accountFactory.create(accountFormDto.getType());
        account.setValute(valute);
        account.setUser(user);
        account.setIban(IbanGenerator.generate(account));
        return account;
    }
}
