package NonSqlProject.model;

import NonSqlProject.model.Enum.EventType;

import java.time.LocalDateTime;

public class Record {

    private int id;
    private LocalDateTime dateTime;
    private String userName;
    private EventType eventType;

    public Record(LocalDateTime dateTime, String userName, EventType eventType) {
        this.dateTime = dateTime;
        this.userName = userName;
        this.eventType = eventType;
    }

    public Record() {
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public EventType getEventType() {
        return eventType;
    }

    public void setEventType(EventType eventType) {
        this.eventType = eventType;
    }

}
