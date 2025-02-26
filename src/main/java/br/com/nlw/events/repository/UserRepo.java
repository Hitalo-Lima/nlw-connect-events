package br.com.nlw.events.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import br.com.nlw.events.model.User;

public interface UserRepo extends CrudRepository<User, Integer>{
    public Optional<User> findByEmail(String email);
}
