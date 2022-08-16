package com.soat.back.cqrs;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CommandResponse<E extends Event> {

    List<E> events;

    public CommandResponse(E event) {
        this.events = new ArrayList<>();
        events.add(event);
    }

    // Todo (je voudrais que le type de retour soir le type passer en paramètre)
    // .map(clazz::cast) ne suffit pas
    public Optional<? extends Event> findFirst(Class<? extends Event> clazz) {
        return events.stream()
                .filter(e -> e.getClass().equals(clazz))
                .findFirst();
    }

    public List<E> events() {
        return events;
    }
}
