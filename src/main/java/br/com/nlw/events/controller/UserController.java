package br.com.nlw.events.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.nlw.events.model.User;
import br.com.nlw.events.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RequestMapping("user")
@RestController
@Tag(name = "Usuários", description = "Gerenciar usuários na base de dados da aplicação")
public class UserController {
   
    @Autowired
    private UserService userService;

    @GetMapping("/list")
    @Operation(summary = "Listar todos os usuários", description = "Retorna uma lista com todos os usuários no banco")
    public ResponseEntity<Object> listAllUsers() {
        List<User> userList =  userService.listAllUsers();

        if (userList.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Não há usuários cadastrados na base de dados.");
        }

        return ResponseEntity.ok(userList);
    }

    @GetMapping("/{userId}")
    @Operation(summary = "Retorna um usuário baseado no seu ID")
    public ResponseEntity<?> getUserById(@PathVariable Integer userId) {
        var userFind = userService.findUserById(userId);

        if (userFind == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário " + userId + " não encontrado.");
        }

        return ResponseEntity.ok(userService.findUserById(userId));
    }

    @PostMapping("/create")
    public ResponseEntity<Object> addNewUser(@RequestBody User user) {
        if (user.getName() == null || user.getEmail() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Os campos nome e email são obrigatórios.");
        }

        User userFind = userService.findUserByEmail(user.getEmail());

        if (userFind != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("O email informado já existe.");
        }

        return ResponseEntity.ok(userService.addNewUser(user));
    }
}
