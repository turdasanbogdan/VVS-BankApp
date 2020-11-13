package ro.sd.a2;



import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;
import ro.sd.a2.entity.Bill;
import ro.sd.a2.entity.User;
import ro.sd.a2.repository.BillRepository;
import ro.sd.a2.service.BillService;
import ro.sd.a2.utils.generators.BillGenerator;

import javax.persistence.Table;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class BillRepositoryIntegrationTest {
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private BillRepository billRepository;

    @Test
    public void testFindByUser_id(){
        Bill bill1 = new Bill();
        Bill bill2 = new Bill();
        Bill bill3 = new Bill();
        User user1 = new User();
        User user2 = new User();

        user1.setId(UUID.randomUUID().toString());
        entityManager.persist(user1);
        user2.setId(UUID.randomUUID().toString());
        entityManager.persist(user2);


        bill1.setUser(user1);
        bill1.setId(UUID.randomUUID().toString());
        entityManager.persist(bill1);
        bill2.setId(UUID.randomUUID().toString());
        bill2.setUser(user2);
        entityManager.persist(bill2);
        bill3.setId(UUID.randomUUID().toString());
        bill3.setUser(user1);
        entityManager.persist(bill3);
        entityManager.flush();

        List<Bill> bills = billRepository.findByUser_Id(user1.getId());
        bills.forEach(e->assertThat(e.getUser().getId().equals(user1.getId())));
        assertThat(bills.size()==2);
    }

}
