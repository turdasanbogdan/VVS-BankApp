package ro.sd.a2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ro.sd.a2.entity.Valute;

@Repository
public interface ValuteRepository extends JpaRepository<Valute, String> {
    Valute findByName(String name);
}
