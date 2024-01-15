package mindSwap.mindera.porto.RentACarAPI.controller;

import mindSwap.mindera.porto.RentACarAPI.clientDto.ClientCreateDto;
import mindSwap.mindera.porto.RentACarAPI.clientDto.ClientUpdateDto;
import mindSwap.mindera.porto.RentACarAPI.model.Client;
import mindSwap.mindera.porto.RentACarAPI.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/clients")
public class ClientController {
    private final ClientService clientService;

    @Autowired
    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping("/")
    public ResponseEntity<List<ClientCreateDto>> getClients() {
        return new ResponseEntity<>(clientService.getClients(), HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<Client> addNewClient(@RequestBody ClientCreateDto client) {

        clientService.addNewClient(client);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("{clientId}")
    public ResponseEntity<Client> updateClient(@PathVariable("clientId") Long clientId, @RequestBody ClientUpdateDto clientUpdateDto) {
        clientService.updateClient(clientId, clientUpdateDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @DeleteMapping(path = "{clientId}")
    public ResponseEntity<Client> deleteClient(@PathVariable("clientId") Long clientId) {
        clientService.deleteClient(clientId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
