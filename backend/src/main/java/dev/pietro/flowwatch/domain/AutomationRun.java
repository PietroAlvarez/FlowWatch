package dev.pietro.flowwatch.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "automation_runs")
public class AutomationRun {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "automation_id")
    private Automation automation;

    @Enumerated(EnumType.STRING)
    private RunStatus status;

    private LocalDateTime startedAt;
    private LocalDateTime finishedAt;
    private int recordsProcessed;
    private String message;

    protected AutomationRun() {
    }

    public AutomationRun(Automation automation, RunStatus status, int recordsProcessed, String message) {
        this.automation = automation;
        this.status = status;
        this.recordsProcessed = recordsProcessed;
        this.message = message;
        this.startedAt = LocalDateTime.now();
        this.finishedAt = status == RunStatus.RUNNING ? null : this.startedAt.plusSeconds(18);
    }

    public Long getId() { return id; }
    public Automation getAutomation() { return automation; }
    public RunStatus getStatus() { return status; }
    public LocalDateTime getStartedAt() { return startedAt; }
    public LocalDateTime getFinishedAt() { return finishedAt; }
    public int getRecordsProcessed() { return recordsProcessed; }
    public String getMessage() { return message; }
}
