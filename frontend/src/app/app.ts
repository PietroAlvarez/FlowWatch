import { CommonModule } from '@angular/common';
import { Component, OnInit, signal } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { forkJoin } from 'rxjs';
import { ApiService } from './api.service';
import { Automation, AutomationPayload, AutomationRun, AutomationStatus, Dashboard, RunStatus } from './models';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './app.html',
  styleUrl: './app.scss'
})
export class App implements OnInit {
  readonly dashboard = signal<Dashboard>({ automations: 0, active: 0, executions: 0, successRate: 0, failed: 0 });
  readonly automations = signal<Automation[]>([]);
  readonly runs = signal<AutomationRun[]>([]);
  readonly loading = signal(true);
  readonly message = signal('');
  readonly saving = signal(false);

  form: AutomationPayload = this.emptyForm();

  constructor(private readonly api: ApiService) {}

  ngOnInit(): void { this.refresh(); }

  refresh(): void {
    this.loading.set(true);
    forkJoin({ dashboard: this.api.getDashboard(), automations: this.api.getAutomations(), runs: this.api.getRuns() })
      .subscribe({
        next: data => {
          this.dashboard.set(data.dashboard);
          this.automations.set(data.automations);
          this.runs.set(data.runs);
          this.loading.set(false);
        },
        error: () => {
          this.message.set('No se pudo conectar con la API de FlowWatch.');
          this.loading.set(false);
        }
      });
  }

  create(): void {
    this.saving.set(true);
    this.api.createAutomation(this.form).subscribe({
      next: () => {
        this.form = this.emptyForm();
        this.saving.set(false);
        this.message.set('Automatización creada correctamente.');
        this.refresh();
      },
      error: () => { this.saving.set(false); this.message.set('Revisa los datos ingresados.'); }
    });
  }

  toggle(item: Automation): void { this.api.toggleAutomation(item.id).subscribe(() => this.refresh()); }

  run(item: Automation, outcome: RunStatus): void {
    this.api.simulate(item.id, outcome).subscribe({
      next: () => { this.message.set(`Ejecución de ${item.name} registrada.`); this.refresh(); },
      error: () => this.message.set('Activa la automatización antes de ejecutarla.')
    });
  }

  statusLabel(status: AutomationStatus): string {
    return { ACTIVE: 'Activa', PAUSED: 'Pausada', MAINTENANCE: 'Mantenimiento' }[status];
  }

  runLabel(status: RunStatus): string {
    return { SUCCESS: 'Exitosa', FAILED: 'Fallida', RUNNING: 'En ejecución' }[status];
  }

  private emptyForm(): AutomationPayload {
    return { name: '', area: '', owner: '', schedule: '', description: '' };
  }
}
