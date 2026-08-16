package dev.pietro.flowwatch.service;

import dev.pietro.flowwatch.api.AutomationRequest;
import dev.pietro.flowwatch.api.AutomationResponse;
import dev.pietro.flowwatch.api.DashboardResponse;
import dev.pietro.flowwatch.api.RunResponse;
import dev.pietro.flowwatch.domain.Automation;
import dev.pietro.flowwatch.domain.AutomationRun;
import dev.pietro.flowwatch.domain.AutomationStatus;
import dev.pietro.flowwatch.domain.RunStatus;
import dev.pietro.flowwatch.repository.AutomationRepository;
import dev.pietro.flowwatch.repository.AutomationRunRepository;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AutomationService {

    private final AutomationRepository automationRepository;
    private final AutomationRunRepository runRepository;

    public AutomationService(AutomationRepository automationRepository, AutomationRunRepository runRepository) {
        this.automationRepository = automationRepository;
        this.runRepository = runRepository;
    }

    @Transactional(readOnly = true)
    public List<AutomationResponse> listAutomations() {
        return automationRepository.findAllByOrderByNameAsc().stream().map(AutomationResponse::from).toList();
    }

    @Transactional(readOnly = true)
    public List<RunResponse> listRuns() {
        return runRepository.findTop20ByOrderByStartedAtDesc().stream().map(RunResponse::from).toList();
    }

    @Transactional(readOnly = true)
    public DashboardResponse dashboard() {
        List<Automation> automations = automationRepository.findAll();
        List<AutomationRun> runs = runRepository.findAll();
        long completed = runs.stream().filter(run -> run.getStatus() != RunStatus.RUNNING).count();
        long success = runs.stream().filter(run -> run.getStatus() == RunStatus.SUCCESS).count();
        double rate = completed == 0 ? 0 : Math.round((success * 1000.0 / completed)) / 10.0;
        return new DashboardResponse(automations.size(),
                automations.stream().filter(item -> item.getStatus() == AutomationStatus.ACTIVE).count(),
                runs.size(), rate, runs.stream().filter(run -> run.getStatus() == RunStatus.FAILED).count());
    }

    public AutomationResponse create(AutomationRequest request) {
        Automation automation = new Automation(request.name(), request.area(), request.owner(),
                request.schedule(), request.description());
        return AutomationResponse.from(automationRepository.save(automation));
    }

    public AutomationResponse toggle(long id) {
        Automation automation = findAutomation(id);
        automation.toggle();
        return AutomationResponse.from(automation);
    }

    public RunResponse simulate(long id, RunStatus outcome) {
        Automation automation = findAutomation(id);
        if (automation.getStatus() != AutomationStatus.ACTIVE) {
            throw new IllegalStateException("La automatización debe estar activa");
        }
        RunStatus safeOutcome = outcome == RunStatus.FAILED ? RunStatus.FAILED : RunStatus.SUCCESS;
        int processed = safeOutcome == RunStatus.SUCCESS ? 120 + Math.toIntExact(id * 17) : 28;
        String message = safeOutcome == RunStatus.SUCCESS ? "Ejecución completada sin incidencias" : "Validación de datos fallida";
        return RunResponse.from(runRepository.save(new AutomationRun(automation, safeOutcome, processed, message)));
    }

    private Automation findAutomation(long id) {
        return automationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Automatización no encontrada"));
    }
}
