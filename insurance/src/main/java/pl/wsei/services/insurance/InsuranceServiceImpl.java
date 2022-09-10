package pl.wsei.services.insurance;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.wsei.controller.exceptions.insurance.InsuranceException;
import pl.wsei.model.Insurance;
import pl.wsei.model.Enrollment;
import pl.wsei.model.authorization.User;
import pl.wsei.repository.InsuranceRepository;
import pl.wsei.repository.EnrollmentRepository;
import pl.wsei.repository.UserRepository;
import pl.wsei.services.security.SecurityService;


import javax.transaction.Transactional;
import java.util.Collection;

@Service
public class InsuranceServiceImpl implements InsuranceService {

    private final InsuranceRepository insuranceRepository;
    private final UserRepository userRepository;
    private final SecurityService securityService;
    private final EnrollmentRepository enrollmentRepository;

    @Autowired
    public InsuranceServiceImpl(InsuranceRepository insuranceRepository, UserRepository userRepository, SecurityService securityService, EnrollmentRepository enrollmentRepository) {
        this.insuranceRepository = insuranceRepository;
        this.userRepository = userRepository;
        this.securityService = securityService;
        this.enrollmentRepository = enrollmentRepository;
    }

    public Collection<Insurance> getAllInsurance() {
        return this.insuranceRepository.findAll();
    }

    @Override
    public Insurance getInsuranceById(Integer id) {
        return this.insuranceRepository.findById(id).orElseThrow(() -> new InsuranceException("insurance not found!"));
    }

    @Override
    @Transactional
    public Insurance addInsurance(Insurance insurance) throws InsuranceException {
        int ownerId = securityService.getCurrentUser()
                .orElseThrow(() -> new InsuranceException("Trying to get current user returned Optional.empty()."))
                .getId();

        User owner = userRepository.findById(ownerId)
                .orElseThrow(() -> new InsuranceException(String.format("Failed to find user with id=%d.", ownerId)));

        insurance.setOwner(owner);
        return insuranceRepository.save(insurance);
    }

    @Override
    @Transactional
    public void deleteInsurance(Integer id) {
        if (this.insuranceRepository.existsById(id)) {
            this.enrollmentRepository.deleteAllByInsuranceId(id);
            this.insuranceRepository.deleteById(id);
        } else {
            throw new InsuranceException(("insurance not found!"));
        }
    }

    @Override
    @Transactional
    public Insurance updateInsurance(Insurance insurance) {
        User owner = insuranceRepository
                .findById(insurance.getId())
                .orElseThrow(() -> new InsuranceException("insurance cannot be not found."))
                .getOwner();

        insurance.setOwner(owner);
        return insuranceRepository.save(insurance);
    }

    @Override
    @Transactional
    public Insurance enrollClient(Integer insuranceId) {
        if (!this.insuranceRepository.existsById(insuranceId)) {
            throw new InsuranceException("insurance not found!");
        }

        Insurance insurance = insuranceRepository.findById(insuranceId).orElseThrow(() -> new InsuranceException("insurance not found!"));

        int userId = securityService.getCurrentUser()
                .orElseThrow(() -> new InsuranceException("Trying to get current user returned Optional.empty()."))
                .getId();

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new InsuranceException(String.format("Failed to find user with id=%d.", userId)));

        if (insurance.getOwner().getId() == userId) {
            throw new InsuranceException("You are the owner of the insurance!" +
                    " You cannot enlist to your own insurance! Who would teach it? :P");
        }

        if (enrollmentRepository.findByInsuranceIdInAndUserId(insuranceId, userId) != null) {
            throw new InsuranceException(String.format("User already enrolled in insurance with id=%d.", userId));
        }

        this.enrollmentRepository.save(new Enrollment(insurance, user));

        return insurance;
    }
}
