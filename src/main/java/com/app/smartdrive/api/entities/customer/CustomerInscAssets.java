package com.app.smartdrive.api.entities.customer;

import java.time.LocalDateTime;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "customer_insc_assets", schema = "customer")
@Entity
public class CustomerInscAssets {
    @Id
    @Column(name = "cias_creq_entityid")
    private Long ciasCreqEntityid;

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

    @Column(name = "cias_paid_type", length = 15)
    private String ciasPaidType;

    @Column(name = "cias_isNewChar", length = 1)
    private Character ciasIsNewChar;

    // mtr.car_series fk :cias_cars_id
    // mtr.insurance_type fk :cias_inty_name varchar(25)
    // mtr.cities  fk:ias_city_id

    @OneToOne(mappedBy = "customerInscAssets", cascade = CascadeType.ALL)
    private CustomerInscDoc customerInscDoc;
    
    @OneToOne(mappedBy = "customerInscAssets", cascade = CascadeType.ALL)
    private CustomerInscExtend customerInscExtend;





    





}
