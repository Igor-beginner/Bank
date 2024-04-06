package md.brainet.service.bank.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;

@Getter
@AllArgsConstructor
public enum Role implements GrantedAuthority {
    USER(0), ADMIN(1), OWNER(2);

    private final Integer priority;

    @Override
    public String getAuthority() {
        return "ROLE_" + name();
    }

    public static Role valueOf(GrantedAuthority authority) {
        return valueOf(authority.getAuthority().substring(5));
    }
}
