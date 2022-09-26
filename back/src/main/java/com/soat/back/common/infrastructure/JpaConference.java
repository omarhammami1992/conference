package com.soat.back.common.infrastructure;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "conference")
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

    @OneToMany(mappedBy = "conference", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<JpaPriceRange> priceRanges;

    public JpaConference(Integer id, String name, String link, LocalDate startDate, LocalDate endDate) {
        this.id = id;
        this.name = name;
        this.link = link;
        this.startDate = startDate;
        this.endDate = endDate;
    }


    public JpaConference() {

    }

    public JpaConference(String name, String link, LocalDate startDate, LocalDate endDate) {
        this.name = name;
        this.link = link;
        this.startDate = startDate;
        this.endDate = endDate;
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

    public List<JpaPriceRange> getPriceRanges() {
        return priceRanges;
    }

    public void setPriceRanges(List<JpaPriceRange> priceRanges) {
        this.priceRanges = priceRanges;
    }
}
