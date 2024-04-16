package id.ac.ui.cs.advprog.bookubeauth.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Entity
@Table(name = "users")
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(nullable = false)
  private Integer id;

  @Column(unique = true)
  @Setter
  private String email;

  @Setter
  private String username;

  @Setter
  private String password;
}
