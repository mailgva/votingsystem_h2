package com.voting.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "daily_menu_detail", uniqueConstraints = {@UniqueConstraint(columnNames = { "daily_menu_id", "dish_id"}, name = "dailymenudetail_unique_dailymenu_dish_idx")})
public class DailyMenuDish extends AbstractBaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "daily_menu_id", nullable=false)
    @NotNull
    private DailyMenu dailyMenu;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "dish_id", nullable=false)
    private Dish dish;

    public DailyMenuDish(Dish dish) {
        this.dish = dish;
    }

    /*public DailyMenuDish(Integer id) {
        super(id);
    }



    public DailyMenuDish(@NotNull DailyMenu dailyMenu, Dish dish) {
        this.dailyMenu = dailyMenu;
        this.dish = dish;
    }

    public DailyMenuDish(Integer id, @NotNull DailyMenu dailyMenu, Dish dish) {
        super(id);
        this.dailyMenu = dailyMenu;
        this.dish = dish;
    }*/

    @Override
    public String toString() {
        return "DailyMenuDish{" +
                //"dailyMenu=" + dailyMenu +
                ", dish=" + dish +
                ", id=" + id +
                '}';
    }
}
