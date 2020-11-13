package ro.sd.a2.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import ro.sd.a2.entity.Exchange;
import ro.sd.a2.entity.Valute;

@Repository
public interface ExchangeRepository extends JpaRepository<Exchange, Integer> {

    Exchange findByValuteFrom_IdAndValuteTo_Id(String valuteFrom_Id, String valuteTo_Id);
}
