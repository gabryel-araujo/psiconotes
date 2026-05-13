package com.psn_cloud.psiconotes.controllers;

import com.psn_cloud.psiconotes.domain.Psicologo;
import com.psn_cloud.psiconotes.dtos.PsicologoRecordDto;
import com.psn_cloud.psiconotes.repositories.PsicologoRepository;
import com.psn_cloud.psiconotes.services.psicologoService.PsicologoService;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/psicologos")
public class PsicologoController {

    @Autowired
    PsicologoRepository psicologoRepository;

    @Autowired
    PsicologoService psicologoService;

    @GetMapping
    public ResponseEntity<List<Psicologo>> listarPsicologos(){
        return ResponseEntity.status(HttpStatus.OK).body(psicologoService.listarTodos());
    }

    @PostMapping
    public ResponseEntity<Psicologo> cadastrarPsicologo(@Valid @RequestBody PsicologoRecordDto psicologoRecordDto){
        Psicologo psicologo = new Psicologo();
        BeanUtils.copyProperties(psicologoRecordDto,psicologo);
        return ResponseEntity.status(HttpStatus.OK).body(psicologoService.criar(psicologo));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Psicologo> editarPsicologo(@PathVariable(name = "id") UUID id,@Valid @RequestBody PsicologoRecordDto psicologoRecordDto){
        Optional<Psicologo> optionalPsicologo = psicologoRepository.findById(id);
        Psicologo psicologo = optionalPsicologo.get();
        BeanUtils.copyProperties(psicologoRecordDto, psicologo);
        return ResponseEntity.status(HttpStatus.OK).body(psicologoService.atualizar(id,psicologo));
    }
}
