package com.soat.back.conference.command.domain;

import java.time.LocalDate;

public class Conference {
    private final String name;
    private final String link;
    private final Float price;
    private final LocalDate startDate;
    private final LocalDate endDate;
    private final PriceRanges priceRanges;

    private final Address address;
    private final PriceGroup priceGroup;
    private final PriceAttendingDays priceAttendingDays;

    private Conference(String name,
                       String link,
                       Float price,
                       LocalDate startDate,
                       LocalDate endDate,
                       PriceRanges priceRanges,
                       Address address,
                       PriceGroup priceGroup,
                       PriceAttendingDays priceAttendingDays) {
        this.name = name;
        this.link = link;
        this.price = price;
        this.startDate = startDate;
        this.endDate = endDate;
        this.priceRanges = priceRanges;
        this.address = address;
        this.priceGroup = priceGroup;
        this.priceAttendingDays = priceAttendingDays;
    }

    public static ConferenceBuilder builder() {
        return new ConferenceBuilder();
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

    public PriceRanges getPriceRanges() {
        return priceRanges;
    }

    public PriceGroup getPriceGroup() {
        return priceGroup;
    }

    public PriceAttendingDays getPriceAttendingDays() {
        return priceAttendingDays;
    }

    public Address getAddress() {
        return address;
    }

    @Override
    public String toString() {
        return "Conference{" +
                "name='" + name + '\'' +
                ", link='" + link + '\'' +
                ", price=" + price +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", priceRanges=" + priceRanges +
                ", priceGroup=" + priceGroup +
                '}';
    }

    public static final class ConferenceBuilder {
        private String name;
        private String link;
        private Float price;
        private LocalDate startDate;
        private LocalDate endDate;
        private PriceRanges priceRanges = PriceRanges.createEmpty();
        private Address address;
        private PriceGroup priceGroup = PriceGroup.createEmpty();
        private PriceAttendingDays priceAttendingDays = PriceAttendingDays.createEmpty();

        private ConferenceBuilder() {
        }

        public ConferenceBuilder name(String name) {
            this.name = name;
            return this;
        }

        public ConferenceBuilder link(String link) {
            this.link = link;
            return this;
        }

        public ConferenceBuilder price(Float price) {
            this.price = price;
            return this;
        }

        public ConferenceBuilder startDate(LocalDate startDate) {
            this.startDate = startDate;
            return this;
        }

        public ConferenceBuilder endDate(LocalDate endDate) {
            this.endDate = endDate;
            return this;
        }

        public ConferenceBuilder priceRanges(PriceRanges priceRanges) throws InvalidIntervalException, InvalidPricesException {
            priceRanges.checkIntervals(price);
            this.priceRanges = priceRanges;
            return this;
        }

        public ConferenceBuilder address(Address address) {
            this.address = address;
            return this;
        }

        public ConferenceBuilder priceGroup(PriceGroup priceGroup, Float defaultPrice) throws InvalidPricesException {
            priceGroup.checkPriceGroupAmount(defaultPrice);
            this.priceGroup = priceGroup;
            return this;
        }

        public ConferenceBuilder priceAttendingDays(PriceAttendingDays priceAttendingDays) throws InvalidAttendingDaysException {
            var interval = new DateInterval(startDate, endDate);
            priceAttendingDays.checkAreAllBelow(interval.period());
            this.priceAttendingDays = priceAttendingDays;
            return this;
        }

        public Conference build() {
            return new Conference(name, link, price, startDate, endDate, priceRanges, address, priceGroup, priceAttendingDays);
        }
    }
}
