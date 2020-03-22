package NonSqlProject.model;

import NonSqlProject.model.Enum.Type;

import java.time.LocalDateTime;

public class Incidence {

    private int id;
    private LocalDateTime dateTime;
    private Employee origin;
    private Employee destination;
    private String details;
    private Type type;

    public Incidence(LocalDateTime dateTime, Employee origin, Employee destination, String details, Type type) {
        this.dateTime = dateTime;
        this.origin = origin;
        this.destination = destination;
        this.details = details;
        this.type = type;
    }

    public Incidence(int id, LocalDateTime dateTime, Employee origin, Employee destination, String details, Type type) {
        this.id = id;
        this.dateTime = dateTime;
        this.origin = origin;
        this.destination = destination;
        this.details = details;
        this.type = type;
    }

    public Incidence() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public Employee getOrigin() {
        return origin;
    }

    public void setOrigin(Employee origin) {
        this.origin = origin;
    }

    public Employee getDestination() {
        return destination;
    }

    public void setDestination(Employee destination) {
        this.destination = destination;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Id:" + id +
                "\n Date:" + dateTime +
                "\n Origin:" + origin +
                "\n Recipient:" + destination +
                "\n Details:'" + details +
                "\n Type:" + type;
    }
}
