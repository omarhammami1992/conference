package com.soat.back.common.infrastructure;

import com.soat.back.conference.query.domain.Conference;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "conference")
public class JpaConference implements Conference {
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

    public JpaConference(Integer id, String name, String link, Float price, LocalDate startDate, LocalDate endDate) {
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

    public JpaConference(int i, String name, LocalDate startDate, LocalDate endDate, Float price) {
        this.id = i;
        this.name = name;
        this.price = price;
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

    @Override
    public Integer id() {
        return id;
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public LocalDate startDate() {
        return startDate;
    }

    @Override
    public LocalDate endDate() {
        return endDate;
    }

    @Override
    public float fullPrice() {
        return getPrice().floatValue();
    }

    @Override
    public Boolean isOnline() {
        return fullPrice() == 0;
    }

    @Override
    public String city() {
        return "Paris";
    }

    @Override
    public String country() {
        return "France";
    }

    @Override
    public String toString() {
        return "JpaConference{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", link='" + link + '\'' +
                ", price=" + price +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                '}';
    }
}
