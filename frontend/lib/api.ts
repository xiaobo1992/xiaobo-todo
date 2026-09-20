export type Task = {
  id: string;
  title: string;
  description: string;
  completed: boolean;
  createdDate: string;
  dueDate?: string | null;
  additionalContent?: string | null;
};

export type TaskInput = {
  title: string;
  description: string;
  dueDate?: string | null;
  additionalContent?: string | null;
  completed?: boolean;
};

async function request<T>(path: string, init?: RequestInit): Promise<T> {
  const res = await fetch(`/api${path}`, {
    ...init,
    headers: { "Content-Type": "application/json", ...init?.headers },
  });
  if (!res.ok) throw new Error(`Request failed (${res.status})`);
  return res.status === 204 ? (undefined as T) : res.json();
}

export const listTasks = () => request<Task[]>("/task");
export const createTask = (t: TaskInput) =>
  request<Task>("/task", { method: "POST", body: JSON.stringify(t) });
export const updateTask = (id: string, t: TaskInput) =>
  request<Task>(`/task/${id}`, { method: "PUT", body: JSON.stringify(t) });
export const deleteTask = (id: string) => request<void>(`/task/${id}`, { method: "DELETE" });

export const toInput = (t: Task, patch: Partial<TaskInput> = {}): TaskInput => ({
  title: t.title,
  description: t.description,
  dueDate: t.dueDate ?? null,
  additionalContent: t.additionalContent ?? null,
  completed: t.completed,
  ...patch,
});
