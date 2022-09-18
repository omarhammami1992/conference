package com.soat.back.common.infrastructure;

import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import com.soat.back.conference.command.domain.Conference;

@Entity
@Table(name = "pricing_range")
public class JpaPricingRange {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Integer id;

   @Column
   private LocalDate startDate;

   @Column
   private LocalDate endDate;

   @Column
   private Double price;

   @ManyToOne(fetch = FetchType.LAZY)
   @JoinColumn(name = "conference_id")
   private JpaConference conference;

   public JpaPricingRange(LocalDate startDate, LocalDate endDate, Double price, JpaConference conference) {
      this.startDate = startDate;
      this.endDate = endDate;
      this.price = price;
      this.conference = conference;
   }

   public JpaPricingRange() {

   }

   public Integer getId() {
      return id;
   }

   public LocalDate getStartDate() {
      return startDate;
   }

   public LocalDate getEndDate() {
      return endDate;
   }

   public Double getPrice() {
      return price;
   }

   public JpaConference getConference() {
      return conference;
   }
}
