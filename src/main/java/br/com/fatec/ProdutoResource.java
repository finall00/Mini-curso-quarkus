package br.com.fatec;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;

import jakarta.transaction.Transactional;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/api")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ProdutoResource {

    @Operation(
        summary = "Criar produto",
        description = "Esse recurso cadastra um novo produto"
    )
    @APIResponse(
        responseCode = "201",
        description = "Produto cadastrado com sucesso"
    )
    @APIResponse(
        responseCode = "500",
        description = "Erro interno servidor"
    )
    @Transactional
    @POST
    @Path("/produto")
    public Response criarProdduto(ProdutoDTO produtoDTO) {
        Produto produto = new Produto(produtoDTO.getNome(), produtoDTO.getDescricao(), produtoDTO.getPreco());
        produto.persist();

        return Response.status(Response.Status.CREATED).entity(produto).build();
    }

    @Operation(
        summary = "Busca Produtos",
        description = "Lista todos os produtos"
    )
    @APIResponse(
        responseCode = "200",
        description = "Retorna os produtos cadastrados"
    )
    @APIResponse(
        responseCode = "500",
        description = "Erro interno servidor"
    )
    @GET
    @Path("/produtos")
    public Response listarProduto() {
        return Response.status(Response.Status.OK).entity(Produto.listAll()).build();

    }

    @Operation(
        summary = "Buscar Produto por id",
        description = "Esse recurso busca um produto apartir de um id" 
    )
    @APIResponse(
        responseCode = "200",
        description = "Retorna os dados do produto apartir do id"
    )
    @APIResponse(
        responseCode = "500",
        description = "Erro interno servidor"
    )
    @APIResponse(
        responseCode = "404",
        description = "Not Found"
    )
    @GET
    @Path("/produto/{id}")
    public Response buscarProduto(@PathParam("id") long id) {

        Produto produto = Produto.findById(id);

        if (produto == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("Não foi possivel encontrar").build();
        }

        return Response.status(Response.Status.OK).entity(produto).build();

    }

    @Operation(
        summary = "Excluir Produto",
        description = "Esse recurso exclui um  produto"
    )
    @APIResponse(
        responseCode = "200",
        description = "Produto deletado com sucesso"
    )
    @APIResponse(
        responseCode = "500",
        description = "Erro interno servidor"
    )
    @DELETE
    @Path("/produto/{id}")
    @Transactional
    public Response deletarProduto(@PathParam("id") long id) {

        Produto produto = Produto.findById(id);

        if (produto == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("Não foi possivel encontrar").build();
        }
        Produto.deleteById(id);
        return Response.status(Response.Status.OK).entity("Excluido paizão").build();
    }


    @Operation(
        summary = "Atualiza um Produto apartir do id",
        description = "Esse recurso atualiza um produto apartir de um id" 
    )
    @APIResponse(
        responseCode = "200",
        description = "Produto alterado com sucesso"
    )
    @APIResponse(
        responseCode = "500",
        description = "Erro interno servidor"
    )
    @APIResponse(
        responseCode = "404",
        description = "Not Found"
    )
    @Transactional
    @PUT
    @Path("/produto/{id}")
    public Response atualizarProduto(@PathParam("id") long id, ProdutoDTO produtoDTO) {
        Produto produto = Produto.findById(id);

        if (produto == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("Não foi possivel encontrar").build();
        }

        produto.setNome(produtoDTO.getNome());
        produto.setDescricao(produtoDTO.getDescricao());
        produto.setPreco(produtoDTO.getPreco());

        return Response.status(Response.Status.OK).entity("Produto atualizar com sucesso").build();
        

    }
}