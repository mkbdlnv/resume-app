package kabdulinovmedet.pet.kz.resume.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.sql.Timestamp;

@AllArgsConstructor
@Getter
public class UserErrorResponse{
    private String message;
    private long timestamp;
}
