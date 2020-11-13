package ro.sd.a2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ro.sd.a2.entity.Attempts;

@Repository
public interface AttemptsRepository extends JpaRepository<Attempts, Integer> {
    Attempts findByEmail(String email);
}
