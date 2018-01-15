package com.simple.controllers;

import com.simple.models.Ator;
import com.simple.models.Filme;
import com.simple.repository.AtoresRepository;
import com.simple.repository.FilmeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class FilmeController {

    @Autowired
    private FilmeRepository rf;

    @Autowired
    private AtoresRepository ar;

    @RequestMapping(value="/addFilme", method = RequestMethod.GET)
    public String form(){
        return "filme/formFilme";
    }

    @RequestMapping(value = "/addFilme", method = RequestMethod.POST)
    public String form(Filme filme){
        rf.save(filme);
        return "redirect:/addFilme";
    }

    @RequestMapping("/filmes")
    public ModelAndView listaFilmes(){
        ModelAndView mv = new ModelAndView("index");
        Iterable<Filme> filmes = rf.findAll();
        mv.addObject("filmes", filmes);
        return mv;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ModelAndView sobreFilme(@PathVariable("id") long id){
        Filme filme = rf.findById(id);
        ModelAndView mv = new ModelAndView("filme/sobreFilme");
        mv.addObject("filme", filme);
        mv.addObject("ator",filme.getAtores());
        return mv;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    public String sobreFilmePost(@PathVariable("id") long id, Ator ator){
        Filme filme = rf.findById(id);
        filme.addAtor(ator);
        rf.save(filme);
        return "redirect:/{id}";
    }

}