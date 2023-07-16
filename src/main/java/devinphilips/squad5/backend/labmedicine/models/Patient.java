package devinphilips.squad5.backend.labmedicine.models;

import devinphilips.squad5.backend.labmedicine.enums.Gender;
import devinphilips.squad5.backend.labmedicine.enums.MaritalStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@SequenceGenerator(sequenceName = "SQ_PATIENT", allocationSize = 1, name="my_seq_gen")
public class Patient extends BaseEntity {

    private String name;

    private String cpf;

    private String rg;

    @Column(name = "birth_date")
    private LocalDate birthDate;

    private Gender gender;

    @Column(name = "marital_status")
    private MaritalStatus maritalStatus;

    private String phone;

    private String email;

    private String naturality;

    @Column(name = "emergency_contact")
    private String emergencyContact;

    @Column(name = "allergy_list")
    private String allergyList;

    @Column(name = "specific_care_list")
    private String specificCareList;

    @Column(name = "health_insurance")
    private String healthInsurance;

    @Column(name = "insurance_number")
    private Integer insuranceNumber;

    @Column(name = "insurance_validity")
    private LocalDate insuranceValidity;

    private boolean status;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id" )
    private Address address;
}
