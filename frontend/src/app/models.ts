export type AutomationStatus = 'ACTIVE' | 'PAUSED' | 'MAINTENANCE';
export type RunStatus = 'SUCCESS' | 'FAILED' | 'RUNNING';

export interface Automation {
  id: number;
  name: string;
  area: string;
  owner: string;
  schedule: string;
  description: string;
  status: AutomationStatus;
  updatedAt: string;
}

export interface AutomationPayload {
  name: string;
  area: string;
  owner: string;
  schedule: string;
  description: string;
}

export interface AutomationRun {
  id: number;
  automationId: number;
  automationName: string;
  status: RunStatus;
  startedAt: string;
  finishedAt?: string;
  recordsProcessed: number;
  message: string;
}

export interface Dashboard {
  automations: number;
  active: number;
  executions: number;
  successRate: number;
  failed: number;
}
