package pl.wsei.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.wsei.annotations.IsInsuranceOwner;
import pl.wsei.annotations.IsClient;
import pl.wsei.annotations.IsAgent;
import pl.wsei.dto.insurance.InsuranceConverter;
import pl.wsei.dto.insurance.InsuranceDto;
import pl.wsei.dto.variant.ReorderOperationDto;
import pl.wsei.dto.variant.VariantConverter;
import pl.wsei.dto.variant.VariantDto;
import pl.wsei.model.Insurance;
import pl.wsei.services.insurance.InsuranceServiceImpl;
import pl.wsei.services.variant.VariantServiceImpl;

import javax.validation.Valid;
import java.util.Collection;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/insurance")
public class InsuranceController {

    private final InsuranceServiceImpl insuranceServiceImpl;
    private final VariantServiceImpl variantServiceImpl;

    private final InsuranceConverter insuranceConverter;
    private final VariantConverter variantConverter;

    @Autowired
    public InsuranceController(InsuranceServiceImpl insuranceServiceImpl, InsuranceConverter insuranceConverter, VariantConverter variantConverter, VariantServiceImpl variantServiceImpl) {
        this.insuranceServiceImpl = insuranceServiceImpl;
        this.insuranceConverter = insuranceConverter;
        this.variantConverter = variantConverter;
        this.variantServiceImpl = variantServiceImpl;
    }

    @GetMapping("/all")
    public ResponseEntity<Collection<InsuranceDto>> getAllinsurance() {
        return ResponseEntity.ok(this.insuranceServiceImpl.getAllInsurance().stream()
                .map(this.insuranceConverter::createFrom).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<InsuranceDto> getInsuranceById(@PathVariable Integer id) {
        return ResponseEntity.ok(this.insuranceConverter.createFrom(this.insuranceServiceImpl.getInsuranceById(id)));
    }

    @IsAgent
    @PostMapping
    public ResponseEntity<InsuranceDto> addInsurance(@RequestBody InsuranceDto insurance) {
        return ResponseEntity.ok(
                this.insuranceConverter.createFrom(this.insuranceServiceImpl.addInsurance(this.insuranceConverter.createFrom(insurance)))
        );
    }

    @IsInsuranceOwner
    @DeleteMapping("/{id}")
    public void deleteCourse(@PathVariable Integer id) {
        this.insuranceServiceImpl.deleteInsurance(id);
    }

    @IsInsuranceOwner
    @PutMapping("/{id}")
    public ResponseEntity<InsuranceDto> updateInsurance(@PathVariable Integer id, @Valid @RequestBody InsuranceDto InsuranceDto) {
        Insurance insurance = this.insuranceConverter.createFrom(InsuranceDto);
        insurance.setId(id);
        return ResponseEntity.ok(this.insuranceConverter.createFrom(this.insuranceServiceImpl.updateInsurance(insurance)));
    }

    @IsClient
    @PostMapping("{id}/enroll")
    public ResponseEntity<InsuranceDto> enroll(@PathVariable Integer id) {
        return ResponseEntity.ok(this.insuranceConverter.createFrom(this.insuranceServiceImpl.enrollClient(id)));
    }

    @IsInsuranceOwner
    @PostMapping("/{id}/variant")
    public ResponseEntity<VariantDto> createVariant(@PathVariable Integer id, @RequestBody VariantDto variantDto) {
        return ResponseEntity.ok(
                this.variantConverter.createFrom(
                        this.variantServiceImpl.createVariant(
                                id, this.variantConverter.createFrom(variantDto)))
        );
    }

    @IsInsuranceOwner
    @PostMapping("/{id}/variant/{variantId}")
    public ResponseEntity<VariantDto> reOrderVariant(
            @PathVariable Integer id,
            @PathVariable Integer variantId,
            @RequestBody ReorderOperationDto reorderOperation
    ) {
        int orderIndex = reorderOperation.getOrder();
        return ResponseEntity.ok(
                this.variantConverter.createFrom(
                        this.variantServiceImpl.reOrderVariant(id, variantId, orderIndex))
        );
    }

}
