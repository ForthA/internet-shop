package ru.tarasov.internetshop.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "cart")
@NoArgsConstructor
@Getter
@Setter
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "product_id")
    @JsonIgnore
    private int product_id;

    @Column(name = "amount")
    private int amount;

    @OneToMany
    @JoinColumn(name = "id", referencedColumnName = "product_id")
    private List<Product> product;
}
