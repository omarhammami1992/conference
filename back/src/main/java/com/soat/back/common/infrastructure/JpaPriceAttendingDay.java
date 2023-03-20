package com.soat.back.common.infrastructure;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

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

   public JpaPriceAttendingDay(Float price, Float attendingDays, JpaConference conference) {
      this.price = price;
      this.attendingDays = attendingDays;
      this.conference = conference;
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
