package com.soat.back.common.infrastructure;

import com.soat.back.conference.query.domain.Address;
import com.soat.back.conference.query.domain.Conference;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

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
    @Column
    private String city;
    @Column
    private String country;
    @Column
    private String fullAddress;
    @Column
    private String latitude;
    @Column
    private String longitude;

    @OneToMany(mappedBy = "conference", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<JpaPriceRange> priceRanges;

    @OneToMany(mappedBy = "conference", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<JpaPriceAttendingDay> priceAttendingDays;

    @OneToOne(mappedBy = "conference", cascade = CascadeType.ALL)
    private JpaPriceGroup priceGroup;

    public JpaConference(Integer id, String name, String link, Float price, LocalDate startDate, LocalDate endDate, String city, String country, String fullAddress, String latitude, String longitude) {
        this.id = id;
        this.name = name;
        this.link = link;
        this.price = price;
        this.startDate = startDate;
        this.endDate = endDate;
        this.city = city;
        this.country = country;
        this.fullAddress = fullAddress;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public JpaConference(String name, String link, Float price, LocalDate startDate, LocalDate endDate, String city, String country,
                         String fullAddress, String latitude, String longitude) {
        this.name = name;
        this.link = link;
        this.price = price;
        this.startDate = startDate;
        this.endDate = endDate;
        this.city = city;
        this.country = country;
        this.fullAddress = fullAddress;
        this.latitude = latitude;
        this.longitude = longitude;
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

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    @Override
    public Boolean getIsOnline() {
        return price.equals(0f);
    }

    @Override
    public Address getAddress() {
        return new Address(this.fullAddress, this.city, this.country, this.latitude, this.longitude);
    }


    public JpaPriceGroup getPriceGroup() {
        return priceGroup;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        JpaConference that = (JpaConference) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(link, that.link) && Objects.equals(price, that.price) && Objects.equals(startDate, that.startDate) && Objects.equals(endDate, that.endDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, link, price, startDate, endDate);
    }
}
