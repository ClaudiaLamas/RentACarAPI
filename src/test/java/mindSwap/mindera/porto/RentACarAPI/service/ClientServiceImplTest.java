package mindSwap.mindera.porto.RentACarAPI.service;

import mindSwap.mindera.porto.RentACarAPI.clientDto.ClientCreateDto;
import mindSwap.mindera.porto.RentACarAPI.converter.ClientConverter;
import mindSwap.mindera.porto.RentACarAPI.exceptions.AppExceptions;
import mindSwap.mindera.porto.RentACarAPI.model.Client;
import mindSwap.mindera.porto.RentACarAPI.repository.ClientRepository;
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
class ClientServiceImplTest {

    @MockBean
    private ClientRepository clientRepositoryMock;

    private ClientServiceImpl clientService;
    static MockedStatic<ClientConverter> mockedClientConverter = Mockito.mockStatic(ClientConverter.class);

    @BeforeEach
    public void setUp() {
        clientService = new ClientServiceImpl(clientRepositoryMock);
    }

    @Test
    @DisplayName("Test Create Client Calls Repository And Converter")
    void testCreateClientCallsRepositoryAndConverter() {

        //given
        ClientCreateDto clientCreateDto = new ClientCreateDto("Batman", "bat@test.com", 111111111, LocalDate.of(1809,01,20), 111111111);
        Client client = new Client("Batman", "bat@test.com", 111111111, LocalDate.of(1809,01,20), 111111111);
        when(clientRepositoryMock.findClientByEmail(clientCreateDto.email())).thenReturn(Optional.empty());
        mockedClientConverter.when(() -> ClientConverter.fromClientDtoToCrearteClient(clientCreateDto)).thenReturn(client);
        when(clientRepositoryMock.save(Mockito.any())).thenReturn(client);

        //when

        clientService.addNewClient(clientCreateDto);

        //then
        verify(clientRepositoryMock, times(1)).findClientByEmail(clientCreateDto.email());

        mockedClientConverter.verify(() -> ClientConverter.fromClientDtoToCrearteClient(clientCreateDto));
        mockedClientConverter.verifyNoMoreInteractions();

        verify(clientRepositoryMock, times(1)).save(client);
        Mockito.verifyNoMoreInteractions(clientRepositoryMock);
        assertEquals(client, clientService.addNewClient(clientCreateDto));
    }

    @Test
    @DisplayName("Create Client With Duplicated Email Throws Exception")
    void createClientWithDuplicatedEmailThrowsException() {

        //given
        ClientCreateDto clientCreateDto = new ClientCreateDto("Batman", "bat@test.com", 111111111, LocalDate.of(1809,01,20), 111111111);

        //when
        when(clientRepositoryMock.findClientByEmail(clientCreateDto.email())).thenReturn(Optional.of(new Client()));

        //then
        assertThrows(AppExceptions.class, () -> clientService.addNewClient (clientCreateDto));
        assertEquals("Email already exists", assertThrows(AppExceptions.class, () -> clientService.addNewClient(clientCreateDto)).getMessage());

    }


}