package com.psn_cloud.psiconotes.controllers;

import com.psn_cloud.psiconotes.domain.Consulta;
import com.psn_cloud.psiconotes.dtos.ConsultaDto;
import com.psn_cloud.psiconotes.services.consulta.ConsultaService;
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
@RequestMapping("/consultas")
public class ConsultaController {

    @Autowired
    private ConsultaService consultaService;

    @PreAuthorize("hasAnyRole('PSICOLOGO','DEV', 'SECRETARIO')")
    @GetMapping
    public ResponseEntity<List<Consulta>> listarConsultas() {
        return ResponseEntity.status(HttpStatus.OK).body(consultaService.listarConsultas());
    }

    @PreAuthorize("hasAnyRole('PSICOLOGO','DEV', 'SECRETARIO')")
    @GetMapping("/{id}")
    public ResponseEntity<Consulta> listarConsulta(@PathVariable(name = "id") UUID id) {
        return ResponseEntity.status(HttpStatus.OK).body(consultaService.consultaPorId(id));
    }

    @PreAuthorize("hasAnyRole('PSICOLOGO','DEV', 'SECRETARIO')")
    @GetMapping("/psicologo/{psicologoId}")
    public ResponseEntity<List<Consulta>> listarPorPsicologo(@PathVariable(name = "psicologoId") UUID psicologoId) {
        return ResponseEntity.status(HttpStatus.OK).body(consultaService.listarConsultasPorPsicologo(psicologoId));
    }

    @PreAuthorize("hasAnyRole('PSICOLOGO','DEV', 'SECRETARIO')")
    @GetMapping("/paciente/{pacienteId}")
    public ResponseEntity<List<Consulta>> listarPorPaciente(@PathVariable(name = "pacienteId") UUID pacienteId) {
        return ResponseEntity.status(HttpStatus.OK).body(consultaService.listarConsultasPorPaciente(pacienteId));
    }

    @PreAuthorize("hasAnyRole('PSICOLOGO','DEV', 'SECRETARIO')")
    @PostMapping
    public ResponseEntity<Consulta> cadastrarConsulta(@Valid @RequestBody ConsultaDto consultaDto) {
        Consulta consulta = new Consulta();
        BeanUtils.copyProperties(consultaDto, consulta);
        return ResponseEntity.status(HttpStatus.OK).body(consultaService.cadastrarConsulta(consulta));
    }

    @PreAuthorize("hasAnyRole('PSICOLOGO','DEV', 'SECRETARIO')")
    @PatchMapping("/{id}/confirmar")
    public ResponseEntity<Consulta> confirmarConsulta(@PathVariable(name = "id") UUID id) {
        return ResponseEntity.status(HttpStatus.OK).body(consultaService.confirmarConsulta(id));
    }

    @PreAuthorize("hasAnyRole('PSICOLOGO','DEV', 'SECRETARIO')")
    @PatchMapping("/{id}/cancelar")
    public ResponseEntity<Consulta> cancelarConsulta(@PathVariable(name = "id") UUID id) {
        return ResponseEntity.status(HttpStatus.OK).body(consultaService.cancelarConsulta(id));
    }

    @PreAuthorize("hasAnyRole('PSICOLOGO','DEV')")
    @PatchMapping("/{id}/finalizar")
    public ResponseEntity<Consulta> finalizarConsulta(@PathVariable(name = "id") UUID id) {
        return ResponseEntity.status(HttpStatus.OK).body(consultaService.finalizarConsulta(id));
    }

    @PreAuthorize("hasAnyRole('PSICOLOGO','DEV', 'SECRETARIO')")
    @PatchMapping("/{id}/falta")
    public ResponseEntity<Consulta> marcarFalta(@PathVariable(name = "id") UUID id) {
        return ResponseEntity.status(HttpStatus.OK).body(consultaService.marcarFalta(id));
    }
}
