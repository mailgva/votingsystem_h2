package com.voting.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@NamedQueries({
        @NamedQuery(name = Vote.DELETE, query = "DELETE FROM Vote v WHERE v.id=:id AND v.user.id=:userId"),
        @NamedQuery(name = Vote.ALL_SORTED, query = "SELECT v FROM Vote v " +
                "JOIN FETCH v.resto JOIN FETCH v.user " +
                "ORDER BY v.date DESC"),
        @NamedQuery(name = Vote.GET_BETWEEN, query = "SELECT v FROM Vote v " +
                "JOIN FETCH v.resto JOIN FETCH v.user " +
                "WHERE v.user.id=:userId AND v.date BETWEEN :startDate AND :endDate ORDER BY v.date DESC"),
        /*@NamedQuery(name = Vote.GET_USER_BY_DATE, query = "SELECT v FROM Vote v " +
                "JOIN FETCH v.resto JOIN FETCH v.user " +
                "WHERE v.user.id=:userId AND v.date=:date "),*/
        @NamedQuery(name = Vote.GET_USER_BY_DATE, query = "SELECT v FROM Vote v " +
                //"JOIN FETCH v.resto JOIN FETCH v.user " +
                "WHERE v.user.id=:userId AND v.date=:date "),
        @NamedQuery(name = Vote.GET_ALL_BY_DATE, query = "SELECT v FROM Vote v " +
                "JOIN FETCH v.resto JOIN FETCH v.user " +
                "WHERE v.date=:date ORDER BY v.resto.name, v.user.name "),
})
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "votes", uniqueConstraints = {@UniqueConstraint(columnNames = {"user_id", "rest_id", "date"}, name = "voting_unique_user_rest_date_idx")})
public class Vote extends AbstractBaseEntity {

    public static final String DELETE = "Vote.delete";
    public static final String ALL_SORTED = "Vote.getAll";
    public static final String GET_BETWEEN = "Vote.getBetween";
    public static final String GET_ALL_BY_DATE = "Vote.getAllByDate";
    public static final String GET_USER_BY_DATE = "Vote.getUserByDate";


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @NotNull
    private User user;

    @ManyToOne(fetch = FetchType.EAGER) //LAZY
    @JoinColumn(name = "rest_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @NotNull
    private Resto resto;

    @Column(name = "date", nullable = false)
    @JsonFormat(pattern="yyyy-MM-dd")
    @NotNull
    private Date date;

    @Column(name = "date_time", nullable = false)
    @NotNull
    private LocalDateTime dateTime;

    public Vote(Resto resto, Date date, LocalDateTime dateTime) {
        this.resto = resto;
        this.date = date;
        this.dateTime = dateTime;
    }

    public Vote(Vote v) {
        this(v.getUser(), v.getResto(), v.getDate(), v.getDateTime());
    }

    @Override
    public String toString() {
        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return "Vote{" +
                "id=" + id +
                ", \nuser=" + user +
                ", \nresto=" + resto +
                ", \ndate=" + fmt.format(date) +
                ", \ndateTime=" + dtf.format(dateTime) +
                '}';
    }

}
