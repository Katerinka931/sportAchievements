package com.coursework.sportachievements.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "sportsman", schema = "public")
@Getter
@Setter
public class Sportsman {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "passport", columnDefinition = "text", length = 50, nullable = false, unique = true)
    private String passport;
    private String firstName;
    private String lastName;
    private String middleName;
    @Temporal(TemporalType.DATE)
    private Date birthdate;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.REFRESH)
    @JoinColumn(name = "sport_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Sport sport;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.REFRESH)
    @JoinColumn(name = "team_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Team team;

    @OneToMany(mappedBy = "sportsman", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Contact> contacts;

    @OneToMany(mappedBy = "sportsman", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Achievement> achievements;
}
