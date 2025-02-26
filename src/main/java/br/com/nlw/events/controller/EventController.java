package br.com.nlw.events.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.nlw.events.model.Event;
import br.com.nlw.events.service.EventService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

@RequestMapping("/events")
@RestController
@Tag(name = "Eventos", description = "Gerencia os eventos da aplicação")
public class EventController {

    @Autowired
    private EventService service;

    @PostMapping("create")
    @Operation(summary = "Criar um novo evento", description = "Adicionar um novo evento ao banco de dados")
    public Event addNewEvent(@RequestBody Event newEvent) {
       return service.addNewEvent(newEvent); 
    }

    @GetMapping
    @Operation(summary = "Listar todos os eventos", description = "Retorna uma lista de eventos cadastrados")
    public List<Event> getAllEvents() {
        return service.getAllEvents();
    }

    @GetMapping("/{prettyName}")
    @Operation(summary = "Retornar um evento pelo nome", description = "Retorna um evento baseado em seu prettyName")
    public ResponseEntity<Event> getEventByPrettyName(@Parameter(description = "Prettyname do evento") @PathVariable String prettyName) {
        Event evt = service.getByPrettyName(prettyName);
        if (evt != null) { // evento existe no banco de dados
            return ResponseEntity.ok().body(evt);
        }
        
        // retorna o status code 404 not found com o corpo vazio
        return ResponseEntity.notFound().build();
    }
}
