package mindSwap.mindera.porto.RentACarAPI.controller;

import mindSwap.mindera.porto.RentACarAPI.model.Rental;
import mindSwap.mindera.porto.RentACarAPI.rentalDto.RentalCreateDto;
import mindSwap.mindera.porto.RentACarAPI.rentalDto.RentalPostDto;
import mindSwap.mindera.porto.RentACarAPI.rentalDto.RentalUpdateDto;
import mindSwap.mindera.porto.RentACarAPI.service.RentalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/rentals")
public class RentalController {
    private final RentalService rentalService;


    @Autowired
    public RentalController(RentalService rentalService) {
        this.rentalService = rentalService;
    }

    @GetMapping("/")
    public ResponseEntity<List<RentalCreateDto>> getRentals() {
        return new ResponseEntity<>(rentalService.getRentals(), HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<Rental> addNewRental(@RequestBody RentalPostDto rental) {
        rentalService.addNewRental(rental);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }


    @PutMapping(path = "{rentalId}")
    public ResponseEntity<Rental> updateRental(@PathVariable("rentalId") Long id, @RequestBody RentalUpdateDto rental) {
        rentalService.updateRental(id, rental);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping(path = "{rentalId}")
    public ResponseEntity<Rental> deleteRental(@PathVariable("rentalId") Long id) {
        rentalService.deleteRental(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
