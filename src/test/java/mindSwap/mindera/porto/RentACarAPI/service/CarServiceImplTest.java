package mindSwap.mindera.porto.RentACarAPI.service;

import mindSwap.mindera.porto.RentACarAPI.carDto.CarCreateDto;
import mindSwap.mindera.porto.RentACarAPI.converter.CarConverter;
import mindSwap.mindera.porto.RentACarAPI.exceptions.AppExceptions;
import mindSwap.mindera.porto.RentACarAPI.model.Car;
import mindSwap.mindera.porto.RentACarAPI.repository.CarRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class CarServiceImplTest {

    @MockBean
    private CarRepository carRepositoryMock;
    private CarServiceImpl carService;
    static MockedStatic<CarConverter> mockedCarConverter = Mockito.mockStatic(CarConverter.class);

    @BeforeEach
    public void setup() {
        carService = new CarServiceImpl(carRepositoryMock);
    }

    @Test
    @DisplayName("Test Create Car Calls Repository And Converter")
    void testCreateCarCallsRepositoryAndConverter() {

        // given
        CarCreateDto carCreateDto = new CarCreateDto("BatMobile", "AA-00-AA", 200, 100000, LocalDate.of(1989,1,1));
        Car car = new Car("BatMobile", "AA-00-AA", 200, 100000, LocalDate.of(1989,1,1));
        when(carRepositoryMock.findCarByPlate(carCreateDto.plate())).thenReturn(Optional.empty());
        mockedCarConverter.when(() -> CarConverter.fromDtaToCreateCar(carCreateDto)).thenReturn(car);
        when(carRepositoryMock.save(Mockito.any())).thenReturn(car);

        // when
        carService.addNewCar(carCreateDto);

        // then
        verify(carRepositoryMock, times(1)).findCarByPlate(carCreateDto.plate());
        mockedCarConverter.verify(() -> CarConverter.fromDtaToCreateCar(carCreateDto));
        mockedCarConverter.verifyNoMoreInteractions();

        verify(carRepositoryMock, times(1)).save(car);
        Mockito.verifyNoMoreInteractions(carRepositoryMock);
        assertEquals(car, carService.addNewCar(carCreateDto));
    }

    @Test
    @DisplayName("Create Car With Duplicated Plate Throws Exception")
    void createCarWithDuplicatedPlateThrowsException() {

        //given
        CarCreateDto carCreateDto = new CarCreateDto("BatMobile", "AA-00-AA", 200, 100000, LocalDate.of(1989,1,1));

        //when
        when(carRepositoryMock.findCarByPlate(carCreateDto.plate())).thenReturn(Optional.of(new Car()));

        //then
        assertThrows(AppExceptions.class, () -> carService.addNewCar (carCreateDto));
        assertEquals("This car Licence Plate already exists", assertThrows(AppExceptions.class, () -> carService.addNewCar(carCreateDto)).getMessage());

    }

    @Test
    @DisplayName("Test Delete Car Calls Repository")
    void testDeleteCarCallsRepository() {

        //given
        Car car = new Car(1L,"BatMobile", "AA-00-AA", 200, 100000, LocalDate.of(1989,1,1));
        when(carRepositoryMock.existsById(car.getId())).thenReturn(true);
        when(carRepositoryMock.findById(car.getId())).thenReturn(Optional.of(car));

        //when
        carService.deleteCar(car.getId());

        //then
        verify(carRepositoryMock, times(1)).deleteById(car.getId());

    }

}