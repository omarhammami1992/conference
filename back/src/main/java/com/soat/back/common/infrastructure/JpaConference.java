package com.soat.back.common.infrastructure;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

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
    private Float price;
    @Column
    private LocalDate startDate;

    @Column
    private LocalDate endDate;

    @OneToMany(mappedBy = "conference", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<JpaPriceRange> priceRanges;

    @OneToMany(mappedBy = "conference", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<JpaPriceAttendingDay> priceAttendingDays;

    @OneToOne(mappedBy = "conference", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private JpaPriceGroup priceGroup;

    public JpaConference(Integer id, String name, String link, Float price,  LocalDate startDate, LocalDate endDate) {
        this.id = id;
        this.name = name;
        this.link = link;
        this.price = price;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public JpaConference(String name, String link, Float price, LocalDate startDate, LocalDate endDate) {
        this.name = name;
        this.link = link;
        this.price = price;
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

    public Float getPrice() {
        return price;
    }

    public JpaPriceGroup getPriceGroup() {
        return priceGroup;
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

    public JpaPriceGroup getGroupPrice() {
        return this.priceGroup;
    }

    public void setPriceGroup(JpaPriceGroup priceGroup) {
        this.priceGroup = priceGroup;
    }

    public List<JpaPriceAttendingDay> getPriceAttendingDays() {
        return priceAttendingDays;
    }

    public void setPriceAttendingDays(List<JpaPriceAttendingDay> priceAttendingDays) {
        this.priceAttendingDays = priceAttendingDays;
    }
}
