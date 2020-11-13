package ro.sd.a2;

import org.junit.jupiter.api.Test;
import ro.sd.a2.dto.BillDetailsDto;
import ro.sd.a2.dto.UserRegisterDto;
import ro.sd.a2.entity.Bill;
import ro.sd.a2.entity.Company;
import ro.sd.a2.entity.User;
import ro.sd.a2.entity.Valute;
import ro.sd.a2.mapper.BillDetailsMapper;
import ro.sd.a2.mapper.UserRegisterMapper;

import java.awt.*;
import java.util.Date;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;


public class MappersIntegrationTest {
    @Test
    public void testBillDetailsMapper()
    {
        Bill bill = new Bill();
        bill.setId(UUID.randomUUID().toString());
        Company comp = new Company();
        Valute val = new Valute();
        val.setSymbol("USD");
        comp.setValute(val);
        comp.setName("Cristal");
        bill.setCompany(comp);
        bill.setSum(2);
        bill.setEmissionDate(new Date());
        User user = new User();
        user.setUsername("vlad");
        bill.setUser(user);
        bill.setTransaction(null);

        BillDetailsDto billDetailsDto = BillDetailsMapper.convertToDto(bill);

        assertThat(billDetailsDto.getCompany().equals(bill.getCompany().getName()));
        assertThat(billDetailsDto.getEmissionDate()==bill.getEmissionDate());
        assertThat(billDetailsDto.getId().equals(bill.getId()));
        assertThat(billDetailsDto.getSum()==bill.getSum());
        assertThat(billDetailsDto.getValute().equals(bill.getCompany().getValute().getSymbol()));
    }

    @Test
    public void testRegisterMapping()
    {
        UserRegisterDto userRegisterDto = new UserRegisterDto();
        userRegisterDto.setAddress("Orastie");
        userRegisterDto.setDateOfBirth(new Date());
        userRegisterDto.setEmail("mail");
        userRegisterDto.setFirstName("Vlad");
        userRegisterDto.setLastName("Negru");
        userRegisterDto.setUsername("MyUser");
        userRegisterDto.setPassword("test124");
        userRegisterDto.setPasswordConfirmation("test124");

        User user = UserRegisterMapper.convertToEntity(userRegisterDto);
        assertThat(user.getEmail().equals(userRegisterDto.getEmail()));
        assertThat(user.getFirstName().equals(userRegisterDto.getFirstName()));
        assertThat(user.getLastName().equals(userRegisterDto.getLastName()));
        assertThat(user.getAddress().equals(userRegisterDto.getAddress()));
        assertThat(user.getBirthDate()== userRegisterDto.getDateOfBirth());
        assertThat(user.getPassword().equals(userRegisterDto.getPassword()));

    }
}
