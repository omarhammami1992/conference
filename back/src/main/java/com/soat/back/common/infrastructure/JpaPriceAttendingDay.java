package com.soat.back.common.infrastructure;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "price_attending_day")
public class JpaPriceAttendingDay {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Integer id;
   @Column
   private Float price;
   @Column
   private Float attendingDays;

   @ManyToOne
   @JoinColumn(name = "conference_id")
   private JpaConference conference;

   public JpaPriceAttendingDay(Float price, Float attendingDays) {
      this.price = price;
      this.attendingDays = attendingDays;
   }

   public JpaPriceAttendingDay() {

   }

   public Integer getId() {
      return id;
   }

   public Float getPrice() {
      return price;
   }

   public Float getAttendingDays() {
      return attendingDays;
   }

   public JpaConference getConference() {
      return conference;
   }
}
