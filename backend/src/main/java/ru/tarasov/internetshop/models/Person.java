package ru.tarasov.internetshop.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Entity
@Table(name = "Person")
@Getter
@Setter
@NoArgsConstructor
public class Person {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotEmpty(message = "Имя не должно быть пустым")
    @Size(min=2, max = 100, message = "Имя должно быть от 2 до 100 символов")
    @Column(name = "name")
    private String name;

    @NotEmpty(message = "Поле не должно быть пустым")
    @Column(name = "surname")
    private String surname;

    @NotEmpty(message = "Поле не должно быть пустым")
    @Email(message = "Это не мейл ебанутый")
    @Column(name = "email")
    private String email;

    @NotEmpty(message = "Поле не должно быть пустым")
    @Column(name = "password")
    private String password;


    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "person_roles",
    joinColumns = {@JoinColumn(name = "person_id", referencedColumnName = "id")},
    inverseJoinColumns = {@JoinColumn(name = "role_id", referencedColumnName = "id")})
    @JsonIgnore
    private Set<Role> roles;

}
