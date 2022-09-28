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
    private JpaConference conference;

    public JpaPriceGroup() {

    }
    public JpaPriceGroup(float groupPrice, int threshold) {
        price = groupPrice;
        this.threshold = threshold;
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
