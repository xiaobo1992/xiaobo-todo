"use client";

import Link from "next/link";
import { useParams } from "next/navigation";
import { useEffect, useState } from "react";
import TaskForm from "@/components/TaskForm";
import { listTasks, Task } from "@/lib/api";

export default function EditTaskPage() {
  const { id } = useParams<{ id: string }>();
  const [task, setTask] = useState<Task | null>(null);
  const [state, setState] = useState<"loading" | "missing" | "error" | "ready">("loading");

  useEffect(() => {
    listTasks()
      .then((all) => {
        const found = all.find((t) => t.id === id);
        setTask(found ?? null);
        setState(found ? "ready" : "missing");
      })
      .catch(() => setState("error"));
  }, [id]);

  return (
    <main>
      <p><Link href="/">← Back</Link></p>
      <h1>Edit task</h1>
      {state === "loading" && <p>Loading…</p>}
      {state === "missing" && <p>Task not found.</p>}
      {state === "error" && <p className="error">Could not load task.</p>}
      {state === "ready" && task && <TaskForm task={task} />}
    </main>
  );
}
