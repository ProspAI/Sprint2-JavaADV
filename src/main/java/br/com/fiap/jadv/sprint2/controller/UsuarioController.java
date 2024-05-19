package br.com.fiap.jadv.sprint2.controller;

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

import br.com.fiap.jadv.sprint2.dto.request.UsuarioRequestDTO;
import br.com.fiap.jadv.sprint2.dto.response.UsuarioResponseDTO;
import br.com.fiap.jadv.sprint2.service.UsuarioService;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<UsuarioResponseDTO>>> obterTodosUsuarios() {
        List<EntityModel<UsuarioResponseDTO>> usuarios = usuarioService.obterTodosUsuarios().stream()
                .map(usuario -> EntityModel.of(usuario,
                        WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UsuarioController.class).obterUsuarioPorId(usuario.getId())).withSelfRel(),
                        WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UsuarioController.class).obterTodosUsuarios()).withRel("todos-usuarios")))
                .collect(Collectors.toList());
        
        return ResponseEntity.ok(CollectionModel.of(usuarios,
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UsuarioController.class).obterTodosUsuarios()).withSelfRel()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<UsuarioResponseDTO>> obterUsuarioPorId(@PathVariable Long id) {
        Optional<UsuarioResponseDTO> usuario = usuarioService.obterUsuarioPorId(id);
        return usuario.map(u -> ResponseEntity.ok(EntityModel.of(u,
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UsuarioController.class).obterUsuarioPorId(u.getId())).withSelfRel(),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UsuarioController.class).obterTodosUsuarios()).withRel("todos-usuarios"))))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<EntityModel<UsuarioResponseDTO>> criarUsuario(@Validated @RequestBody UsuarioRequestDTO usuarioRequestDTO) {
        UsuarioResponseDTO usuarioCriado = usuarioService.criarUsuario(usuarioRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(EntityModel.of(usuarioCriado,
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UsuarioController.class).obterUsuarioPorId(usuarioCriado.getId())).withSelfRel(),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UsuarioController.class).obterTodosUsuarios()).withRel("todos-usuarios")));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<UsuarioResponseDTO>> atualizarUsuario(@PathVariable Long id, @Validated @RequestBody UsuarioRequestDTO usuarioRequestDTO) {
        Optional<UsuarioResponseDTO> usuarioAtualizado = usuarioService.atualizarUsuario(id, usuarioRequestDTO);
        return usuarioAtualizado.map(u -> ResponseEntity.ok(EntityModel.of(u,
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UsuarioController.class).obterUsuarioPorId(u.getId())).withSelfRel(),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UsuarioController.class).obterTodosUsuarios()).withRel("todos-usuarios"))))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarUsuario(@PathVariable Long id) {
        usuarioService.deletarUsuario(id);
        return ResponseEntity.noContent().build();
    }
}
