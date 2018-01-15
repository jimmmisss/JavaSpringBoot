package com.simple.repository;

import com.simple.models.Ator;
import com.simple.models.Filme;
import org.springframework.data.repository.CrudRepository;

public interface AtoresRepository extends CrudRepository<Ator, String>{
    Iterable<Ator> findByFilme(Filme filme);
}