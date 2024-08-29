package br.com.fiap.jadv.sprint2.controller.mvc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import br.com.fiap.jadv.sprint2.dto.request.UsuarioRequestDTO;
import br.com.fiap.jadv.sprint2.service.UsuarioService;

@Controller
@RequestMapping("/usuarios")
public class UsuarioMvcController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping
    public String listarUsuarios(Model model) {
        model.addAttribute("usuarios", usuarioService.obterTodosUsuarios());
        return "usuarios";
    }

    @GetMapping("/{id}")
    public String visualizarUsuario(@PathVariable Long id, Model model) {
        usuarioService.obterUsuarioPorId(id).ifPresent(usuario -> model.addAttribute("usuario", usuario));
        return "usuario-view";
    }

    @GetMapping("/novo")
    public String novoUsuarioForm(Model model) {
        model.addAttribute("usuario", new UsuarioRequestDTO());
        return "usuario-form";
    }

    @PostMapping("/salvar")
    public String salvarUsuario(@ModelAttribute UsuarioRequestDTO usuarioRequestDTO) {
        usuarioService.criarUsuario(usuarioRequestDTO);
        return "redirect:/usuarios";
    }

    @GetMapping("/editar/{id}")
    public String editarUsuarioForm(@PathVariable Long id, Model model) {
        usuarioService.obterUsuarioPorId(id).ifPresent(usuario -> model.addAttribute("usuario", usuario));
        return "usuario-form";
    }

    @PostMapping("/atualizar/{id}")
    public String atualizarUsuario(@PathVariable Long id, @ModelAttribute UsuarioRequestDTO usuarioRequestDTO) {
        usuarioService.atualizarUsuario(id, usuarioRequestDTO);
        return "redirect:/usuarios";
    }

    @GetMapping("/deletar/{id}")
    public String deletarUsuario(@PathVariable Long id) {
        usuarioService.deletarUsuario(id);
        return "redirect:/usuarios";
    }
}
