package com.voting.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.*;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@NamedQueries({
        @NamedQuery(name = DailyMenu.DELETE, query = "DELETE FROM DailyMenu dm WHERE dm.id=:id"),
        @NamedQuery(name = DailyMenu.DELETE_BY_DATE, query = "DELETE FROM DailyMenu dm WHERE dm.date=:date"),
        @NamedQuery(name = DailyMenu.GET,
                query = "SELECT dm FROM DailyMenu dm " +
                        "JOIN FETCH dm.resto " +
                        "LEFT JOIN FETCH dm.dmDishes " +
                        "WHERE dm.id=:id"),
        @NamedQuery(name = DailyMenu.GET_ALL,
                query = "SELECT dm FROM DailyMenu dm " +
                        "JOIN FETCH dm.resto " +
                        "LEFT JOIN FETCH dm.dmDishes " +
                        "ORDER BY dm.date DESC, dm.resto.name "),
        /*@NamedQuery(name = DailyMenu.GET_BY_DATE,
                query = "SELECT distinct dm FROM DailyMenu dm " +
                        "JOIN FETCH dm.resto " +
                        "LEFT JOIN FETCH dm.dmDishes " +
                        "WHERE dm.date=:date ORDER BY dm.resto.name"),*/
        @NamedQuery(name = DailyMenu.GET_BY_NAME_RESTO,
                query = "SELECT dm FROM DailyMenu dm " +
                        "JOIN FETCH dm.resto " +
                        "LEFT JOIN FETCH dm.dmDishes " +
                        "WHERE UPPER(dm.resto.name) LIKE CONCAT('%',:name,'%') ORDER BY dm.date DESC, dm.resto.name ")
})
@NamedStoredProcedureQueries({
        @NamedStoredProcedureQuery(
                name = DailyMenu.GENERATE_STR_DAILY_MENU,
                procedureName = "generate_strdailymenu",
                parameters = {
                        @StoredProcedureParameter(
                                name = "fromdate",
                                type = String.class,
                                mode = ParameterMode.IN),
                        @StoredProcedureParameter(
                                name = "todate",
                                type = String.class,
                                mode = ParameterMode.IN),
                        }
        ),
        @NamedStoredProcedureQuery(
                name = DailyMenu.GENERATE_DAILY_MENU,
                procedureName = "generate_dailymenu",
                parameters = {
                        @StoredProcedureParameter(
                                name = "fromdate",
                                type = Date.class,
                                mode = ParameterMode.IN),
                        @StoredProcedureParameter(
                                name = "todate",
                                type = Date.class,
                                mode = ParameterMode.IN)
                }
        )
})
@NoArgsConstructor
@Data
@Entity
@Table(name = "daily_menu", uniqueConstraints = {@UniqueConstraint(columnNames = { "date", "rest_id"}, name = "dailymenu_unique_date_rest_idx")})
public class DailyMenu extends AbstractBaseEntity{
    public static final String DELETE =             "DailyMenu.delete"; //*
    public static final String DELETE_BY_DATE =     "DailyMenu.deleteByDate"; //*
    public static final String GET =                "DailyMenu.get"; //*
    public static final String GET_ALL =            "DailyMenu.getAll";
    //public static final String GET_BY_DATE =        "DailyMenu.getByDate";
    public static final String GET_BY_NAME_RESTO =  "DailyMenu.getByNameResto";
    public static final String GENERATE_DAILY_MENU =  "DailyMenu.generate_DailyMenu";
    public static final String GENERATE_STR_DAILY_MENU =  "DailyMenu.generate_StrDailyMenu";

    @Column(name = "date")
    @NotNull
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date date;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "rest_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @NotNull
    private Resto resto;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "dailyMenu", cascade = CascadeType.ALL)
    @Fetch(value = FetchMode.SUBSELECT)
    @NotNull
    private List<DailyMenuDish> dmDishes = new ArrayList<>();;

    public DailyMenu(DailyMenu dm) {
        this(dm.getId(), dm.getDate(), dm.getResto(), dm.getDmDishes());
    }

    public DailyMenu(@NotNull Date date, @NotNull Resto resto) {
        this.date = date;
        this.resto = resto;
    }

    public DailyMenu(Integer id, @NotNull Date date, @NotNull Resto resto) {
        super(id);
        this.date = date;
        this.resto = resto;
    }

    public DailyMenu(@NotNull Date date, @NotNull Resto resto, List<DailyMenuDish> dmDishes) {
        this.date = date;
        this.resto = resto;
        this.dmDishes = dmDishes;
        setDailyMenuIdToDailyMenuDishes();
    }

    public DailyMenu(Integer id, @NotNull Date date, @NotNull Resto resto, @NotNull List<DailyMenuDish> dmDishes) {
        super(id);
        this.date = date;
        this.resto = resto;
        this.dmDishes = dmDishes;
        setDailyMenuIdToDailyMenuDishes();
    }

    public void addDailyMenuDish(DailyMenuDish dmDish) {
        dmDish.setDailyMenu(this);
        this.dmDishes.add(dmDish);
    }

    public void removeDailyMenuDish(DailyMenuDish dmDish) {
        dmDish.setDailyMenu(null);
        this.dmDishes.remove(dmDish);
    }

    public List<Dish> getDishes(){
        return this.getDmDishes().stream()
                .map(dailyMenuDish -> dailyMenuDish.getDish())
                .collect(Collectors.toList());
    }

    private void setDailyMenuIdToDailyMenuDishes() {
        dmDishes.stream().forEach(dailyMenuDish -> dailyMenuDish.setDailyMenu(this));
    }

    @Override
    public String toString() {
        return "DailyMenu{" +
                "id=" + id +
                ", date=" + date +
                ", resto=" + resto +
                ",\n dmDishes=" + dmDishes +
                '}';
    }
}
