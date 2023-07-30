package devinphilips.squad5.backend.labmedicine.controllers;

import devinphilips.squad5.backend.labmedicine.dtos.user.*;
import devinphilips.squad5.backend.labmedicine.services.AuthService;
import devinphilips.squad5.backend.labmedicine.services.UsersService;
import jakarta.validation.Valid;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class UsersController {
    private final UsersService usersService;
    private final AuthService authService;

    public UsersController(UsersService usersService, AuthService authService) {
        this.usersService = usersService;
        this.authService = authService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<UserResponseDTO> getAll() {
        return usersService.getAll();
    }

    @PostMapping("/login")
    public ResponseEntity<JwtAuthenticationResponse> authenticate(@RequestBody @Valid LoginPostRequestDTO requestBody) {
        return ResponseEntity.ok(authService.login(requestBody));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody @Valid UsersPostRequestDTO requestBody) {
        authService.register(requestBody);
    }

    @PutMapping("/resetarsenha")
    @ResponseStatus(HttpStatus.OK)
    public void resetPassword(@Valid @RequestBody ResetPasswordPutRequestDTO resetPasswordPutRequestDTO) {
        authService.resetPassword(resetPasswordPutRequestDTO);
    }

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public void updateUser(@Valid @RequestBody UsersPutRequestDTO usersPutRequestDTO, @PathVariable Integer id) {
        usersService.update(id, usersPutRequestDTO);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(code = HttpStatus.ACCEPTED)
    public ResponseEntity<?> remove(@PathVariable int id, @RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
        usersService.delete(id, token);
        return ResponseEntity.accepted().build();
    }
}
