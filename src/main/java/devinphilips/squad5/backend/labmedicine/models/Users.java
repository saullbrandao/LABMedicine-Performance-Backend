package devinphilips.squad5.backend.labmedicine.models;

import devinphilips.squad5.backend.labmedicine.enums.Gender;
import devinphilips.squad5.backend.labmedicine.enums.UserType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

@Entity
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@SequenceGenerator(sequenceName = "SQ_USERS", allocationSize = 1, name="my_seq_gen")
public class Users extends BaseEntity implements UserDetails {
    @Enumerated(EnumType.STRING)
    private UserType type;

    private String name;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    private String cpf;

    private String phone;

    private String email;

    private String password;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(type.name()));
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
