package dev.pietro.flowwatch.controller;

import dev.pietro.flowwatch.api.AutomationRequest;
import dev.pietro.flowwatch.api.AutomationResponse;
import dev.pietro.flowwatch.api.RunResponse;
import dev.pietro.flowwatch.domain.RunStatus;
import dev.pietro.flowwatch.service.AutomationService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/automations")
public class AutomationController {

    private final AutomationService service;

    public AutomationController(AutomationService service) { this.service = service; }

    @GetMapping
    public List<AutomationResponse> list() { return service.listAutomations(); }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AutomationResponse create(@Valid @RequestBody AutomationRequest request) { return service.create(request); }

    @PatchMapping("/{id}/toggle")
    public AutomationResponse toggle(@PathVariable long id) { return service.toggle(id); }

    @PostMapping("/{id}/run")
    @ResponseStatus(HttpStatus.CREATED)
    public RunResponse simulate(@PathVariable long id, @RequestParam(defaultValue = "SUCCESS") RunStatus outcome) {
        return service.simulate(id, outcome);
    }
}
