package NonSqlProject.model;

import NonSqlProject.model.Enum.EventType;

import java.time.LocalDateTime;

public class Record {

    private int id;
    private LocalDateTime dateTime;
    private Employee employee;
    private EventType eventType;

    public Record(LocalDateTime dateTime, Employee employee, EventType eventType) {
        this.dateTime = dateTime;
        this.employee = employee;
        this.eventType = eventType;
    }

    public Record(int id, LocalDateTime dateTime, Employee employee, EventType eventType) {
        this.id = id;
        this.dateTime = dateTime;
        this.employee = employee;
        this.eventType = eventType;
    }

    public Record() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public Employee getUserName() {
        return employee;
    }

    public void setUserName(Employee employee) {
        this.employee = employee;
    }

    public EventType getEventType() {
        return eventType;
    }

    public void setEventType(EventType eventType) {
        this.eventType = eventType;
    }

    @Override
    public String toString() {
        return "id:" + id +
                "\n Date:" + dateTime +
                "\n Employee:" + employee +
                "\n Type:" + eventType;
    }
}
