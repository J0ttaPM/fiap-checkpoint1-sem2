package br.com.fiap.ecommerce.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import br.com.fiap.ecommerce.model.Cliente;
import br.com.fiap.ecommerce.repository.ClienteRepository;

public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    public List<Cliente> list() {
        return clienteRepository.findAll();  
    }

    public Cliente save(Cliente cliente) {        
        return clienteRepository.save(cliente);
    }

    public boolean existsById(Long id) {        
        return clienteRepository.existsById(id);
    }

    public void delete(Long id) {
        clienteRepository.deleteById(id);
    }

    public Optional<Cliente> findById(Long id) {
        return clienteRepository.findById(id);
    }
}
