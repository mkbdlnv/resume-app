package kabdulinovmedet.pet.kz.resume.dto;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Size;
import kabdulinovmedet.pet.kz.resume.models.Job;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResumeDTO {
    @Size(min=100, max = 1000, message = "Length of resume must be between 100 and 1000 characters.")
    private String resumeText;
    private Long userId;
    @Enumerated(EnumType.STRING)
    private Job job;
}
