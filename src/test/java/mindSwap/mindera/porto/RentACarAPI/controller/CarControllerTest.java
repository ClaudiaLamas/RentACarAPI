package mindSwap.mindera.porto.RentACarAPI.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import mindSwap.mindera.porto.RentACarAPI.model.Car;
import mindSwap.mindera.porto.RentACarAPI.repository.CarRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class CarControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CarRepository carRepository;

    private static ObjectMapper objectMapper;

    @BeforeAll
    public static void setUp() {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
    }

    @BeforeEach
    public void init() {
        carRepository.deleteAll();
        carRepository.resetId();
    }

    @Test
    void contextLoads() {
    }

    @Test
    @DisplayName("Test get all cars when no cars on database returns empty list")
    void testGetAllCarsWhenNoCarsOnDatabaseReturnsEmptyList() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/cars/"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    @DisplayName("Test create a cars when cars returns status code 201")
    public void testCreateCarReturnCreateAndGetIdEqualsTo1() throws Exception {

        //Given
        String carJson = "{\"brand\": \"BMW\", \"plate\": \"AA-11-AA\", \"horsePower\": \"200\" ,\"km\": \"40\" , \"acquisitionDate\": \"2022-11-12\"}";


        //When
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/cars/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(carJson))
                .andExpect(status().isCreated())
                .andReturn();

        //Then
        String responseContent = result.getResponse().getContentAsString();

        Car car = objectMapper.readValue(responseContent, Car.class);

        //assert client id and name using matchers
        assertThat(car.getId()).isEqualTo(1L);
        assertThat(car.getPlate()).isEqualTo("AA-11-AA");
        assertThat(car.getBrand()).isEqualTo("BMW");
        assertThat(car.getAcquisitionDate()).isEqualTo("2022-11-12");
        assertThat(car.getKm()).isEqualTo(40);
        assertThat(car.getHorsePower()).isEqualTo(200);


    }

    @Test
    @DisplayName("Test get all cars when 2 cars on database returns list with 2 cars")
    void testGetAllCarsWhen2CarsOnDatabaseReturnsListWith2Cars() throws Exception {
        //Create 2 cars
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/cars/")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"brand\": \"BMW\", \"plate\": \"AA-11-AA\", \"horsePower\": \"200\" ,\"km\": \"40\" , \"acquisitionDate\": \"2022-11-12\"}"));

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/cars/")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"brand\": \"Mercedes\", \"plate\": \"AA-12-AA\", \"horsePower\": \"200\" ,\"km\": \"40\" , \"acquisitionDate\": \"2022-11-12\"}"));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/cars/"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)));

        //delete
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/cars/1"));
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/cars/2"));
    }

}