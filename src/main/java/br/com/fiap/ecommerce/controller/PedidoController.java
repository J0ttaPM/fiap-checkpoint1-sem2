package br.com.fiap.ecommerce.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.com.fiap.ecommerce.dtos.PedidoRequestCreateDto;
import br.com.fiap.ecommerce.dtos.PedidoRequestUpdateDto;
import br.com.fiap.ecommerce.dtos.PedidoResponseDto;
import br.com.fiap.ecommerce.service.PedidoService;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

    private final PedidoService pedidoService;

    @Autowired
    public PedidoController(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    @GetMapping
    public ResponseEntity<List<PedidoResponseDto>> getAllPedidos() {
        List<PedidoResponseDto> dtos = pedidoService.list()
                .stream()
                .map(pedido -> new PedidoResponseDto().toDto(pedido))
                .collect(Collectors.toList());
        
        return ResponseEntity.ok(dtos);
    }

    @PostMapping
    public ResponseEntity<PedidoResponseDto> createPedido(@RequestBody PedidoRequestCreateDto dto) {
        PedidoResponseDto responseDto = new PedidoResponseDto().toDto(pedidoService.save(dto.toModel()));
        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PedidoResponseDto> updatePedido(@PathVariable Long id, @RequestBody PedidoRequestUpdateDto dto) {
        if (!pedidoService.existsById(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        
        PedidoResponseDto responseDto = new PedidoResponseDto().toDto(pedidoService.save(dto.toModel(id)));
        return ResponseEntity.ok(responseDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePedido(@PathVariable Long id) {
        if (!pedidoService.existsById(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        pedidoService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PedidoResponseDto> getPedidoById(@PathVariable Long id) {
        return pedidoService.findById(id)
                .map(pedido -> ResponseEntity.ok(new PedidoResponseDto().toDto(pedido)))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
