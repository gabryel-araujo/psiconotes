package com.psn_cloud.psiconotes.controllers;

import com.psn_cloud.psiconotes.config.multitenancy.TenantContext;
import com.psn_cloud.psiconotes.config.security.JwtService;
import com.psn_cloud.psiconotes.domain.OrganizacaoUsuario;
import com.psn_cloud.psiconotes.dtos.AuthDto;
import com.psn_cloud.psiconotes.dtos.RegisterDto;
import com.psn_cloud.psiconotes.repositories.OrganizacaoUsuarioRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private OrganizacaoUsuarioRepository organizacaoUsuarioRepository;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid AuthDto data) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(data.email(), data.senha());
        var auth = this.authenticationManager.authenticate(usernamePassword);

        // Extrai o usuario autenticado
        OrganizacaoUsuario usuarioAutenticado = (OrganizacaoUsuario) auth.getPrincipal();
        
        // Define dinamicamente o tenant baseado no organizacaoId do usuário (ex: "o1")
        String tenantName = "o" + usuarioAutenticado.getOrganizacaoId();

        var token = jwtService.generateToken(usuarioAutenticado, tenantName);

        return ResponseEntity.ok(token);
    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody @Valid RegisterDto data) {
        if (this.organizacaoUsuarioRepository.findByEmail(data.email()).isPresent()) {
            return ResponseEntity.badRequest().build();
        }

        String encryptedPassword = passwordEncoder.encode(data.senha());
        OrganizacaoUsuario newUser = OrganizacaoUsuario.builder()
                .nome(data.nome())
                .email(data.email())
                .senha(encryptedPassword)
                .role(data.role())
                .organizacaoId(data.organizacaoId())
                .build();

        this.organizacaoUsuarioRepository.save(newUser);

        return ResponseEntity.ok().build();
    }
}
