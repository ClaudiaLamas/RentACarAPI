package mindSwap.mindera.porto.RentACarAPI.service;

import mindSwap.mindera.porto.RentACarAPI.clientDto.ClientCreateDto;
import mindSwap.mindera.porto.RentACarAPI.clientDto.ClientUpdateDto;
import mindSwap.mindera.porto.RentACarAPI.converter.ClientConverter;
import mindSwap.mindera.porto.RentACarAPI.exceptions.AppExceptions;
import mindSwap.mindera.porto.RentACarAPI.model.Client;
import mindSwap.mindera.porto.RentACarAPI.repository.ClientRepository;
import mindSwap.mindera.porto.RentACarAPI.utils.Messages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static mindSwap.mindera.porto.RentACarAPI.converter.ClientConverter.fromClientDtoToCrearteClient;

@Service
public class ClientServiceImpl implements ClientServiceI{

    private final ClientRepository clientRepository;

    @Autowired
    public ClientServiceImpl(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public List<ClientCreateDto> getClients() {
        List<Client> clients = clientRepository.findAll();
        return clients.stream()
                .map(ClientConverter::fromClientToCreateDto)
                .toList();
    }

    public Client addNewClient(ClientCreateDto client) {
        Optional<Client> clientOptional = this.clientRepository.findClientByEmail(client.email());
        if (clientOptional.isPresent()) {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Email already exists");
            return null;
        }

        Client newClient = fromClientDtoToCrearteClient(client);
        return clientRepository.save(newClient);
    }

    public void deleteClient(Long clientId) {
        if(!clientRepository.existsById(clientId)) {

            throw new AppExceptions(Messages.CLIENT_NOT_FOUND.getMessage());

        }
        clientRepository.deleteById(clientId);
    }

    public void updateClient(Long id, ClientUpdateDto clientUpdateDto) {

        Optional<Client> clientOptional = clientRepository.findById(id);
        if(!clientOptional.isPresent()) {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Client with id " + id + " does not exist");
        }
        Client clientToUpdate = clientOptional.get();
        if(clientUpdateDto.name() != null && clientUpdateDto.name().length() > 0 && !clientUpdateDto.name().equals(clientToUpdate.getName())) {
            clientToUpdate.setName(clientUpdateDto.name());
        }
        if(clientUpdateDto.email() != null && clientUpdateDto.email().length() > 0 && !clientUpdateDto.email().equals(clientToUpdate.getEmail())){
            Optional<Client> clientOptionalEmail = clientRepository.findClientByEmail(clientUpdateDto.email());
            if (clientOptionalEmail.isPresent())
                ResponseEntity.status(HttpStatus.BAD_REQUEST).body("email already exists");
            clientToUpdate.setEmail(clientUpdateDto.email());
        }

        clientRepository.save(clientToUpdate);
    }

    public Client getClientById(Long id) {
        Optional<Client> optionalClient = clientRepository.findById(id);

        if (optionalClient.isEmpty()) {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Client with id " + id + " does not exist");
        }
        return optionalClient.get();
    }



}
