package br.edu.utfpr.cp.espjava.CRUDCidades.cidade;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

//import org.springframework.data.jpa.repository.JpaRepository;

import jakarta.validation.Valid;

@Controller
public class CidadeController {
    
    /**
     * Objeto Set para armazenar a lista de cidades
     * 
     * Set
     private Set<Cidade> cidades;
     */

    private final CidadeRepository repository;

    /**
     * Lista mutável, inserir e remover cidades da lista.
     * O hashset é uma implementação do Set, assim como arraylist de list
     * Fazem parte de Collections.
     */
    public CidadeController(final CidadeRepository repository){
        this.repository = repository;
    }
    
    /**
     * Método para listar cidades.
     */
    @GetMapping("/")
    public String listar(Model memoria){

        memoria.addAttribute("listaCidades", this.converteCidade(repository.findAll()));

        return "/crud";
    }

    private List<Cidade> converteCidade( List<CidadeEntidade> cidades ){

        return cidades.stream()
                .map(
                      cidade -> new Cidade(
                      cidade.getNome(), 
                      cidade.getEstado()))
                .collect(Collectors.toList());
    }

    @PostMapping("/criar")
    public String criar(@Valid Cidade cidade, BindingResult validacao, Model memoria){

        if(validacao.hasErrors()){
                   
            validacao
            .getFieldErrors()
            .forEach(
                error -> memoria.addAttribute( 
                    error.getField(), 
                    error.getDefaultMessage()));

                memoria.addAttribute("nomeInformado", cidade.getNome());
                memoria.addAttribute("estadoInformado", cidade.getEstado());
                memoria.addAttribute("listaCidades", this.converteCidade(repository.findAll()));
            return "/crud";
        } else {
            /*
            var novaCidade = new CidadeEntidade();
            novaCidade.setNome(cidade.getNome());
            novaCidade.setEstado(cidade.getEstado());
            */
            
            repository.save(cidade.clonar());
            //cidades.add(cidade);

        }

        return "redirect:/";
    }

    @GetMapping("/excluir")
    public String excluir(
        @RequestParam String nome,
        @RequestParam String estado ){

            var cidadeEstadoEncontrada = repository.findByNomeAndEstado(nome, estado);
            cidadeEstadoEncontrada.ifPresent(repository::delete);

            return "redirect:/";

    }

    @GetMapping("/preparaAlterar")
    public String preparaAlterar(
        @RequestParam String nome,
        @RequestParam String estado,
        Model memoria){
            var cidadeAtual = repository.findByNomeAndEstado(nome, estado);
                cidadeAtual.ifPresent(cidadeEncontrada -> {
                    memoria.addAttribute("cidadeAtual", cidadeEncontrada);
                    memoria.addAttribute("listaCidades", repository.findAll());
                });

        return "/crud";
    }

    @GetMapping("/alterar")
    public String alterar(
        @RequestParam String nomeAtual,
        @RequestParam String estadoAtual,
        Cidade cidade){

            var cidadeAtual = repository.findByNomeAndEstado(nomeAtual, estadoAtual);        

            if(cidadeAtual.isPresent()){

                var cidadeEncontrada = cidadeAtual.get();
                cidadeEncontrada.setNome(cidade.getNome());
                cidadeEncontrada.setEstado(cidade.getEstado());

                repository.saveAndFlush(cidadeEncontrada);
            }
            return "redirect:/";

    }

}
