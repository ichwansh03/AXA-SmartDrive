package com.app.smartdrive.api.entities.master;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "inbox_messaging", schema = "mtr")
public class InboxMessaging {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ibme_id", updatable = false, nullable = false)
    private Long ibmeId;

    @Column(name = "ibme_date")
    private LocalDate ibmeDate;

    @Column(name = "ibme_entityid_source")
    private int ibme_entityid_source;

    @Column(name = "ibme_entityid_target")
    private int ibme_entityid_target;

    @Column(name = "ibme_type")
    private int ibme_type;

    @Column(name = "ibme_count")
    private int ibme_count;
}
