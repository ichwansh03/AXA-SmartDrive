package com.app.smartdrive.api.entities.customer;

import java.time.LocalDateTime;

import com.app.smartdrive.api.entities.master.CarSeries;
import com.app.smartdrive.api.entities.master.Cities;
import com.app.smartdrive.api.entities.master.InsuranceType;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
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

    
    @OneToOne(mappedBy = "customerInscAssets", cascade = CascadeType.ALL)
    private CustomerInscDoc customerInscDoc;
    
    @JsonManagedReference
    @OneToOne(mappedBy = "customerInscAssets", cascade = CascadeType.ALL)
    private CustomerInscExtend customerInscExtend;

    
    @ManyToOne
    @JoinColumn(name = "cias_cars_id")
    private CarSeries carSeries;


    
    @ManyToOne
    @JoinColumn(name = "cias_inty_name", referencedColumnName = "inty_name")
    private InsuranceType insuranceType;

    
    @ManyToOne
    @JoinColumn(name = "cias_city_id")
    private Cities city;






    





}
