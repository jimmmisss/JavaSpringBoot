package com.simple.controllers;

import com.simple.models.Ator;
import com.simple.models.Filme;
import com.simple.repository.AtorRepository;
import com.simple.repository.FilmeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
public class FilmeController {

    @Autowired
    private FilmeRepository rf;

    @Autowired
    private AtorRepository ar;

    @RequestMapping(value="/addFilme", method = RequestMethod.GET)
    public String form(){
        return "filme/formFilme";
    }

    // MÉTODO QUE INSERE FILME NO BANCO DE DADOS
    // RETORNA MENSAGEM DE SUCESSO OU FALHA
    @RequestMapping(value = "/addFilme", method = RequestMethod.POST)
    public String form(@Valid Filme filme, BindingResult result, RedirectAttributes atributes){
        if(result.hasErrors()){
            atributes.addFlashAttribute("mensagem", "Preencha os campos obrigatórios");
            return "redirect:/addFilme";
        }
        rf.save(filme);
        atributes.addFlashAttribute("mensagem", "Filme adicionado com sucesso");
        return "redirect:/addFilme";
    }

    // MÉTODO QUE LISTA FILMES
    @RequestMapping("/filmes")
    public ModelAndView listaFilmes(){
        ModelAndView mv = new ModelAndView("index");
        Iterable<Filme> filmes = rf.findFilmeByContemAtor();
        mv.addObject("filmes", filmes);
        return mv;
    }

    // LISTA FILME E SEUS ATORES - TEMPLATE SOBRE FILME
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ModelAndView sobreFilme(@PathVariable("id") long id){
        Filme filme = rf.findById(id);
        ModelAndView mv = new ModelAndView("filme/sobreFilme");
        mv.addObject("filme", filme);
        mv.addObject("ator",filme.getAtores());
        return mv;
    }

    // MÉTODO QUE DELETA FILME
    @RequestMapping("/delFilme")
    public String delFilme(long id){
        Filme filme = rf.findById(id);
        rf.delete(filme);
        return "redirect:/filmes";
    }

    // MÉTODO QUE DELETA ATOR E RETORNA PARA O MESMO LOCAL
    @RequestMapping("/delAtor")
    public String delAtor(long id){
        Ator ator = ar.findById(id);
        ar.delete(ator);

        Filme filme = ator.getFilme();
        long idLong = filme.getId();
        String idAtual = "" + idLong;
        return "redirect:/" + idAtual;

    }

    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    public String sobreFilmePost(@PathVariable("id") long id, @Valid Ator ator, BindingResult result, RedirectAttributes atributes){
        if (result.hasErrors()){
            atributes.addFlashAttribute("mensagem", "Verifique os campos");
            return "redirect:/{id}";
        }
        Filme filme = rf.findById(id);
        filme.addAtor(ator);
        rf.save(filme);
        atributes.addFlashAttribute("mensagem", "Ator adcioando com sucesso");
        return "redirect:/{id}";
    }

}