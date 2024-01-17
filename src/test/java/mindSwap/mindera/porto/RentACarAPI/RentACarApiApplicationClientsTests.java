package mindSwap.mindera.porto.RentACarAPI;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import mindSwap.mindera.porto.RentACarAPI.model.Client;
import mindSwap.mindera.porto.RentACarAPI.repository.ClientRepository;
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
class RentACarApiApplicationClientsTests {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ClientRepository clientRepository;

	private static ObjectMapper objectMapper;

	@BeforeAll
	public static void setUp() {
		objectMapper = new ObjectMapper();
		objectMapper.registerModule(new JavaTimeModule());
	}

	@BeforeEach
	public void init() {
		clientRepository.deleteAll();
		clientRepository.resetId();
	}

	@Test
	void contextLoads() {
	}

	@Test
	@DisplayName("Test get all clients when no clients on database returns empty list")
	void testGetAllClientsWhenNoClientsOnDatabaseReturnsEmpyList() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/clients/"))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$", hasSize(0)));
	}

	@Test
	@DisplayName("Test create a client when client returns status code 201")
	public void testCreateClientReturnCreateAndGetIdEqualsTo1() throws Exception {

		//Given
		String clientJson = "{\"name\": \"Joao\", \"email\": \"j@eee.com\", \"driverLicence\": \"111111111\" ,\"dateOfBirth\": \"1990-01-01\" , \"nif\": \"111111111\"}";


		//When
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/clients/")
						.contentType(MediaType.APPLICATION_JSON)
						.content(clientJson))
				.andExpect(status().isCreated())
				.andReturn();

		//Then
		String responseContent = result.getResponse().getContentAsString();

		Client client = objectMapper.readValue(responseContent, Client.class);

		//assert client id and name using matchers
		assertThat(client.getId()).isEqualTo(1L);
		assertThat(client.getName()).isEqualTo("Joao");
		assertThat(client.getEmail()).isEqualTo("j@eee.com");
		assertThat(client.getDriverLicence()).isEqualTo(111111111);


	}


	@Test
	@DisplayName("Test get all clients when 2 clients on database returns list with 2 clients")
	void testGetAllClientsWhen2ClientsOnDatabaseReturnsListWith2Clients() throws Exception {
		//Create 2 clients
		mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/clients/")
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"name\": \"Joao\", \"email\": \"j@eee.com\", \"driverLicence\": \"111111111\" ,\"dateOfBirth\": \"1990-01-01\" , \"nif\": \"111111111\"}"));

		mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/clients/")
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"name\": \"Maria\", \"email\": \"mar@eee.com\", \"driverLicence\": \"222222222\" ,\"dateOfBirth\": \"1990-01-01\" , \"nif\": \"222111111\"}"));

		mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/clients/"))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$", hasSize(2)));

		//delete
		mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/clients/1"));
		mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/clients/2"));
	}



}
