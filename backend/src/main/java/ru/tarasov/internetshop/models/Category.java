package ru.tarasov.internetshop.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Formula;
import org.hibernate.annotations.SQLSelect;
import org.springframework.context.annotation.Primary;

import java.util.List;

@Entity
@Table(name = "category",
        indexes = {@Index(name = "first_index", columnList = "father_id", unique = false)})
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

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(unique = false, name = "father_id")
    private List<Category> subCategories;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(unique = false, name = "father_id", referencedColumnName = "id")
    private Category fatherCategory;

    @OneToMany(mappedBy = "categoryId")
    private List<Product> products;

}
