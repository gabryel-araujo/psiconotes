package com.psn_cloud.psiconotes.controllers;

import com.psn_cloud.psiconotes.domain.Disponibilidade;
import com.psn_cloud.psiconotes.dtos.DisponibilidadeDto;
import com.psn_cloud.psiconotes.repositories.DisponibilidadeRepository;
import com.psn_cloud.psiconotes.services.disponibilidade.DisponibilidadeService;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/disponibilidade")
public class DisponibilidadeController {

    @Autowired
    DisponibilidadeService disponibilidadeService;

    @PreAuthorize("hasAnyRole('PSICOLOGO','DEV', 'SECRETARIO')")
    @GetMapping
    public ResponseEntity<List<Disponibilidade>> listarDisponibilidade(){
        return ResponseEntity.status(HttpStatus.OK).body(disponibilidadeService.listarTodos());
    }

    @PreAuthorize("hasAnyRole('PSICOLOGO','DEV', 'SECRETARIO')")
    @GetMapping("/{id}")
    public ResponseEntity<Disponibilidade> listarDisponibilidade(@PathVariable(name = "id") UUID id){
        return ResponseEntity.status(HttpStatus.OK).body(disponibilidadeService.buscarPorId(id));
    }

    @PreAuthorize("hasAnyRole('PSICOLOGO','DEV')")
    @PostMapping
    public ResponseEntity<Disponibilidade> cadastrarDisponibilidade(@Valid @RequestBody DisponibilidadeDto disponibilidadeDto){
        Disponibilidade disponibilidade = new Disponibilidade();
        BeanUtils.copyProperties(disponibilidadeDto, disponibilidade);
        return ResponseEntity.status(HttpStatus.OK).body(disponibilidadeService.criar(disponibilidade));
    }

    @PreAuthorize("hasAnyRole('PSICOLOGO','DEV')")
    @PutMapping("/{id}")
    public ResponseEntity<Disponibilidade> editarDisponibilidade(@Valid @RequestBody DisponibilidadeDto disponibilidadeDto, @PathVariable(name = "id") UUID id){
        Disponibilidade disponibilidade = new Disponibilidade();
        BeanUtils.copyProperties(disponibilidadeDto, disponibilidade);
        return ResponseEntity.status(HttpStatus.OK).body(disponibilidadeService.atualizar(id, disponibilidade));
    }
}
