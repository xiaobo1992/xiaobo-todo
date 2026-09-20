import Link from "next/link";
import TaskForm from "@/components/TaskForm";

export default function NewTaskPage() {
  return (
    <main>
      <p><Link href="/">← Back</Link></p>
      <h1>New task</h1>
      <TaskForm />
    </main>
  );
}
