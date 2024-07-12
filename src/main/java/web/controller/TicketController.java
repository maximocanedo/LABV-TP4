package web.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import web.entity.Optional;
import web.entity.Ticket;
import web.entity.User;
import web.entity.input.FilterStatus;
import web.entity.input.TicketQuery;
import web.logicImpl.TicketLogicImpl;

@RestController
@RequestMapping("/tickets")
@CrossOrigin(origins = "*", allowedHeaders = "*", exposedHeaders = { "Authorization", "Content-Length" })
public class TicketController {
    
    @Autowired
    private TicketLogicImpl tickets;
    
    @Autowired
    private AuthUtils auth;
    
    // Acciones Generales

    @InitBinder
    public void initBinder(HttpServletRequest req, HttpServletResponse res) {
        auth.preHandle(req, res);
    }
    
    @GetMapping
    public List<Ticket> search(
            @RequestParam(required = false, defaultValue = "") String q, 
            @RequestParam(required = false, defaultValue = "") FilterStatus status,
            @RequestParam(required = false, defaultValue = "1") int page,
            @RequestParam(required = false, defaultValue = "15") int size,
            HttpServletRequest req, HttpServletResponse res
            ) {
        User requiring = auth.require(req, res);
        return tickets.search(new TicketQuery(q, status).paginate(page, size), requiring);
    }
    
    @PostMapping
    public Ticket addTicket(@RequestBody Ticket ticket, HttpServletRequest req, HttpServletResponse res) {
        User requiring = auth.require(req, res);
        tickets.add(ticket, requiring);
        return ticket;
    }
    
    // Acciones con Terceros
    
    @GetMapping("/t/{id}")
    public Optional<Ticket> findById(@PathVariable int id, HttpServletRequest req, HttpServletResponse res) {
        auth.require(req, res);
        return tickets.findById(id);
    }
    
    @PatchMapping("/t/{id}")
    public Ticket update(@PathVariable int id, @RequestBody Ticket ticket, HttpServletRequest req, HttpServletResponse res) {
        User requiring = auth.require(req, res);
        ticket.setId(id);
        tickets.update(ticket, requiring);
        return ticket;
    }
    
}
