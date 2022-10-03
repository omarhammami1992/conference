package com.soat.back.common.infrastructure;

import javax.persistence.*;

@Entity
@Table(name = "price_group")
public class JpaPriceGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private Float price;

    @Column
    private Integer threshold;

    @OneToOne
    @JoinColumn(name = "conference_id")
    private JpaConference conference;

    public JpaPriceGroup() {

    }
    public JpaPriceGroup(float groupPrice, int threshold, JpaConference conference) {
        this.price = groupPrice;
        this.threshold = threshold;
        this.conference = conference;
    }

    public JpaPriceGroup(float groupPrice, int threshold) {
        this.threshold = threshold;
        this.price = groupPrice;
    }

    public Integer getId() {
        return id;
    }

    public Float getPrice() {
        return price;
    }

    public Integer getThreshold() {
        return threshold;
    }
}
