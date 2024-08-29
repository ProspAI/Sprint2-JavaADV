package br.com.fiap.jadv.sprint2.controller.mvc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import br.com.fiap.jadv.sprint2.dto.request.ClienteRequestDTO;
import br.com.fiap.jadv.sprint2.service.ClienteService;

@Controller
@RequestMapping("/clientes")
public class ClienteMvcController {

    @Autowired
    private ClienteService clienteService;

    // Listar todos os clientes
    @GetMapping
    public String listarClientes(Model model) {
        model.addAttribute("clientes", clienteService.obterTodosClientes());
        return "clientes/clientes";  // NÃO adicionar "/WEB-INF/templates/"
    }

    // Visualizar um cliente específico
    @GetMapping("/{id}")
    public String visualizarCliente(@PathVariable Long id, Model model) {
        return clienteService.obterClientePorId(id)
                .map(cliente -> {
                    model.addAttribute("cliente", cliente);
                    return "clientes/cliente-view";  // Usando o template cliente-view.html dentro da pasta clientes
                })
                .orElse("redirect:/clientes");
    }

    // Formulário para criar um novo cliente
    @GetMapping("/novo")
    public String novoClienteForm(Model model) {
        model.addAttribute("cliente", new ClienteRequestDTO());
        return "clientes/cliente-form";  // Usando o template cliente-form.html dentro da pasta clientes
    }

    // Salvar o novo cliente
    @PostMapping("/salvar")
    public String salvarCliente(@ModelAttribute ClienteRequestDTO clienteRequestDTO) {
        clienteService.criarCliente(clienteRequestDTO);
        return "redirect:/clientes";
    }

    // Formulário para editar um cliente existente
    @GetMapping("/editar/{id}")
    public String editarClienteForm(@PathVariable Long id, Model model) {
        return clienteService.obterClientePorId(id)
                .map(cliente -> {
                    model.addAttribute("cliente", cliente);
                    return "clientes/cliente-form";  // Usando o template cliente-form.html dentro da pasta clientes
                })
                .orElse("redirect:/clientes");
    }

    // Atualizar um cliente existente
    @PostMapping("/atualizar/{id}")
    public String atualizarCliente(@PathVariable Long id, @ModelAttribute ClienteRequestDTO clienteRequestDTO) {
        clienteService.atualizarCliente(id, clienteRequestDTO);
        return "redirect:/clientes";
    }

    // Deletar um cliente
    @GetMapping("/deletar/{id}")
    public String deletarCliente(@PathVariable Long id) {
        clienteService.deletarCliente(id);
        return "redirect:/clientes";
    }
}
