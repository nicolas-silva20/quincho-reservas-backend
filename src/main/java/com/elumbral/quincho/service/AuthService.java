package com.elumbral.quincho.service;

import com.elumbral.quincho.exception.ReservaException;
import com.elumbral.quincho.model.dto.LoginRequestDTO;
import com.elumbral.quincho.model.dto.LoginResponseDTO;
import com.elumbral.quincho.model.entity.Usuario;
import com.elumbral.quincho.repository.UsuarioRepository;
import com.elumbral.quincho.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {

    private final UsuarioRepository usuarioRepository;
    private final JwtUtil jwtUtil;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public LoginResponseDTO login(LoginRequestDTO request) {
        log.info("Intento de login para usuario: {}", request.getUsername());

        Usuario usuario = usuarioRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new ReservaException("Usuario o contraseÃ±a incorrectos"));

        if (!usuario.getActivo()) {
            throw new ReservaException("Usuario desactivado");
        }

        // Validar contraseÃ±a con BCrypt
        if (!passwordEncoder.matches(request.getPassword(), usuario.getPassword())) {
            throw new ReservaException("Usuario o contraseÃ±a incorrectos");
        }

        // Generar token JWT
        String token = jwtUtil.generateToken(usuario.getUsername());

        log.info("Login exitoso para usuario: {}", request.getUsername());

        return LoginResponseDTO.builder()
                .token(token)
                .username(usuario.getUsername())
                .rol(usuario.getRol())
                .mensaje("Login exitoso")
                .build();
    }

    public boolean validarToken(String token) {
        try {
            String username = jwtUtil.extractUsername(token);
            return jwtUtil.validateToken(token, username);
        } catch (Exception e) {
            return false;
        }
    }
    
    // MÃ©todo helper para hashear contraseÃ±as (usar al crear usuarios)
    public String hashPassword(String plainPassword) {
        return passwordEncoder.encode(plainPassword);
    }
}