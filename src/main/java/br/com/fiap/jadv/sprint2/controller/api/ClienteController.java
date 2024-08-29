package br.com.fiap.jadv.sprint2.controller.api;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.fiap.jadv.sprint2.dto.request.ClienteRequestDTO;
import br.com.fiap.jadv.sprint2.dto.response.ClienteResponseDTO;
import br.com.fiap.jadv.sprint2.service.ClienteService;

@RestController
@RequestMapping("api/clientes")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<ClienteResponseDTO>>> obterTodosClientes() {
        List<EntityModel<ClienteResponseDTO>> clientes = clienteService.obterTodosClientes().stream()
                .map(cliente -> EntityModel.of(cliente,
                        WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ClienteController.class).obterClientePorId(cliente.getId())).withSelfRel(),
                        WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ClienteController.class).obterTodosClientes()).withRel("todos-clientes")))
                .collect(Collectors.toList());

        return ResponseEntity.ok(CollectionModel.of(clientes,
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ClienteController.class).obterTodosClientes()).withSelfRel()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<ClienteResponseDTO>> obterClientePorId(@PathVariable Long id) {
        Optional<ClienteResponseDTO> cliente = clienteService.obterClientePorId(id);
        return cliente.map(c -> ResponseEntity.ok(EntityModel.of(c,
                        WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ClienteController.class).obterClientePorId(c.getId())).withSelfRel(),
                        WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ClienteController.class).obterTodosClientes()).withRel("todos-clientes"))))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<EntityModel<ClienteResponseDTO>> criarCliente(@Validated @RequestBody ClienteRequestDTO clienteRequestDTO) {
        ClienteResponseDTO clienteCriado = clienteService.criarCliente(clienteRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(EntityModel.of(clienteCriado,
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ClienteController.class).obterClientePorId(clienteCriado.getId())).withSelfRel(),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ClienteController.class).obterTodosClientes()).withRel("todos-clientes")));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<ClienteResponseDTO>> atualizarCliente(@PathVariable Long id, @Validated @RequestBody ClienteRequestDTO clienteRequestDTO) {
        Optional<ClienteResponseDTO> clienteAtualizado = clienteService.atualizarCliente(id, clienteRequestDTO);
        return clienteAtualizado.map(c -> ResponseEntity.ok(EntityModel.of(c,
                        WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ClienteController.class).obterClientePorId(c.getId())).withSelfRel(),
                        WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ClienteController.class).obterTodosClientes()).withRel("todos-clientes"))))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarCliente(@PathVariable Long id) {
        clienteService.deletarCliente(id);
        return ResponseEntity.noContent().build();
    }
}