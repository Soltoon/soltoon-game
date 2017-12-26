package ir.pint.soltoon.soltoongame.shared.result;

import ir.pint.soltoon.utils.shared.facades.result.DefaultEventLog;

public class Event extends DefaultEventLog {
    private EventType eventType;

    public Event(EventType eventType) {
        this.eventType = eventType;
    }

    public EventType getEventType() {
        return eventType;
    }

    public void setEventType(EventType eventType) {
        this.eventType = eventType;
    }
}
