package br.com.nlw.events.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.nlw.events.model.User;
import br.com.nlw.events.repository.UserRepo;

@Service
public class UserService {

    @Autowired
    private UserRepo userRepo;

    public List<User> listAllUsers() {
        return (List<User>)userRepo.findAll();
    }

    public User findUserById(Integer idUser) {
        return userRepo.findById(idUser).orElse(null);
    }

    public User findUserByEmail(String email) {
        return userRepo.findByEmail(email).orElse(null);
    }

    public User addNewUser(User user) {
        return userRepo.save(user);
    }
}
