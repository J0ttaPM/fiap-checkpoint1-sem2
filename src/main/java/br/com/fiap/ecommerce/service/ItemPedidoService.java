package br.com.fiap.ecommerce.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import br.com.fiap.ecommerce.model.ItemPedido;
import br.com.fiap.ecommerce.repository.ItemPedidoRepository;

public class ItemPedidoService {

    @Autowired
    private ItemPedidoRepository itemPedidoRepository;

    public List<ItemPedido> list() {
        return itemPedidoRepository.findAll();
    }

    public ItemPedido save(ItemPedido itemPedido) {        
        return itemPedidoRepository.save(itemPedido);
    }

    public boolean existsById(Long id) {        
        return itemPedidoRepository.existsById(id);
    }

    public void delete(Long id) {
        itemPedidoRepository.deleteById(id);
    }

    public Optional<ItemPedido> findById(Long id) {
        return itemPedidoRepository.findById(id);
    }
}
