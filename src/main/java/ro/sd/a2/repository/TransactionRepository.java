package ro.sd.a2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ro.sd.a2.entity.Transaction;
import ro.sd.a2.entity.User;

import java.util.List;
@Repository
public interface TransactionRepository extends JpaRepository<Transaction, String> {
    List<Transaction> findByAccount_Id(Integer id);
    List<Transaction> findByAccount_User_Id(String user_id);
}
