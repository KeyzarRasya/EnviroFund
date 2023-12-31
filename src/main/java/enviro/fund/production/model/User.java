package enviro.fund.production.model;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Entity
@Data
public class User implements UserDetails {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String username;
    private String email;
    private String password;
    private BigDecimal money;
    private int point;
    private boolean isRenting;
    private String role;
    @ManyToMany(mappedBy = "donors")
    private List<Charity> history;
    @OneToOne
    private Vehicle vehicle;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        String prefixRole = "ROLE_" + this.getRole();
        return Collections.singleton(new SimpleGrantedAuthority(prefixRole));
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
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
