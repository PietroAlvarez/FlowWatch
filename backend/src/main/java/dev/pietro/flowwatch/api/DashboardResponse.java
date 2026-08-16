package dev.pietro.flowwatch.api;

public record DashboardResponse(long automations, long active, long executions, double successRate, long failed) {
}
