package ro.sd.a2.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.sd.a2.entity.Valute;
import ro.sd.a2.repository.ValuteRepository;

import java.util.List;

/**
 * Service class for the Valute Entity.
 */
@Service
public class ValuteService {
    @Autowired
    private ValuteRepository valuteRepository;

    public List<Valute> findAll() {
        return valuteRepository.findAll();
    }

    public Valute insert(Valute valute) {
        return valuteRepository.save(valute);
    }

    public Valute findByName(String name) {
        return valuteRepository.findByName(name);
    }
}
