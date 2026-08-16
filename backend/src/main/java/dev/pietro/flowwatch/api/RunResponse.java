package dev.pietro.flowwatch.api;

import dev.pietro.flowwatch.domain.AutomationRun;
import dev.pietro.flowwatch.domain.RunStatus;
import java.time.LocalDateTime;

public record RunResponse(Long id, Long automationId, String automationName, RunStatus status,
                          LocalDateTime startedAt, LocalDateTime finishedAt, int recordsProcessed, String message) {
    public static RunResponse from(AutomationRun run) {
        return new RunResponse(run.getId(), run.getAutomation().getId(), run.getAutomation().getName(),
                run.getStatus(), run.getStartedAt(), run.getFinishedAt(), run.getRecordsProcessed(), run.getMessage());
    }
}
