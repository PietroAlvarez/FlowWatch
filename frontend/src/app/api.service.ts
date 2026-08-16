import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Automation, AutomationPayload, AutomationRun, Dashboard, RunStatus } from './models';

@Injectable({ providedIn: 'root' })
export class ApiService {
  private readonly baseUrl = 'http://localhost:8082/api';
  constructor(private readonly http: HttpClient) {}

  getDashboard() { return this.http.get<Dashboard>(`${this.baseUrl}/dashboard`); }
  getAutomations() { return this.http.get<Automation[]>(`${this.baseUrl}/automations`); }
  getRuns() { return this.http.get<AutomationRun[]>(`${this.baseUrl}/runs`); }
  createAutomation(payload: AutomationPayload) { return this.http.post<Automation>(`${this.baseUrl}/automations`, payload); }
  toggleAutomation(id: number) { return this.http.patch<Automation>(`${this.baseUrl}/automations/${id}/toggle`, {}); }
  simulate(id: number, outcome: RunStatus) {
    const params = new HttpParams().set('outcome', outcome);
    return this.http.post<AutomationRun>(`${this.baseUrl}/automations/${id}/run`, {}, { params });
  }
}
