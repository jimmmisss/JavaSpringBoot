package com.simple.repository;

import com.simple.models.Filme;
import org.springframework.data.repository.CrudRepository;

public interface FilmeRepository extends CrudRepository<Filme, Long>{
    Filme findById(long id);
}