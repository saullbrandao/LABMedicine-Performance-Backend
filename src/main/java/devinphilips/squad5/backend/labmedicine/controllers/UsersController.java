package devinphilips.squad5.backend.labmedicine.controllers;

import devinphilips.squad5.backend.labmedicine.dtos.UsersPostRequest;
import devinphilips.squad5.backend.labmedicine.enums.Gender;
import devinphilips.squad5.backend.labmedicine.enums.UserType;
import devinphilips.squad5.backend.labmedicine.models.Users;
import devinphilips.squad5.backend.labmedicine.repositories.UsersRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/usuarios")
public class UsersController {
    private final UsersRepository repo;

    public UsersController(UsersRepository repo) {
        this.repo = repo;
    }

    @GetMapping
    public ResponseEntity<?> getAll() {
        // logic below only for initial setup testings purpose
        return ResponseEntity.ok().body(repo.findAll());
    }

    @PostMapping("/cadastrar")
    public ResponseEntity<?> create(@RequestBody UsersPostRequest requestBody){
        // logic below only for initial setup testings purpose
        try {
            var now = LocalDateTime.now();

            var user = new Users();
            user.setType(UserType.valueOf(requestBody.getType()));
            user.setName(requestBody.getName());
            user.setCpf(requestBody.getCpf());
            user.setEmail(requestBody.getEmail());
            user.setPassword(new BCryptPasswordEncoder().encode(requestBody.getPassword()));
            user.setPhone(requestBody.getPhone());
            user.setGender(Gender.valueOf(requestBody.getGender()));
            user.setCreatedAt(now);
            user.setUpdatedAt(now);

            repo.save(user);
            return ResponseEntity.created(URI.create("")).body(user);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.internalServerError().body("Deu ruim...");
        }
    }
}
