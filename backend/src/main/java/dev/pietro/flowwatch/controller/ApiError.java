package dev.pietro.flowwatch.controller;

import java.time.Instant;
public record ApiError(Instant timestamp, int status, String message) {
}
