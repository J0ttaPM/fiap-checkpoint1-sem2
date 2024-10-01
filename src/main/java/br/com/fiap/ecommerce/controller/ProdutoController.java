package br.com.fiap.ecommerce.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.com.fiap.ecommerce.dtos.ProdutoRequestCreateDto;
import br.com.fiap.ecommerce.dtos.ProdutoRequestUpdateDto;
import br.com.fiap.ecommerce.dtos.ProdutoResponseDto;
import br.com.fiap.ecommerce.service.ProdutoService;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {

    private final ProdutoService produtoService;

    @Autowired
    public ProdutoController(ProdutoService produtoService) {
        this.produtoService = produtoService;
    }

    @GetMapping
    public ResponseEntity<List<ProdutoResponseDto>> getAllProdutos() {
        List<ProdutoResponseDto> dtos = produtoService.list()
                .stream()
                .map(produto -> new ProdutoResponseDto().toDto(produto))
                .collect(Collectors.toList());
        
        return ResponseEntity.ok(dtos);
    }

    @PostMapping
    public ResponseEntity<ProdutoResponseDto> createProduto(@RequestBody ProdutoRequestCreateDto dto) {
        ProdutoResponseDto responseDto = new ProdutoResponseDto().toDto(produtoService.save(dto.toModel()));
        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProdutoResponseDto> updateProduto(@PathVariable Long id, @RequestBody ProdutoRequestUpdateDto dto) {
        if (!produtoService.existsById(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        
        ProdutoResponseDto responseDto = new ProdutoResponseDto().toDto(produtoService.save(dto.toModel(id)));
        return ResponseEntity.ok(responseDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduto(@PathVariable Long id) {
        if (!produtoService.existsById(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        produtoService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProdutoResponseDto> getProdutoById(@PathVariable Long id) {
        return produtoService.findById(id)
                .map(produto -> ResponseEntity.ok(new ProdutoResponseDto().toDto(produto)))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
