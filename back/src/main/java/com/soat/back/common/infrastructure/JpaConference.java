package com.soat.back.common.infrastructure;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
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

    @OneToMany(mappedBy = "conference", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<JpaPricingRange> pricingRanges;

    public JpaConference(Integer id, String name, String link, LocalDate startDate, LocalDate endDate) {
        this.id = id;
        this.name = name;
        this.link = link;
        this.startDate = startDate;
        this.endDate = endDate;
        this.pricingRanges = new ArrayList<>();
    }

    public JpaConference(String name, String link, LocalDate startDate, LocalDate endDate) {
        this.name = name;
        this.link = link;
        this.startDate = startDate;
        this.endDate = endDate;
        this.pricingRanges = new ArrayList<>();
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

    public List<JpaPricingRange> getPricingRanges() {
        return pricingRanges;
    }

    public void setPricingRanges(List<JpaPricingRange> pricingRanges) {
        this.pricingRanges = pricingRanges;
    }
}
