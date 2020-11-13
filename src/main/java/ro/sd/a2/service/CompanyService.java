package ro.sd.a2.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.sd.a2.entity.Company;
import ro.sd.a2.repository.CompanyRepository;

import java.util.List;

/**
 * Service class of Company entity.
 */
@Service
public class CompanyService {
    @Autowired
    private CompanyRepository companyRepository;

    /**
     * Get all the companies in the database.
     * @return a list of all companies
     */
    public List<Company> findAll() {
        return companyRepository.findAll();
    }

    /**
     * Insert a new company in the database
     * @param company object to be inserted
     * @return The newly inserted object
     */
    public Company insert(Company company) {
        return companyRepository.save(company);
    }
}
