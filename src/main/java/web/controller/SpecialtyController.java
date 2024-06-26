package web.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import web.entity.Optional;
import web.entity.Specialty;
import web.entity.User;
import web.entity.input.FilterStatus;
import web.entity.input.SpecialtyQuery;
import web.logicImpl.SpecialtyLogicImpl;
import web.controller.AuthUtils;

@RestController
@RequestMapping("/specialties")
public class SpecialtyController {
    
    @Autowired
    private SpecialtyLogicImpl specialties;
    
    @Autowired
    private AuthUtils auth;
    
    // Acciones Generales
    
    @GetMapping
    public List<Specialty> search(
            @RequestParam(required = false, defaultValue = "") String q, 
            @RequestParam(required = false, defaultValue = "") FilterStatus status,
            @RequestParam(required = false, defaultValue = "1") int page,
            @RequestParam(required = false, defaultValue = "15") int size,
            HttpServletRequest req, HttpServletResponse res
            ) {
        User requiring = auth.require(req, res);
        return specialties.search(new SpecialtyQuery(q, status).paginate(page, size), requiring);
    }
    
    @PostMapping
    public Specialty addSpecialty(@RequestBody Specialty specialty, HttpServletRequest req, HttpServletResponse res) {
        User requiring = auth.require(req, res);
        specialties.add(specialty, requiring);
        return specialty;
    }
    
    // Acciones con Terceros
    
    @GetMapping("/s/{id}")
    public Optional<Specialty> findById(@PathVariable int id, HttpServletRequest req, HttpServletResponse res) {
        User requiring = auth.require(req, res);
        return specialties.findById(id);
    }
    
    @PatchMapping("/s/{id}")
    public Specialty update(@PathVariable int id, @RequestBody Specialty specialty, HttpServletRequest req, HttpServletResponse res) {
        User requiring = auth.require(req, res);
        specialty.setId(id);
        specialties.update(specialty, requiring);
        return specialty;
    }
    
    @DeleteMapping("/s/{id}")
    public ResponseEntity<?> disable(@PathVariable int id, HttpServletRequest req, HttpServletResponse res) {
        User requiring = auth.require(req, res);
        specialties.disable(id, requiring);
        return ResponseEntity.status(200).build();
    }
}
