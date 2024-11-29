package mx.edu.uaz.is.poo2.carger.view.windows.consult;

import java.util.List;

import mx.edu.uaz.is.poo2.carger.model.entities.events.Event;

public class EventWindow extends ConsultWindow<Event> {

    private List<Event> events;

    public EventWindow() {
        super("Event");
    }

    @Override
    public void start() {
        // Se debe pedir el ID del evento a consultar pues no es realista mostrarlos todos de una vez.
        this.println("NOT IMPLEMENTED");
    }

    @Override
    public void start(Event event) {
        this.println("NOT IMPLEMENTED");
    }

    public List<Event> getEvents() {  // Nomás está para evitar la advertencia de no usar el atributo events
        return this.events;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
    }
}