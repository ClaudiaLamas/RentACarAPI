package mindSwap.mindera.porto.RentACarAPI.controller;

import mindSwap.mindera.porto.RentACarAPI.carDto.CarCreateDto;
import mindSwap.mindera.porto.RentACarAPI.model.Car;
import mindSwap.mindera.porto.RentACarAPI.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/cars")
public class CarController {
    private final CarService carService;

    @Autowired
    public CarController (CarService carService) {
        this.carService = carService;
    }

    @GetMapping("/")
    public ResponseEntity<List<CarCreateDto>> getCars() {
        return new ResponseEntity<>(carService.getCars(), HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<Car> addNewClient(@RequestBody CarCreateDto car) {
        carService.addNewCar(car);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping(path = "carId")
    public ResponseEntity<Car> deleteClient(@PathVariable("carId") Long carId) {
        carService.deleteCar(carId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
