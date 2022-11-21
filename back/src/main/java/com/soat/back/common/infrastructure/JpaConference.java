package com.soat.back.common.infrastructure;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
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

    @OneToMany(mappedBy = "conference", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<JpaPriceRange> priceRanges = new ArrayList<>();

    @OneToMany(mappedBy = "conference", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<JpaPriceAttendingDay> priceAttendingDays = new ArrayList<>();

    @OneToOne(mappedBy = "conference", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @LazyCollection(LazyCollectionOption.FALSE)
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
