package mindSwap.mindera.porto.RentACarAPI.service;

import mindSwap.mindera.porto.RentACarAPI.clientDto.ClientCreateDto;
import mindSwap.mindera.porto.RentACarAPI.clientDto.ClientUpdateDto;
import mindSwap.mindera.porto.RentACarAPI.model.Client;

import java.util.List;

public interface ClientServiceI {

    // implement CRUD methods for Client Service???

List<ClientCreateDto> getClients();
Client addNewClient(ClientCreateDto clientCreateDto);
void deleteClient(Long id);
void updateClient(Long id, ClientUpdateDto clientUpdateDto);
Client getClientById(Long id);

}
