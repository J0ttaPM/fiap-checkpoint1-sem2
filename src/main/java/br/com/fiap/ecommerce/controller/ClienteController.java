package br.com.fiap.ecommerce.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.com.fiap.ecommerce.dtos.ClienteRequestCreateDto;
import br.com.fiap.ecommerce.dtos.ClienteRequestUpdateDto;
import br.com.fiap.ecommerce.dtos.ClienteResponseDto;
import br.com.fiap.ecommerce.service.ClienteService;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

    private final ClienteService clienteService;

    @Autowired
    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @GetMapping
    public ResponseEntity<List<ClienteResponseDto>> getAllClientes() {
        List<ClienteResponseDto> dtos = clienteService.list()
                .stream()
                .map(cliente -> new ClienteResponseDto().toDto(cliente))
                .collect(Collectors.toList());
        
        return ResponseEntity.ok(dtos);
    }

    @PostMapping
    public ResponseEntity<ClienteResponseDto> createCliente(@RequestBody ClienteRequestCreateDto dto) {
        ClienteResponseDto responseDto = new ClienteResponseDto().toDto(clienteService.save(dto.toModel()));
        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClienteResponseDto> updateCliente(@PathVariable Long id, @RequestBody ClienteRequestUpdateDto dto) {
        if (!clienteService.existsById(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        
        ClienteResponseDto responseDto = new ClienteResponseDto().toDto(clienteService.save(dto.toModel(id)));
        return ResponseEntity.ok(responseDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCliente(@PathVariable Long id) {
        if (!clienteService.existsById(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        clienteService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClienteResponseDto> getClienteById(@PathVariable Long id) {
        return clienteService.findById(id)
                .map(cliente -> ResponseEntity.ok(new ClienteResponseDto().toDto(cliente)))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
