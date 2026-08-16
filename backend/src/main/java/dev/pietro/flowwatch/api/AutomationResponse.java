package dev.pietro.flowwatch.api;

import dev.pietro.flowwatch.domain.Automation;
import dev.pietro.flowwatch.domain.AutomationStatus;
import java.time.LocalDateTime;

public record AutomationResponse(Long id, String name, String area, String owner, String schedule,
                                 String description, AutomationStatus status, LocalDateTime updatedAt) {
    public static AutomationResponse from(Automation automation) {
        return new AutomationResponse(automation.getId(), automation.getName(), automation.getArea(),
                automation.getOwner(), automation.getSchedule(), automation.getDescription(),
                automation.getStatus(), automation.getUpdatedAt());
    }
}
