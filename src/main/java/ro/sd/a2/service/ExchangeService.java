package ro.sd.a2.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.sd.a2.entity.Exchange;
import ro.sd.a2.entity.Valute;
import ro.sd.a2.repository.ExchangeRepository;

/**
 * Service for Exchange entity.
 */
@Service
public class ExchangeService {
    @Autowired
    private ExchangeRepository exchangeRepository;

    /**
     * Finds the data from the table satisfying that is an exchange from valute valuteFrom to valute valuteTo.
     * @param valueFrom The valute from which the exchange begins
     * @param valueTo The valute at the end of conversion
     * @return Exchange object from the database.
     */
    public Exchange findExchange(Valute valueFrom, Valute valueTo)
    {
        return exchangeRepository.findByValuteFrom_IdAndValuteTo_Id(valueFrom.getId(), valueTo.getId());

    }
}
