package com.example.Onboarding.controller;

import com.example.Onboarding.DTO.LoginDTO;
import com.example.Onboarding.DTO.SignUpDto;
import com.example.Onboarding.entity.Usuario;
import com.example.Onboarding.repository.RoleRepository;
import com.example.Onboarding.repository.UsuarioRepositoryDao;
import com.example.Onboarding.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private int intentos = 0;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UsuarioRepositoryDao userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/")
    public List<Usuario> listaUsuarios(){
        return (List<Usuario>) userRepository.findAll();
    }

    @PostMapping("/signin")
    public ResponseEntity<String> authenticateUser(@RequestBody LoginDTO loginDto) {

               if (userRepository.existsByUsername(loginDto.getUsernameOrEmail()) || userRepository.existsByEmail(loginDto.getUsernameOrEmail())) {
                   Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                           loginDto.getUsernameOrEmail(), loginDto.getPassword()));
                   System.out.println("authentication = " + authentication.isAuthenticated());
                   if (authentication.isAuthenticated()) {
                       SecurityContextHolder.getContext().setAuthentication(authentication);
                       return new ResponseEntity<>("Bienvenido", HttpStatus.OK);
                   }
               } else if (intentos < 3){
                   intentos++;
                   return new ResponseEntity<>("Error de usuario o contraseña", HttpStatus.UNAUTHORIZED);
               }else
                   intentos = 0;
        return new ResponseEntity<>("Por seguridad su usuario ha sido bloqueado", HttpStatus.FORBIDDEN);
    }

    @GetMapping("/unblock-user/{username}/{telefono}")
    public ResponseEntity<String> desbloquearUsuario(@PathVariable("username") String username, @PathVariable("telefono") String telefono){
        if(userRepository.existsByUsername(username)){
            if(usuarioService.existsByTelefono(telefono)){
                return new ResponseEntity<>("Usuario Desbloqueado ", HttpStatus.ACCEPTED);
            }
            return new ResponseEntity<>("El telefono no coincide ", HttpStatus.ACCEPTED);
        }
        return new ResponseEntity<>("Error de Usuario", HttpStatus.ACCEPTED);
    }

    @PutMapping("/change-password/{username}/{newPassword}")
    public ResponseEntity<?> cambioContraseña(@PathVariable ("username") String username, @PathVariable ("newPassword") String newPassword){
        if(userRepository.existsByUsername(username)){
           Usuario usuario = userRepository.findByUsername(username).get();
            usuario.setPassword(passwordEncoder.encode(newPassword));
            userRepository.save(usuario);

            return new ResponseEntity<>("Contraseña cambiada exitosamente", HttpStatus.OK);
        }
        return new ResponseEntity<>("El usuario no existe", HttpStatus.BAD_REQUEST);
    }
    @PutMapping("/change-email/{username}/{email}")
    public ResponseEntity<?> cambioEmail(@PathVariable ("username") String username, @PathVariable ("email") String newEmail){
        if(userRepository.existsByUsername(username)){
            Usuario usuario = userRepository.findByUsername(username).get();
            usuario.setEmail(newEmail);
            userRepository.save(usuario);

            return new ResponseEntity<>("Email cambiado exitosamente", HttpStatus.OK);
        }
        return new ResponseEntity<>("El usuario no existe", HttpStatus.BAD_REQUEST);
    }

    @PutMapping("/change-telefono/{username}/{telefono}")
    public ResponseEntity<?> cambioTelefono(@PathVariable ("username") String username, @PathVariable ("telefono") String newTelefono){
        if(userRepository.existsByUsername(username)){
            Usuario usuario = userRepository.findByUsername(username).get();
            usuario.setTelefono(newTelefono);
            userRepository.save(usuario);

            return new ResponseEntity<>("Telefono cambiado exitosamente", HttpStatus.OK);
        }
        return new ResponseEntity<>("El usuario no existe", HttpStatus.BAD_REQUEST);
    }


    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody SignUpDto signUpDto){

        // add check for username exists in a DB
        if(userRepository.existsByUsername(signUpDto.getUsername())){
            return new ResponseEntity<>("Este nombre de usuario ya esta registrado", HttpStatus.BAD_REQUEST);
        }

        //add check for edad<18
        if( signUpDto.getEdad() < 18 ){
            return new ResponseEntity<>("Debe ser mayor a 17 años", HttpStatus.BAD_REQUEST);
        }

        //add check for edad<18
        if( signUpDto.getTelefono().length()!=10 ){
            return new ResponseEntity<>("El telefono debe tener 10 digitos", HttpStatus.BAD_REQUEST);
        }

        if( !signUpDto.getPassword().matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{6,20}$") ){
            return new ResponseEntity<>("La Contraseña debe contener minimo 6 elementos, un numero, una Mayuscula y una letra", HttpStatus.BAD_REQUEST);
        }


        // add check for email exists in DB
        if(userRepository.existsByEmail(signUpDto.getEmail())){
            return new ResponseEntity<>("El email ya existe", HttpStatus.BAD_REQUEST);
        }

        // create user object
        Usuario user = new Usuario();
        user.setNombre(signUpDto.getNombre());
        user.setApellidoPat(signUpDto.getApellidoPat());
        user.setApellidoMat(signUpDto.getApellidoMat());
        user.setEdad(signUpDto.getEdad());
        user.setEmail(signUpDto.getEmail());
        user.setTelefono(signUpDto.getTelefono());
        user.setUsername(signUpDto.getUsername());
        user.setPassword(passwordEncoder.encode(signUpDto.getPassword()));

        //Role roles = roleRepository.findByName("ROLE_ADMIN").get();
        //user.setRoles(Collections.singleton(roles));

        userRepository.save(user);

        return new ResponseEntity<>("Usuario registrado exitosamente", HttpStatus.OK);

    }
}
