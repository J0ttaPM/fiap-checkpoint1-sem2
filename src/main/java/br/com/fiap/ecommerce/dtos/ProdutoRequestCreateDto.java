package br.com.fiap.ecommerce.dtos;

import java.math.BigDecimal;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;

import br.com.fiap.ecommerce.model.Produto;

public class ProdutoRequestCreateDto {
	
    private String nome;
    private BigDecimal valor;
    private static final ModelMapper modelMapper = new ModelMapper();
    
    static {
        modelMapper.getConfiguration()
            .setMatchingStrategy(MatchingStrategies.STRICT);
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	public Produto toModel() {
        return modelMapper.map(this, Produto.class);
    }
    
}
