package mindSwap.mindera.porto.RentACarAPI.service;

import mindSwap.mindera.porto.RentACarAPI.carDto.CarCreateDto;
import mindSwap.mindera.porto.RentACarAPI.model.Car;

import java.util.List;

public interface CarServiceI {

    List<CarCreateDto> getCars();
    Car addNewCar(CarCreateDto car);
    void deleteCar(Long carĨd);
    void updateCar(Long id, Car car);
    Car getCarById(Long id);

}
