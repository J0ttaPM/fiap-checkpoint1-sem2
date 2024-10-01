package br.com.fiap.ecommerce.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.com.fiap.ecommerce.dtos.ItemPedidoRequestCreateDto;
import br.com.fiap.ecommerce.dtos.ItemPedidoRequestUpdateDto;
import br.com.fiap.ecommerce.dtos.ItemPedidoResponseDto;
import br.com.fiap.ecommerce.service.ItemPedidoService;

@RestController
@RequestMapping("/itemPedidos")
public class ItemPedidoController {

    private final ItemPedidoService itemPedidoService;

    @Autowired
    public ItemPedidoController(ItemPedidoService itemPedidoService) {
        this.itemPedidoService = itemPedidoService;
    }

    @GetMapping
    public ResponseEntity<List<ItemPedidoResponseDto>> getAllItemPedidos() {
        List<ItemPedidoResponseDto> dtos = itemPedidoService.list()
                .stream()
                .map(item -> new ItemPedidoResponseDto().toDto(item))
                .collect(Collectors.toList());
        
        return ResponseEntity.ok(dtos);
    }

    @PostMapping
    public ResponseEntity<ItemPedidoResponseDto> createItemPedido(@RequestBody ItemPedidoRequestCreateDto dto) {
        ItemPedidoResponseDto responseDto = new ItemPedidoResponseDto().toDto(itemPedidoService.save(dto.toModel()));
        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ItemPedidoResponseDto> updateItemPedido(@PathVariable Long id, @RequestBody ItemPedidoRequestUpdateDto dto) {
        if (!itemPedidoService.existsById(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        
        ItemPedidoResponseDto responseDto = new ItemPedidoResponseDto().toDto(itemPedidoService.save(dto.toModel(id)));
        return ResponseEntity.ok(responseDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteItemPedido(@PathVariable Long id) {
        if (!itemPedidoService.existsById(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        itemPedidoService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ItemPedidoResponseDto> getItemPedidoById(@PathVariable Long id) {
        return itemPedidoService.findById(id)
                .map(item -> ResponseEntity.ok(new ItemPedidoResponseDto().toDto(item)))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
