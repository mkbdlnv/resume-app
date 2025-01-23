package kabdulinovmedet.pet.kz.resume.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Resume {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(min=100, max = 1000, message = "Length of resume must be between 100 and 1000 characters.")
    private String resumeText;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name="user_id", referencedColumnName = "id")
    private User owner;

    @Enumerated(EnumType.STRING)
    private Job job;
}
