package md.brainet.service.bank.entity;

import jakarta.persistence.*;
import jakarta.validation.Constraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Account {

    //TODO make uuid

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String title;

    @ManyToOne
    private User owner;

    @Column(nullable = false, columnDefinition = "INTEGER DEFAULT 0")
    private Integer balance;

    @Enumerated(EnumType.STRING)
    private AccountType accountType;

    @Column(nullable = false, columnDefinition = "BOOLEAN DEFAULT true")
    private Boolean enabled;
}
