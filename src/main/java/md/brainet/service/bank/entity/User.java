package md.brainet.service.bank.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 45, unique = true)
    private String username;

    @Column(length = 64, unique = true)
    private String password;

    @OneToMany(fetch = FetchType.LAZY,
            mappedBy = "owner",
            cascade = CascadeType.ALL)
    private List<Account> accounts;

    @Column(columnDefinition = "BOOLEAN DEFAULT true")
    private Boolean enabled;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "VARCHAR(255) DEFAULT USER")
    private Role role;
}
