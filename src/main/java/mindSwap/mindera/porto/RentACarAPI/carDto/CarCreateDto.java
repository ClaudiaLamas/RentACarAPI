package mindSwap.mindera.porto.RentACarAPI.carDto;

import java.time.LocalDate;

public record CarCreateDto(
        String brand,
        String plate,
        int horsePower,
        int km,
        LocalDate acquisitionDate

) {
}
