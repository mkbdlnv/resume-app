package kabdulinovmedet.pet.kz.resume.services;

import kabdulinovmedet.pet.kz.resume.dto.ResumeDTO;
import kabdulinovmedet.pet.kz.resume.models.Resume;
import kabdulinovmedet.pet.kz.resume.models.User;
import kabdulinovmedet.pet.kz.resume.repositories.ResumeRepository;
import kabdulinovmedet.pet.kz.resume.repositories.UserRepository;
import kabdulinovmedet.pet.kz.resume.utils.UserNotCreatedException;
import kabdulinovmedet.pet.kz.resume.utils.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.lang.module.ResolutionException;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class ResumeService {
    private final ResumeRepository resumeRepository;
    private final UserRepository userRepository;
    @Autowired
    public ResumeService(ResumeRepository resumeRepository, UserRepository userRepository){
        this.resumeRepository = resumeRepository;
        this.userRepository = userRepository;
    }

    public List<Resume> findAll(){
        return resumeRepository.findAll();
    }

    public Resume findById(Long id){
        return resumeRepository.findById(id).get();
    }

    @Transactional
    public Optional<Resume> save(ResumeDTO resumeDTO, BindingResult bindingResult){
        Optional<User> user= userRepository.findById(resumeDTO.getUserId());
        if(bindingResult.hasErrors()){
            StringBuilder errorMsg = new StringBuilder();
            List<FieldError> errors = bindingResult.getFieldErrors();
            for(FieldError error:errors){
                errorMsg.append(error.getField())
                        .append(" - ").append(error.getDefaultMessage()).append(";");
            }
            throw new ResolutionException(errorMsg.toString());
        } else if (user.isEmpty()) {
            throw new UserNotFoundException();
        }
        Resume resume = new Resume();
        resume.setResumeText(resumeDTO.getResumeText());
        resume.setOwner(user.get());
        resume.setJob(resumeDTO.getJob());
        resumeRepository.save(resume);
        return Optional.of(resume);
    }
}
