package br.com.nlw.events.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import br.com.nlw.events.model.User;
import br.com.nlw.events.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@Tag(name = "Usuários", description = "Gerenciar usuários na base de dados da aplicação")
public class UserController {
   
    @Autowired
    private UserService userService;

    @GetMapping("users")
    @Operation(summary = "Listar todos os usuários", description = "Retorna uma lista com todos os usuários no banco")
    public List<User> listAllUsers() {
        return userService.listAllUsers();
    }

    @GetMapping("user/{userId}")
    @Operation(summary = "Retorna um usuário baseado no seu ID")
    public User getUserById(@PathVariable Integer userId) {
        return userService.findUserById(userId);
    }
}
