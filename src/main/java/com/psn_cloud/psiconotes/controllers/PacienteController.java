package com.psn_cloud.psiconotes.controllers;

import com.psn_cloud.psiconotes.domain.Paciente;
import com.psn_cloud.psiconotes.dtos.PacienteRecordDto;
import com.psn_cloud.psiconotes.repositories.PacienteRepository;
import com.psn_cloud.psiconotes.services.pacienteService.PacienteService;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/pacientes")
public class PacienteController {

    @Autowired
    private PacienteService pacienteService;

    @GetMapping
    @PreAuthorize("hasAnyRole('DEV', 'ADMIN', 'PSICOLOGO', 'SECRETARIO')")
    public ResponseEntity<List<Paciente>> getPacientes(){
        return ResponseEntity.status(HttpStatus.OK).body(pacienteService.listarTodos());
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('DEV', 'ADMIN', 'PSICOLOGO', 'SECRETARIO')")
    public ResponseEntity<Paciente> addPaciente(@RequestBody PacienteRecordDto pacientedto){
        Paciente paciente = new Paciente();
        BeanUtils.copyProperties(pacientedto, paciente);
        return ResponseEntity.status(HttpStatus.OK).body(pacienteService.criar(paciente));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('DEV', 'ADMIN', 'PSICOLOGO', 'SECRETARIO')")
    public ResponseEntity<Paciente> updatePaciente(@PathVariable(name = "id") UUID id, @Valid @RequestBody PacienteRecordDto paciente){
        Paciente pacienteExistente = pacienteService.buscarPorId(id);
        BeanUtils.copyProperties(paciente, pacienteExistente);
        return ResponseEntity.status(HttpStatus.OK).body(pacienteService.atualizar(id, pacienteExistente));
    }
}
