package doc.backendapi.entities;

import doc.backendapi.Enum.Role;
import javax.persistence.*;
import javax.validation.constraints.Size;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@Entity
@Table(name = "users")
@AllArgsConstructor
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "UserID", nullable = false)
    private Integer id;

    @Size(max = 255)
    @Column(name = "Username")
    private String username;

    @Size(max = 255)
    @Column(name = "password")
    private String password;

    @Size(max = 255)
    @Column(name = "FullName")
    private String fullName;

    @Size(max = 255)
    @Column(name = "Role")
    private String role;

    @Size(max = 255)
    @Column(name = "Email")
    private String email;

    @Size(max = 255)
    @Column(name = "Phone")
    private String phone;

    @Size(max = 255)
    @Column(name = "Branch")
    private String branch;

}