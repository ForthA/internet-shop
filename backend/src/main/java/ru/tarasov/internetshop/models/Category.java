package ru.tarasov.internetshop.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Formula;
import org.hibernate.annotations.SQLSelect;

import java.util.List;

@Entity
@Table(name = "category")
@NoArgsConstructor
@Getter
@Setter
public class Category {
    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name="title")
    private String title;

    @JsonIgnore
    @OneToOne
    @JoinColumn(name = "father_id", referencedColumnName = "id")
    private Category fatherCategory;

    @OneToMany
    @JoinColumn(name = "father_id")
    private List<Category> subCategories;

    @OneToMany(mappedBy = "categoryId")
    private List<Product> products;


}
