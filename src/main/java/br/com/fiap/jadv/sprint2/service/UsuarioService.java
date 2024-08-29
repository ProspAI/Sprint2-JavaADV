package br.com.fiap.jadv.sprint2.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Service;

import br.com.fiap.jadv.sprint2.controller.api.UsuarioController;
import br.com.fiap.jadv.sprint2.dto.request.UsuarioRequestDTO;
import br.com.fiap.jadv.sprint2.dto.response.UsuarioResponseDTO;
import br.com.fiap.jadv.sprint2.entity.Usuario;
import br.com.fiap.jadv.sprint2.repository.UsuarioRepository;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public List<UsuarioResponseDTO> obterTodosUsuarios() {
        return usuarioRepository.findAll().stream().map(this::converterParaResponseDTO).collect(Collectors.toList());
    }

    public Optional<UsuarioResponseDTO> obterUsuarioPorId(Long id) {
        return usuarioRepository.findById(id).map(this::converterParaResponseDTO);
    }

    public UsuarioResponseDTO criarUsuario(UsuarioRequestDTO usuarioRequestDTO) {
        Usuario usuario = new Usuario();
        usuario.setNome(usuarioRequestDTO.getNome());
        usuario.setEmail(usuarioRequestDTO.getEmail());
        usuario.setSenha(usuarioRequestDTO.getSenha());
        return converterParaResponseDTO(usuarioRepository.save(usuario));
    }

    public Optional<UsuarioResponseDTO> atualizarUsuario(Long id, UsuarioRequestDTO usuarioRequestDTO) {
        return usuarioRepository.findById(id).map(usuario -> {
            usuario.setNome(usuarioRequestDTO.getNome());
            usuario.setEmail(usuarioRequestDTO.getEmail());
            usuario.setSenha(usuarioRequestDTO.getSenha());
            return converterParaResponseDTO(usuarioRepository.save(usuario));
        });
    }

    public void deletarUsuario(Long id) {
        usuarioRepository.deleteById(id);
    }

    private UsuarioResponseDTO converterParaResponseDTO(Usuario usuario) {
        UsuarioResponseDTO usuarioResponseDTO = new UsuarioResponseDTO();
        usuarioResponseDTO.setId(usuario.getId());
        usuarioResponseDTO.setNome(usuario.getNome());
        usuarioResponseDTO.setEmail(usuario.getEmail());

        // Adicionando links HATEOAS
        usuarioResponseDTO.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UsuarioController.class).obterUsuarioPorId(usuario.getId())).withSelfRel());
        usuarioResponseDTO.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UsuarioController.class).obterTodosUsuarios()).withRel("todos-usuarios"));
        
        return usuarioResponseDTO;
    }
}
