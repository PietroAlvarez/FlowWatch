package dev.pietro.flowwatch.controller;

import dev.pietro.flowwatch.api.DashboardResponse;
import dev.pietro.flowwatch.api.RunResponse;
import dev.pietro.flowwatch.service.AutomationService;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class DashboardController {
    private final AutomationService service;
    public DashboardController(AutomationService service) { this.service = service; }

    @GetMapping("/dashboard")
    public DashboardResponse dashboard() { return service.dashboard(); }

    @GetMapping("/runs")
    public List<RunResponse> runs() { return service.listRuns(); }
}
