package mindSwap.mindera.porto.RentACarAPI.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import mindSwap.mindera.porto.RentACarAPI.model.Rental;
import mindSwap.mindera.porto.RentACarAPI.repository.CarRepository;
import mindSwap.mindera.porto.RentACarAPI.repository.ClientRepository;
import mindSwap.mindera.porto.RentACarAPI.repository.RentalRepository;
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
class RentalControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private RentalRepository rentalRepository;
    @Autowired
    private ClientRepository clientRepository;
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
        clientRepository.deleteAll();
        rentalRepository.deleteAll();
        carRepository.resetId();
        clientRepository.resetId();
        rentalRepository.resetId();
    }

    @Test
    void contextLoads() {
    }

    @Test
    @DisplayName("Test get all renals when no rental on database returns empty list")
    void testGetAllRentalsWhenNoRentalsOnDatabaseReturnsEmptyList() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/rentals/"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    @DisplayName("Test create a rental when rental returns status code 201")
    public void testCreateRentalReturnCreateAndGetIdEqualsTo1() throws Exception {

        //Given
        String carJson = "{\"brand\": \"BMW\", \"plate\": \"AA-11-AA\", \"horsePower\": \"200\" ,\"km\": \"40\" , \"acquisitionDate\": \"2022-11-12\"}";
        String clientJson = "{\"name\": \"Joao\", \"email\": \"j@eee.com\", \"driverLicence\": \"111111111\" ,\"dateOfBirth\": \"1990-01-01\" , \"nif\": \"111111111\"}";
        String rentalJson = "{\"clientId\": \"1\", \"carId\": \"1\", \"initialDate\": \"2024-01-19\" ,\"lastDayRent\": \"2024-21-24\" }";


        //When
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/cars/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(carJson))
                .andExpect(status().isCreated())
                .andReturn();

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/clients/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(clientJson))
                .andExpect(status().isCreated())
                .andReturn();

        MvcResult resultRental = mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/rentals/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(rentalJson))
                .andExpect(status().isCreated())
                .andReturn();

        //Then
        String responseRentalContent = resultRental.getResponse().getContentAsString();

        Rental rental = objectMapper.readValue(responseRentalContent, Rental.class);


        //assert client id and name using matchers
        assertThat(rental.getId()).isEqualTo(1L);
        assertThat(rental.getClient().getId()).isEqualTo(1);
        assertThat(rental.getCar().getId()).isEqualTo(1);
        assertThat(rental.getInitialRent()).isEqualTo("2024-01-01");
        assertThat(rental.getLastDayRental()).isEqualTo("2024-01-01");

    }

    @Test
    @DisplayName("Test get all rentals when 1 rentals on database returns list with 1 rentals")
    void testGetAllCarsWhen2CarsOnDatabaseReturnsListWith2Cars() throws Exception {

        // Create 1 clients
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/clients/")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\": \"Joao\", \"email\": \"j@eee.com\", \"driverLicence\": \"111111111\" ,\"dateOfBirth\": \"1990-01-01\" , \"nif\": \"111111111\"}"));

        // Create 1 Cars

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/cars/")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"brand\": \"BMW\", \"plate\": \"AA-11-AA\", \"horsePower\": \"200\" ,\"km\": \"40\" , \"acquisitionDate\": \"2022-11-12\"}"));

        //Create 1 rentals
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/rentals/")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"clientId\": \"1\", \"carId\": \"1\", \"initialDate\": \"2024-01-19\" ,\"lastDayRent\": \"2024-21-24\" }"));



        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/rentals/"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1)));
     /*
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/rentals/"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1)));


      */
        //delete
       // mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/rentals/1"));
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/rentals/1"));
    }



}