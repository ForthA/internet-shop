package ru.tarasov.internetshop.dto;

import jakarta.persistence.Column;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class PersonDTO {

    private String name;

    private String surname;

    private String email;

    private String password;
}
