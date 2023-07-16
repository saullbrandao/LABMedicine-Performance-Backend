package devinphilips.squad5.backend.labmedicine.models;

import devinphilips.squad5.backend.labmedicine.enums.Gender;
import devinphilips.squad5.backend.labmedicine.enums.MaritalStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_PATIENT")
    @SequenceGenerator(
            name = "SQ_PATIENT",
            sequenceName = "SQ_PATIENT",
            allocationSize = 1
    )
    private int id;

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

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
