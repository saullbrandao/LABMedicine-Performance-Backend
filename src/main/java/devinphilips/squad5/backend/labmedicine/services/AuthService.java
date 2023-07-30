package devinphilips.squad5.backend.labmedicine.services;

import devinphilips.squad5.backend.labmedicine.dtos.user.JwtAuthenticationResponse;
import devinphilips.squad5.backend.labmedicine.dtos.user.LoginPostRequestDTO;
import devinphilips.squad5.backend.labmedicine.dtos.user.ResetPasswordPutRequestDTO;
import devinphilips.squad5.backend.labmedicine.dtos.user.UsersPostRequestDTO;
import devinphilips.squad5.backend.labmedicine.mappers.UsersMapper;
import devinphilips.squad5.backend.labmedicine.repositories.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UsersRepository usersRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final UsersMapper usersMapper;
    private final PasswordEncoder passwordEncoder;
    private final LogService logService;

    public JwtAuthenticationResponse login(LoginPostRequestDTO request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.email(), request.password()));
        var user = usersRepository.findByEmail(request.email())
                .orElseThrow(() -> new IllegalArgumentException("Email ou senha inválido"));
        var jwt = jwtService.generateToken(user, user.getName());
        return new JwtAuthenticationResponse(jwt);
    }

    public void register(UsersPostRequestDTO dto, String userEmail) {
        var newUser = usersMapper.map(dto);
        newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));

        var user = usersRepository.findByEmail(userEmail).orElse(null);
        if(user == null) return;

        usersRepository.save(newUser);

        logService.registerPeopleCreate(usersMapper.map(user), "usuário", newUser.getName(), newUser.getCpf());
    }

    public void resetPassword(ResetPasswordPutRequestDTO request) {
        var user = usersRepository.findByEmail(request.email())
                .orElseThrow(() -> new IllegalArgumentException("Email ou senha inválido"));

        if (passwordEncoder.matches(request.currentPassword(), user.getPassword())) {
            String newPassword = encodePassword(request.newPassword());
            user.setPassword(newPassword);
            usersRepository.save(user);
        } else {
            throw new IllegalArgumentException("Email ou senha inválido");
        }
    }

    private String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }
}
