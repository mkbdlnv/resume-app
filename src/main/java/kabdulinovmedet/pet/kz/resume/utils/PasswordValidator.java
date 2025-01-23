package kabdulinovmedet.pet.kz.resume.utils;

import java.util.regex.Pattern;

public class PasswordValidator {
    public static boolean isValid(String password){
        return (Pattern.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#!$%^&-+=()])(?=\\S+$).{8,20}$", password));  
    }
}
