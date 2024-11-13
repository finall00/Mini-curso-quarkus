package br.com.fatec;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProdutoDTO {

    @NotNull(message = "O nome não pode ser vazio")
    @NotEmpty
    private String nome;
    @NotNull(message = "A descrição não pode ser vazio")
    @NotEmpty
    private String descricao;
    @NotNull(message = "O Preço não pode ser vazio")
    private Double preco;
}
