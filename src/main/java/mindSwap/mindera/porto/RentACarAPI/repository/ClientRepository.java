package mindSwap.mindera.porto.RentACarAPI.repository;

import jakarta.transaction.Transactional;
import mindSwap.mindera.porto.RentACarAPI.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ClientRepository extends JpaRepository<Client, Long> {

    @Query("SELECT c FROM Client c WHERE c.email = ?1")
    Optional<Client> findClientByEmail(String email);

    @Modifying
    @Transactional
    @Query(value = "ALTER TABLE client AUTO_INCREMENT = 1", nativeQuery = true)
    void resetId();
}
