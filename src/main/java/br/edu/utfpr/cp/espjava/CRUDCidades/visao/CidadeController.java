package br.edu.utfpr.cp.espjava.CRUDCidades.visao;

import java.util.HashSet;
import java.util.Set;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.validation.Valid;

@Controller
public class CidadeController {
    
    /**
     * Objeto Set para armazenar a lista de cidades
     * 
     * Set
     */
    private Set<Cidade> cidades;

    /**
     * Lista mutável, inserir e remover cidades da lista.
     * O hashset é uma implementação do Set, assim como arraylist de list
     * Fazem parte de Collections.
     */
    public CidadeController(){
        cidades = new HashSet<>();
    }
    
    /**
     * Método para listar cidades.
     */
    @GetMapping("/")
    public String listar(Model memoria){

        memoria.addAttribute("listaCidades", cidades);

        return "/crud";
    }

    @PostMapping("/criar")
    public String criar(@Valid Cidade cidade, BindingResult validacao, Model memoria){

        if(validacao.hasErrors()){
                   
            validacao
            .getFieldErrors()
            .forEach(
                error -> 
                memoria.addAttribute(error.getField(), error.getDefaultMessage()));
                memoria.addAttribute("nomeInformado", cidade.getNome());
                memoria.addAttribute("estadoInformado", cidade.getEstado());
                memoria.addAttribute("listaCidades", cidades);
            return "/crud";
        } else {
            cidades.add(cidade);

        }

        return "redirect:/";
    }

    @GetMapping("/excluir")
    public String excluir(
        @RequestParam String nome,
        @RequestParam String estado ){

            cidades.removeIf(cidadeAtual ->
                cidadeAtual.getNome().equals(nome) &&
                cidadeAtual.getEstado().equals(estado)

            );

            return "redirect:/";

    }

    @GetMapping("/alterar")
    public String alterar(
        @RequestParam String nomeAtual,
        @RequestParam String estadoAtual,
        Cidade cidade,
        BindingResult validacao,
        Model memoria
        ){

            cidades.removeIf( cidadeAtual -> 
                                cidadeAtual.getNome().equals(nomeAtual) &&
                                cidadeAtual.getEstado().equals(estadoAtual));
                        
                                criar(cidade, validacao, memoria);
                    

            return "redirect:/";

    }

}
