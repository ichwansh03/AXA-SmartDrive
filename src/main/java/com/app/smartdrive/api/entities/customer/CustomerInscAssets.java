package com.app.smartdrive.api.entities.customer;

import com.app.smartdrive.api.entities.master.CarSeries;
import com.app.smartdrive.api.entities.master.Cities;
import com.app.smartdrive.api.entities.master.InsuranceType;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Table(name = "customer_insc_assets", schema = "customer")
@Entity
public class CustomerInscAssets {
    @Id
    @Column(name = "cias_creq_entityid")
    private Long ciasCreqEntityid;

    @JsonBackReference
    @OneToOne
    @MapsId
    @JoinColumn(name = "cias_creq_entityid")
    private CustomerRequest customerRequest;

    @Column(name = "cias_police_number", length = 15, unique = true, nullable = false)
    private String ciasPoliceNumber;

    @Column(name = "cias_year", nullable = false, length = 4)
    private String ciasYear;

    @Column(name = "cias_startdate")
    private LocalDateTime ciasStartdate;

    @Column(name = "cias_enddate")
    private LocalDateTime ciasEnddate;

    @Column(name = "cias_current_price")
    private Double ciasCurrentPrice;

    @Column(name = "cias_insurance_price")
    private Double ciasInsurancePrice;

    @Column(name = "cias_total_premi")
    private Double ciasTotalPremi;

    @Enumerated(EnumType.STRING)
    @Column(name = "cias_paid_type", length = 15)
    private EnumCustomer.CreqPaidType ciasPaidType;

    @Column(name = "ciasIsnewchar", length = 1)
    private Character ciasIsNewChar;

    @JsonManagedReference
    @OneToMany(mappedBy = "customerInscAssets", cascade = CascadeType.ALL)
    private List<CustomerInscDoc> customerInscDoc;
    
    @JsonManagedReference
    @OneToMany(mappedBy = "customerInscAssets", cascade = CascadeType.ALL)
    private List<CustomerInscExtend> customerInscExtend;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "cias_cars_id")
    private CarSeries carSeries;


    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "cias_inty_name", referencedColumnName = "inty_name")
    private InsuranceType insuranceType;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "cias_city_id")
    private Cities city;






    





}
