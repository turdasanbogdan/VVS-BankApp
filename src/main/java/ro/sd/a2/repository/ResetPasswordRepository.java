package ro.sd.a2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ro.sd.a2.entity.ResetPassword;

public interface ResetPasswordRepository extends JpaRepository<ResetPassword, String> {

}
