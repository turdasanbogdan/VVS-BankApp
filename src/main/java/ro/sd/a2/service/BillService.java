package ro.sd.a2.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.sd.a2.entity.Bill;
import ro.sd.a2.repository.BillRepository;

import java.util.List;
import java.util.Optional;

/**
 * Service class for entity Bill.
 */
@Service
public class BillService {

    @Autowired
    private BillRepository billRepository;

    /**
     * Insert one bill in the database.
     * @param bill Bill to be inseted
     * @return The newly inserted bill.
     */
    public Bill insert(Bill bill)
    {
        return billRepository.save(bill);
    }

    /**
     * Gets all the bills from the database.
     * @return A list containing all the bills.
     */
    public List<Bill> findAll() {
        return billRepository.findAll();
    }

    /**
     * Finds a bill by its id.
     * @param billId The id of the bill we want to retrieve.
     * @return Bill or null if no bill is found
     */
    public Optional<Bill> findById(String billId) {
        return billRepository.findById(billId);
    }

    /**
     * Updates a bill.
     * @param bill we want to update.
     */
    public void update(Bill bill)
    {
        billRepository.save(bill);
    }

    /**
     * Returns the list of bills of some user given his id.
     * @param id The id of the user whose bills we want to find
     * @return A list containing these bills
     */
    public List<Bill> findAllByUserId(String id) {
        return billRepository.findByUser_Id(id);
    }
}
