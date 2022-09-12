package com.soat.back.common.infrastructure;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "Conference")
public class JpaConference {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;

    @Column
    private String link;

    @Column
    private LocalDate startDate;


    @Column
    private LocalDate endDate;

    public JpaConference(Integer id, String name, String link, LocalDate startDate, LocalDate endDate) {
        this.id = id;
        this.name = name;
        this.link = link;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public JpaConference(String name, String link, LocalDate startDate, LocalDate endDate) {
        this.name = name;
        this.link = link;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public JpaConference() {

    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getLink() {
        return link;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }
}
