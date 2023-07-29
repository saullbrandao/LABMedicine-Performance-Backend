package devinphilips.squad5.backend.labmedicine.services;

import devinphilips.squad5.backend.labmedicine.mappers.UsersMapper;
import devinphilips.squad5.backend.labmedicine.repositories.UsersRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UsersService implements UsersServiceInterface {
    private final UsersRepository usersRepository;
    private final UsersMapper usersMapper;

    public UsersService(UsersRepository usersRepository, UsersMapper usersMapper) {
        this.usersRepository = usersRepository;
        this.usersMapper = usersMapper;
    }

    public UserDetailsService userDetailsService(){
        return new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                return usersRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));
            }
        };
    }
}
