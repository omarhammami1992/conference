package com.soat.back.conference.command.domain;

import com.soat.back.conference.event.ConferenceValidated;
import com.soat.back.conference.event.CreateConferenceRejected;

import java.time.LocalDate;
import java.util.Objects;

public final class Conference {
    private final String name;
    private final String link;
    private final LocalDate startDate;
    private final LocalDate endDate;

    private Conference(String name, String link, LocalDate startDate, LocalDate endDate) {
        this.name = name;
        this.link = link;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public static CreationResult create(String name, String link, LocalDate startDate, LocalDate endDate) {
        if(startDate.isAfter(endDate)) {
            return new CreationResult(null, new CreateConferenceRejected());
        }
        return new CreationResult(new Conference(name, link, startDate, endDate), new ConferenceValidated());
    }
    public String name() {
        return name;
    }

    public String link() {
        return link;
    }

    public LocalDate startDate() {
        return startDate;
    }

    public LocalDate endDate() {
        return endDate;
    }


    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (Conference) obj;
        return Objects.equals(this.name, that.name) &&
                Objects.equals(this.link, that.link) &&
                Objects.equals(this.startDate, that.startDate) &&
                Objects.equals(this.endDate, that.endDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, link, startDate, endDate);
    }

    @Override
    public String toString() {
        return "Conference[" +
                "name=" + name + ", " +
                "link=" + link + ", " +
                "startDate=" + startDate + ", " +
                "endDate=" + endDate + ']';
    }


}
