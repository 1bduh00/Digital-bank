package com.example.authservice.service.impl;

import com.example.authservice.dto.JwtResponse;
import com.example.authservice.dto.SigninRequest;
import com.example.authservice.dto.SignupRequest;
import com.example.authservice.exception.AuthenticationFailedException;
import com.example.authservice.exception.EmailAlreadyExistsException;
import com.example.authservice.exception.InvalidEmailException;
import com.example.authservice.model.Client;
import com.example.authservice.model.Role;
import com.example.authservice.repository.ClientRepository;
import com.example.authservice.service.AuthService;
import com.example.authservice.service.JwtService;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {

    private JwtService jwtService;
    private ClientRepository clientRepository;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    @Override
    public JwtResponse Signin(SigninRequest request) throws InvalidEmailException, AuthenticationFailedException {

        try {
            Authentication authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(request.getC_email(), request.getC_password()));
            SecurityContextHolder.getContext().setAuthentication(authentication);

            var user = clientRepository.findClientByEmail(request.getC_email())
                    .orElseThrow(() -> new InvalidEmailException("Email doesn't exist : " + request.getC_email()));

            var jwt = jwtService.generateToken(user);
            JwtResponse jwtAuthResponse = new JwtResponse(jwt);
            jwtAuthResponse.setToken(jwt);

            return jwtAuthResponse;
        }catch (AuthenticationException e){
            throw new AuthenticationFailedException("Authentication failed : " + e.getMessage());
        }

    }

    @Override
    public JwtResponse Signup(SignupRequest request) throws EmailAlreadyExistsException {
        boolean exist = clientRepository.existsByEmail(request.getC_email());
        if(!exist){
            //After checking know we can Add new client
            Client client = new Client(
                    request.getC_firstname(),
                    request.getC_lastname(),
                    request.getC_email(),
                    passwordEncoder.encode(request.getC_password()),
                    request.getC_phone(),
                    Role.USER
            );

            clientRepository.save(client);
            var jwt = jwtService.generateToken(client);

            return new JwtResponse(jwt);

        }else {
            throw new EmailAlreadyExistsException("Email already exists in the database.", request.getC_email());
        }
    }
}
