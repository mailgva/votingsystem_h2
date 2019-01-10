package com.voting.model;

import com.fasterxml.jackson.annotation.JsonInclude;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@NamedQueries({
        @NamedQuery(name = Resto.GET_BY_NAME, query = "SELECT r FROM Resto r WHERE UPPER(r.name) = UPPER(:name)"),
        @NamedQuery(name = Resto.GET_ALL, query = "SELECT r FROM Resto r ORDER BY r.name")
})
@Entity
@Table(name = "restaurants", uniqueConstraints = {@UniqueConstraint(columnNames = "id", name = "restaurants_unique_name_idx")})
public class Resto extends AbstractNamedEntity {
    public static final String GET_BY_NAME = "Resto.getByName";
    public static final String GET_ALL = "Resto.getAll";

    @Column(name = "address")
    private String address;

    @Transient
    private List<Dish> dishes;

    public Resto() {
    }

    public Resto(Resto r) {
        this(r.getId(), r.getName(), r.getAddress());
    }

    public Resto(Integer id, String name) {
        super(id, name);
    }

    public Resto(Integer id, String name, String address) {
        super(id, name);
        this.address = address;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public List<Dish> getDishes() {
        return dishes;
    }

    public void setDishes(List<Dish> dishes) {
        this.dishes = dishes;
    }

    @Override
    public String toString() {
        return "Resto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", dishes=\n'" + getDishes() + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Resto resto = (Resto) o;
        return Objects.equals(address, resto.address) &&
                Objects.equals(dishes, resto.dishes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), address, dishes);
    }
}
