"use client";

import Link from "next/link";
import { useCallback, useEffect, useState } from "react";
import { deleteTask, listTasks, Task, toInput, updateTask } from "@/lib/api";

function TaskItem({ task, onToggle, onDelete }: {
  task: Task;
  onToggle: (t: Task) => void;
  onDelete: (t: Task) => void;
}) {
  return (
    <li className="task">
      <input type="checkbox" checked={task.completed} onChange={() => onToggle(task)} aria-label="Completed" />
      <div className="grow">
        <strong className={task.completed ? "done" : ""}>{task.title}</strong>
        <div>{task.description}</div>
        {task.dueDate && <small>Due {task.dueDate}</small>}
        {task.additionalContent && <div><small>{task.additionalContent}</small></div>}
      </div>
      {!task.completed && <Link href={`/tasks/${task.id}/edit`}>Edit</Link>}
      <button onClick={() => onDelete(task)}>Delete</button>
    </li>
  );
}

export default function Home() {
  const [tasks, setTasks] = useState<Task[] | null>(null);
  const [error, setError] = useState<string | null>(null);

  const load = useCallback(() => {
    listTasks().then(setTasks).catch((e) => setError(String(e)));
  }, []);
  useEffect(load, [load]);

  const run = (p: Promise<unknown>) => p.then(load).catch((e) => setError(String(e)));
  const onToggle = (t: Task) => run(updateTask(t.id, toInput(t, { completed: !t.completed })));
  const onDelete = (t: Task) => confirm(`Delete "${t.title}"?`) && run(deleteTask(t.id));

  const open = tasks?.filter((t) => !t.completed) ?? [];
  const done = tasks?.filter((t) => t.completed) ?? [];

  return (
    <main>
      <div className="row between">
        <h1>Todo</h1>
        <Link href="/tasks/new" className="btn">+ New task</Link>
      </div>
      {error && <p className="error">Error: {error}</p>}
      {!tasks && !error && <p>Loading…</p>}
      {tasks && (
        <>
          <h2>Not Completed ({open.length})</h2>
          <ul>{open.map((t) => <TaskItem key={t.id} task={t} onToggle={onToggle} onDelete={onDelete} />)}</ul>
          {open.length === 0 && <p className="muted">Nothing to do.</p>}
          <h2>Completed ({done.length})</h2>
          <ul>{done.map((t) => <TaskItem key={t.id} task={t} onToggle={onToggle} onDelete={onDelete} />)}</ul>
          {done.length === 0 && <p className="muted">Nothing completed yet.</p>}
        </>
      )}
    </main>
  );
}
