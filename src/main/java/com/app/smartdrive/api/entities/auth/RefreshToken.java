package com.app.smartdrive.api.entities.auth;

import com.app.smartdrive.api.entities.users.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "refresh_token", schema = "users")
public class RefreshToken {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "reto_id")
  private Long retoId;

  @OneToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "reto_user_id", referencedColumnName = "user_entityid")
  private User user;

  @Column(name = "reto_token", nullable = false)
  private String retoToken;

  @Column(name = "reto_expire_date", nullable = false)
  private Instant retoExpiryDate;


}
