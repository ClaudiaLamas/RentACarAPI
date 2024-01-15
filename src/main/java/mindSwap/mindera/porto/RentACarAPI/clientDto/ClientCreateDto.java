package mindSwap.mindera.porto.RentACarAPI.clientDto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record ClientCreateDto (
        @NotBlank(message = "Name not valid")
        String name,
        @Email(message = "email not valid")
        String email,
        @NotBlank
        @Size(min = 9, max = 12)
        int driverLicence,
        @Past(message = "Date of Birthday is not valid!")
        LocalDate dateOfBirth,
        @Size(min = 9)
        int nif

){

}
