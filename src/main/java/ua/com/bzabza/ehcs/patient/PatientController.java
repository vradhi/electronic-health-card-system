package ua.com.bzabza.ehcs.patient;

import org.jboss.aerogear.security.otp.api.Base32;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/patients")
public class PatientController {

    private final PatientService patientService;

    @Autowired
    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    @GetMapping
    public List<Patient> getAll() {
        PatientSpecification patientSpecification = new PatientSpecification();
        return patientService.getBy(patientSpecification);
    }

    @PostMapping
    public void register(@RequestBody @Valid PatientRegisterForm patientRegisterForm) {
        Patient registeredPatient = patientRegisterForm.toPatient();
        registeredPatient.setSecret(Base32.random());
        patientService.save(registeredPatient);
    }
}
