package ro.sd.a2;

import org.junit.Test;
import ro.sd.a2.entity.Bill;
import ro.sd.a2.entity.Company;
import ro.sd.a2.entity.User;
import ro.sd.a2.utils.generators.BillGenerator;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class TestBillGenerator {

    @Test
    public void testBillGenerator()
    {
        List<Company> companies = new LinkedList();
        for(int i=0; i<10; i++)
        {
            Company company = new Company();
            company.setId(UUID.randomUUID().toString());
            company.setName("Company"+i);
            companies.add(company);
        }
        User user = new User();
        user.setId(UUID.randomUUID().toString());
        user.setUsername("Vlad");

        List<Bill> bills = BillGenerator.generate(user, 100, companies);
        assertThat(bills.size()==100);
        bills.forEach(e->assertThat(companies.contains(e.getCompany())));
        bills.forEach(e->assertThat(e.getUser()==user));

        List<Bill> bills2 = BillGenerator.generate(user, 0, companies);
        assertThat(bills2!=null);
        assertThat(bills2.size()==0);
    }
}
