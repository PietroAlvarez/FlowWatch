package dev.pietro.flowwatch.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "automations")
public class Automation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String area;
    private String owner;
    private String schedule;
    private String description;

    @Enumerated(EnumType.STRING)
    private AutomationStatus status;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    protected Automation() {
    }

    public Automation(String name, String area, String owner, String schedule, String description) {
        this.name = name;
        this.area = area;
        this.owner = owner;
        this.schedule = schedule;
        this.description = description;
        this.status = AutomationStatus.ACTIVE;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = this.createdAt;
    }

    public void toggle() {
        status = status == AutomationStatus.ACTIVE ? AutomationStatus.PAUSED : AutomationStatus.ACTIVE;
        updatedAt = LocalDateTime.now();
    }

    public void setMaintenance() {
        status = AutomationStatus.MAINTENANCE;
        updatedAt = LocalDateTime.now();
    }

    public Long getId() { return id; }
    public String getName() { return name; }
    public String getArea() { return area; }
    public String getOwner() { return owner; }
    public String getSchedule() { return schedule; }
    public String getDescription() { return description; }
    public AutomationStatus getStatus() { return status; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
}
