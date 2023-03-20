package com.soat.back.common.infrastructure;

import java.time.LocalDate;
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
@Table(name = "price_range")
public class JpaPriceRange {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Integer id;
   @Column
   private Float price;
   @Column
   private LocalDate startDate;
   @Column
   private LocalDate endDate;

   @ManyToOne
   @JoinColumn(name = "conference_id")
   private JpaConference conference;

   public JpaPriceRange() {

   }
   public JpaPriceRange(Float price, LocalDate startDate, LocalDate endDate, JpaConference conference) {
      this.price = price;
      this.startDate = startDate;
      this.endDate = endDate;
      this.conference = conference;
   }

   public JpaPriceRange(Float price, LocalDate startDate, LocalDate endDate) {
      this.price = price;
      this.startDate = startDate;
      this.endDate = endDate;
   }

   public Integer getId() {
      return id;
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

   public JpaConference getConference() {
      return conference;
   }

}
