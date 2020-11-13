package ro.sd.a2.dto;

import java.util.Date;


/**
 * DTo for displaying the account details for the user.
 */
public class AccountDetailsDto {
    private AccountOverviewDto accountOverviewDto;
    private Date lastModificationDate;
    private Date creationDate;

    public AccountOverviewDto getAccountOverviewDto() {
        return accountOverviewDto;
    }

    public void setAccountOverviewDto(AccountOverviewDto accountOverviewDto) {
        this.accountOverviewDto = accountOverviewDto;
    }

    public Date getLastModificationDate() {
        return lastModificationDate;
    }

    public void setLastModificationDate(Date lastModificationDate) {
        this.lastModificationDate = lastModificationDate;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }
}
