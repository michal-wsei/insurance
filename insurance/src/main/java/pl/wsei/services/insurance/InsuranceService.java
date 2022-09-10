package pl.wsei.services.insurance;

import pl.wsei.model.Insurance;

import java.util.Collection;

public interface InsuranceService {
    Collection<Insurance> getAllInsurance();

    Insurance getInsuranceById(Integer id);

    Insurance addInsurance(Insurance insurance);

    void deleteInsurance(Integer id);

    Insurance updateInsurance(Insurance insurance);

    Insurance enrollClient(Integer insuranceId);
}
