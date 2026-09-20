"use client";

import Link from "next/link";
import { useRouter } from "next/navigation";
import { FormEvent, useState } from "react";
import { createTask, Task, toInput, updateTask } from "@/lib/api";

export default function TaskForm({ task }: { task?: Task }) {
  const router = useRouter();
  const [title, setTitle] = useState(task?.title ?? "");
  const [description, setDescription] = useState(task?.description ?? "");
  const [dueDate, setDueDate] = useState(task?.dueDate ?? "");
  const [additionalContent, setAdditionalContent] = useState(task?.additionalContent ?? "");
  const [error, setError] = useState<string | null>(null);
  const [saving, setSaving] = useState(false);

  async function onSubmit(e: FormEvent) {
    e.preventDefault();
    setSaving(true);
    setError(null);
    const input = {
      title: title.trim(),
      description: description.trim(),
      dueDate: dueDate || null,
      additionalContent: additionalContent.trim() || null,
    };
    try {
      if (task) await updateTask(task.id, toInput(task, input));
      else await createTask(input);
      router.push("/");
    } catch (err) {
      setError(String(err));
      setSaving(false);
    }
  }

  return (
    <form onSubmit={onSubmit} className="form">
      <label>
        Title *
        <input value={title} onChange={(e) => setTitle(e.target.value)} required />
      </label>
      <label>
        Description *
        <textarea value={description} onChange={(e) => setDescription(e.target.value)} required rows={3} />
      </label>
      <label>
        Due date
        <input type="date" value={dueDate} onChange={(e) => setDueDate(e.target.value)} />
      </label>
      <label>
        Additional content
        <textarea value={additionalContent} onChange={(e) => setAdditionalContent(e.target.value)} rows={3} />
      </label>
      {error && <p className="error">{error}</p>}
      <div className="row">
        <button type="submit" disabled={saving}>{task ? "Save" : "Create"}</button>
        <Link href="/">Cancel</Link>
      </div>
    </form>
  );
}
