package kabdulinovmedet.pet.kz.resume.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="users")
public class    User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "Name cannot be empty.")
    private String name;
    @NotBlank(message = "Lastname cannot be empty.")
    private String lastname;
    @Email(message = "Incorrect email.")
    private String email;
    private String password;
    @JsonManagedReference
    @OneToMany(mappedBy = "owner")
    private List<Resume> resumes;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition="varchar default 'ROLE_USER'")
    private Role role;

}
