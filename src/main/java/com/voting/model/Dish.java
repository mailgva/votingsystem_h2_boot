package com.voting.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@NamedQueries({
        @NamedQuery(name = Dish.DELETE, query = "DELETE FROM Dish d WHERE d.id=:id"),
        @NamedQuery(name = Dish.GET_ALL, query = "SELECT d FROM Dish d ORDER BY d.name, d.price"),
        @NamedQuery(name = Dish.GET_BY_NAME, query = "SELECT d FROM Dish d WHERE UPPER(d.name) LIKE CONCAT('%',:partName,'%') ORDER BY d.name "),
})
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "dishes", uniqueConstraints = {@UniqueConstraint(columnNames = "name", name = "dishes_unique_name_idx")})
public class Dish extends AbstractNamedEntity {

    public static final String DELETE = "Dish.delete";
    public static final String GET_ALL = "Dish.getAll";
    public static final String GET_BY_NAME = "Dish.getByName";

    @Column(name = "price", nullable = false)
    @NotNull
    @Range(min = 10, max = 2500)
    private double price;

    @Transient
    private Resto resto;

    @Column(name = "img_file")
    private String imgFilePath;

    public Dish(Integer id, String name, double price) {
        super(id, name);
        this.price = price;
    }

    public Dish(Integer id, String name, double price, String imgFilePath) {
        super(id, name);
        this.price = price;
        this.imgFilePath = imgFilePath;
    }

    @Override
    public String toString() {
        return "Dish{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", imgFilePath=" + imgFilePath +
                '}';
    }
}
