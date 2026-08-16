package dev.pietro.flowwatch.config;

import dev.pietro.flowwatch.domain.Automation;
import dev.pietro.flowwatch.domain.AutomationRun;
import dev.pietro.flowwatch.domain.RunStatus;
import dev.pietro.flowwatch.repository.AutomationRepository;
import dev.pietro.flowwatch.repository.AutomationRunRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DemoDataConfig {
    @Bean
    CommandLineRunner seed(AutomationRepository automations, AutomationRunRepository runs) {
        return args -> {
            Automation invoices = automations.save(new Automation("Carga de facturas", "Finanzas", "Camila Soto", "Lun–Vie · 08:00", "Extrae y valida facturas recibidas."));
            Automation accounts = automations.save(new Automation("Alta de cuentas", "Personas", "Martín Rojas", "Cada 30 minutos", "Crea accesos para nuevas incorporaciones."));
            Automation reports = new Automation("Reporte operativo", "Operaciones", "Valentina Pérez", "Diario · 18:30", "Consolida indicadores y genera un informe." );
            reports.toggle();
            automations.save(reports);
            runs.save(new AutomationRun(invoices, RunStatus.SUCCESS, 248, "Ejecución completada sin incidencias"));
            runs.save(new AutomationRun(accounts, RunStatus.SUCCESS, 36, "Cuentas creadas y notificadas"));
            runs.save(new AutomationRun(invoices, RunStatus.FAILED, 41, "Dos facturas con formato inválido"));
            runs.save(new AutomationRun(accounts, RunStatus.SUCCESS, 18, "Ejecución completada sin incidencias"));
            runs.save(new AutomationRun(reports, RunStatus.SUCCESS, 12, "Informe generado correctamente"));
        };
    }
}
