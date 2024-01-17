package mindSwap.mindera.porto.RentACarAPI.repository;

import jakarta.transaction.Transactional;
import mindSwap.mindera.porto.RentACarAPI.model.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface CarRepository extends JpaRepository<Car, Long> {
    @Query("SELECT car_ FROM Car car_ WHERE car_.plate = ?1")
    Optional<Car> findCarByPlate(String plate);

    @Modifying
    @Transactional
    @Query(value = "ALTER TABLE car AUTO_INCREMENT = 1", nativeQuery = true)
    void resetId();
}
