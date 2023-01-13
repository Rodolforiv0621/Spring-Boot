package com.example.Tutorial.auth;

import com.example.Tutorial.config.JwtService;
import com.example.Tutorial.student.Student;
import com.example.Tutorial.student.StudentRepository;
import com.example.Tutorial.student.StudentRole;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {
    private final StudentRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationService(StudentRepository repository, PasswordEncoder passwordEncoder, JwtService jwtService,
            AuthenticationManager authenticationManager) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    public AuthenticationResponse register(RegisterRequest request) {
        var student = new Student(request.getUsername(), request.getEmail(), request.getAge(),
                passwordEncoder.encode(request.getPassword()),
                request.getPhone(), StudentRole.USER);
        repository.save(student);
        var jwtToken = jwtService.generateToken(student);
        return new AuthenticationResponse(jwtToken);

    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        // Student user =
        // repository.findStudentByEmail(request.getEmail()).orElseThrow();
        // if (user != null) {
        // String encodedPassword = user.getPassword();
        // if (passwordEncoder.matches(request.getPassword(), encodedPassword)) {
        // var student =
        // repository.findStudentByEmail(request.getEmail()).orElseThrow();
        // var jwtToken = jwtService.generateToken(student);
        // return new AuthenticationResponse(jwtToken);
        // }

        // }
        // throw new Exception("User cannot be authenticated");
        authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        // return "Passed authentucation manager :)";
        var user = repository.findStudentByUsername(request.getEmail()).orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        return new AuthenticationResponse(jwtToken);

    }

}
