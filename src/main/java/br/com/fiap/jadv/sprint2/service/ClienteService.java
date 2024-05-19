package br.com.fiap.jadv.sprint2.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Service;

import br.com.fiap.jadv.sprint2.controller.ClienteController;
import br.com.fiap.jadv.sprint2.dto.request.ClienteRequestDTO;
import br.com.fiap.jadv.sprint2.dto.response.ClienteResponseDTO;
import br.com.fiap.jadv.sprint2.entity.Cliente;
import br.com.fiap.jadv.sprint2.repository.ClienteRepository;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    public List<ClienteResponseDTO> obterTodosClientes() {
        return clienteRepository.findAll().stream().map(this::converterParaResponseDTO).collect(Collectors.toList());
    }

    public Optional<ClienteResponseDTO> obterClientePorId(Long id) {
        return clienteRepository.findById(id).map(this::converterParaResponseDTO);
    }

    public ClienteResponseDTO criarCliente(ClienteRequestDTO clienteRequestDTO) {
        Cliente cliente = new Cliente();
        cliente.setNome(clienteRequestDTO.getNome());
        cliente.setEmail(clienteRequestDTO.getEmail());
        cliente.setTelefone(clienteRequestDTO.getTelefone());
        return converterParaResponseDTO(clienteRepository.save(cliente));
    }

    public Optional<ClienteResponseDTO> atualizarCliente(Long id, ClienteRequestDTO clienteRequestDTO) {
        return clienteRepository.findById(id).map(cliente -> {
            cliente.setNome(clienteRequestDTO.getNome());
            cliente.setEmail(clienteRequestDTO.getEmail());
            cliente.setTelefone(clienteRequestDTO.getTelefone());
            return converterParaResponseDTO(clienteRepository.save(cliente));
        });
    }

    public void deletarCliente(Long id) {
        clienteRepository.deleteById(id);
    }

    private ClienteResponseDTO converterParaResponseDTO(Cliente cliente) {
        ClienteResponseDTO clienteResponseDTO = new ClienteResponseDTO();
        clienteResponseDTO.setId(cliente.getId());
        clienteResponseDTO.setNome(cliente.getNome());
        clienteResponseDTO.setEmail(cliente.getEmail());
        clienteResponseDTO.setTelefone(cliente.getTelefone());

        // Adicionando links HATEOAS
        clienteResponseDTO.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ClienteController.class).obterClientePorId(cliente.getId())).withSelfRel());
        clienteResponseDTO.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ClienteController.class).obterTodosClientes()).withRel("todos-clientes"));
        
        return clienteResponseDTO;
    }
}
