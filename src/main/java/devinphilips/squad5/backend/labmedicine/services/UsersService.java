package devinphilips.squad5.backend.labmedicine.services;

import devinphilips.squad5.backend.labmedicine.dtos.user.UserResponseDTO;
import devinphilips.squad5.backend.labmedicine.mappers.UsersMapper;
import devinphilips.squad5.backend.labmedicine.models.Users;
import devinphilips.squad5.backend.labmedicine.repositories.UsersRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsersService implements UsersServiceInterface {
    private final UsersRepository usersRepository;
    private final UsersMapper usersMapper;
    private final JwtService jwtService;

    public UsersService(UsersRepository usersRepository, UsersMapper usersMapper, JwtService jwtService) {
        this.usersRepository = usersRepository;
        this.usersMapper = usersMapper;
        this.jwtService = jwtService;
    }

    public List<UserResponseDTO> getAll() {
        return usersMapper.map(usersRepository.findAll());
    }

    public void delete(Integer id, String token) {
        Users user = usersRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));
        String trimmedToken = token.replace("Bearer ", "").trim();
        String tokenEmail = jwtService.extractUserName(trimmedToken);

        if (user.getEmail().equals(tokenEmail)) {
            throw new IllegalArgumentException("O usuário não pode deletar a si próprio.");
        }

        usersRepository.delete(user);
    }

    public UserDetailsService userDetailsService() {
        return new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                return usersRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));
            }
        };
    }
}
