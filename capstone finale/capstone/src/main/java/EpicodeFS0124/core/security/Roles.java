package EpicodeFS0124.core.security;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;


@Entity
@Table(name = "roles")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(setterPrefix = "with")
public class Roles {
    public static final String ROLES_ADMIN = "ADMIN";
    public static final String ROLES_USER = "USER";
    // i ruoli disponibili sono ADMIN E USER

    @Id
    private String roleType;


}