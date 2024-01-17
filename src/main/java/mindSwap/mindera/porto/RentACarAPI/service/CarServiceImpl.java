package mindSwap.mindera.porto.RentACarAPI.service;

import mindSwap.mindera.porto.RentACarAPI.converter.CarConverter;
import mindSwap.mindera.porto.RentACarAPI.carDto.CarCreateDto;
import mindSwap.mindera.porto.RentACarAPI.model.Car;
import mindSwap.mindera.porto.RentACarAPI.repository.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static mindSwap.mindera.porto.RentACarAPI.converter.CarConverter.fromDtaToCreateCar;

@Service
public class CarServiceImpl implements CarServiceI{

    private final CarRepository carRepository;

    @Autowired
    public CarServiceImpl(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    public List<CarCreateDto> getCars() {
        List<Car> cars = carRepository.findAll();
        return cars.stream()
                .map(CarConverter::fromCarToCreateDto)
                .toList();
    }

    public Car addNewCar(CarCreateDto car) {
        Optional<Car> carOptional = this.carRepository.findCarByPlate(car.plate());
        if(carOptional.isPresent())
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body("This plate already exists");
        Car newCar = fromDtaToCreateCar(car);
        return carRepository.save(newCar);
    }

    public void deleteCar(Long carĨd) {
        if (!carRepository.existsById(carĨd) ) {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Car with plate: " + carĨd + " does not exist" );
        }
        carRepository.deleteById(carĨd);
    }

    public void updateCar(Long id, Car car) {

        Optional<Car> carOptional = carRepository.findById(id);
        if(!carOptional.isPresent()) {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Car with id " + id + " does not exist");
        }
        Car carToUpdate = carOptional.get();
        if(car.getBrand() != null && car.getBrand().length() > 0 && !car.getBrand().equals(carToUpdate.getBrand())) {
            carToUpdate.setBrand(car.getBrand());
        }

        if(car.getPlate() != null && car.getPlate().length() > 0 && !car.getPlate().equals(carToUpdate.getPlate())){
            Optional<Car> carOptionalPlate = carRepository.findCarByPlate(car.getPlate());
            if (carOptionalPlate.isPresent())
                ResponseEntity.status(HttpStatus.BAD_REQUEST).body(" Licence plate already exists");
            carToUpdate.setPlate(car.getPlate());
        }
    }

    public Car getCarById(Long id) {
        Optional<Car> optionalCar = carRepository.findById(id);

        if (optionalCar.isEmpty()) {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Car with id " + id + " does not exist");
        }
        return optionalCar.get();
    }

}
