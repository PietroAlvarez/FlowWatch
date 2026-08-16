package dev.pietro.flowwatch.api;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record AutomationRequest(
        @NotBlank @Size(max = 100) String name,
        @NotBlank @Size(max = 70) String area,
        @NotBlank @Size(max = 80) String owner,
        @NotBlank @Size(max = 80) String schedule,
        @Size(max = 400) String description
) {
}
