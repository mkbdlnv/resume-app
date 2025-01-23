package kabdulinovmedet.pet.kz.resume.dto;

import jakarta.validation.constraints.Pattern;
import kabdulinovmedet.pet.kz.resume.models.Resume;

import java.util.List;

public class UserDTO {
    private String name;
    private String lastname;
    private String email;
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#!$%^&-+=()])(?=\\S+$).{8,20}$",
            message = "Password must contain:\n" +
                    "1. At least one digit\n" +
                    "2. At least one lowercase letter\n" +
                    "3.At least one uppercase letter\n" +
                    "4.At least one special character (@#!$%^&-+=())\n" +
                    "5.No whitespaces\n" +
                    "6.Length between 8 and 20")
    private String password;
    private List<Resume> resumes;
}
