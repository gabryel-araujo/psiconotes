package com.psn_cloud.psiconotes.controllers;

import com.psn_cloud.psiconotes.domain.Psicologo;
import com.psn_cloud.psiconotes.dtos.PsicologoDto;
import com.psn_cloud.psiconotes.services.psicologoService.PsicologoService;
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
@RequestMapping("/psicologos")
public class PsicologoController {

    @Autowired
    PsicologoService psicologoService;

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'PSICOLOGO', 'SECRETARIO','DEV')")
    public ResponseEntity<List<Psicologo>> listarPsicologos(){
        return ResponseEntity.status(HttpStatus.OK).body(psicologoService.listarTodos());
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN','DEV')")
    public ResponseEntity<Psicologo> cadastrarPsicologo(@Valid @RequestBody PsicologoDto psicologoDto){
        Psicologo psicologo = new Psicologo();
        BeanUtils.copyProperties(psicologoDto,psicologo);
        return ResponseEntity.status(HttpStatus.OK).body(psicologoService.criar(psicologo));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'PSICOLOGO','DEV')")
    public ResponseEntity<Psicologo> editarPsicologo(@PathVariable(name = "id") UUID id, @Valid @RequestBody PsicologoDto psicologoDto){
        Psicologo psicologoExistente = psicologoService.buscarPorId(id);
        BeanUtils.copyProperties(psicologoDto, psicologoExistente);
        return ResponseEntity.status(HttpStatus.OK).body(psicologoService.atualizar(id, psicologoExistente));
    }
}
