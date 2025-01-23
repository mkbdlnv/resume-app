package kabdulinovmedet.pet.kz.resume.controllers;

import jakarta.validation.Valid;
import kabdulinovmedet.pet.kz.resume.dto.ResumeDTO;
import kabdulinovmedet.pet.kz.resume.models.Resume;
import kabdulinovmedet.pet.kz.resume.services.ResumeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/resumes")
public class ResumeController {
    private ResumeService resumeService;

    @Autowired
    public ResumeController(ResumeService resumeService){
        this.resumeService = resumeService;
    }

    @GetMapping()
    public List<Resume> getResumes(){
        return resumeService.findAll();
    }

    @GetMapping("/{id}")
    public Resume getResume(@PathVariable Long id){
        return resumeService.findById(id);
    }

    @PostMapping
    public ResponseEntity<HttpStatus> create(@RequestBody @Valid ResumeDTO resumeDTO, BindingResult bindingResult){
        System.out.println(resumeDTO.getJob());
        return resumeService.save(resumeDTO, bindingResult).isPresent()?ResponseEntity.ok().build():
                ResponseEntity.badRequest().build();
    }




}
