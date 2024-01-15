package mindSwap.mindera.porto.RentACarAPI.clientDto;

import jakarta.validation.constraints.Email;

public record ClientUpdateDto(
        String name,
        @Email(message = "email not valid")
        String email

) {

}
