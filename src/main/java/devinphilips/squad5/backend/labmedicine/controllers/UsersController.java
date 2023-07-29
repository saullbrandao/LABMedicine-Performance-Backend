package devinphilips.squad5.backend.labmedicine.controllers;

import devinphilips.squad5.backend.labmedicine.dtos.user.UsersPostRequestDTO;
import devinphilips.squad5.backend.labmedicine.dtos.user.JwtAuthenticationResponse;
import devinphilips.squad5.backend.labmedicine.dtos.user.LoginPostRequestDTO;
import devinphilips.squad5.backend.labmedicine.services.AuthService;
import devinphilips.squad5.backend.labmedicine.services.UsersService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/usuarios")
public class UsersController {
    private final UsersService usersService;
    private final AuthService authService;

    public UsersController(UsersService usersService, AuthService authService) {
        this.usersService = usersService;
        this.authService = authService;
    }

//    @GetMapping
//    public ResponseEntity<?> getAll() {
//        // logic below only for initial setup testings purpose
//        return ResponseEntity.ok().body(repo.findAll());
//    }

    @PostMapping("/login")
    public ResponseEntity<JwtAuthenticationResponse> authenticate(@RequestBody @Valid LoginPostRequestDTO requestBody) {
        return ResponseEntity.ok(authService.login(requestBody));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody @Valid UsersPostRequestDTO requestBody){
        authService.register(requestBody);
    }
}
