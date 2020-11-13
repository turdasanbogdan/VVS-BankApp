package ro.sd.a2.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.sd.a2.entity.ResetPassword;
import ro.sd.a2.repository.ResetPasswordRepository;

import java.util.Optional;

/**
 * Service class for ResetPassword entity.
 */
@Service
public class ResetPasswordService {
    @Autowired
    private ResetPasswordRepository resetPasswordRepository;

    /**
     * Finds the ResetPassword object by its id (token).
     * @param token an uuid, the id of the resetPassword
     * @return ResetPassword object having the token id.
     */
    public Optional<ResetPassword> findById(String token) {
        return resetPasswordRepository.findById(token);
    }

    /**
     * Insert a new ResetPassword object.
     * @param resetPassword the object to be inserted.
     */
    public void insert(ResetPassword resetPassword) {
        resetPasswordRepository.save(resetPassword);
    }

    /**
     * Delets the password token.
     * @param resetPassword Object to be deleted.
     */
    public void delete(ResetPassword resetPassword) {
        resetPasswordRepository.delete(resetPassword);
    }
}
