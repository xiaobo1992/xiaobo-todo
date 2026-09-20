"use client";

import { useEffect, useState } from "react";

type Health = { status: string; database: string };

export default function Home() {
  const [health, setHealth] = useState<Health | null>(null);
  const [error, setError] = useState<string | null>(null);

  useEffect(() => {
    fetch("/api/health")
      .then((res) => res.json())
      .then(setHealth)
      .catch((e) => setError(String(e)));
  }, []);

  return (
    <main style={{ maxWidth: 640, margin: "4rem auto", padding: "0 1rem" }}>
      <h1>Todo</h1>
      <h2>System health</h2>
      {error && <p style={{ color: "crimson" }}>Backend unreachable: {error}</p>}
      {!error && !health && <p>Checking…</p>}
      {health && (
        <ul>
          <li>Backend: {health.status}</li>
          <li>MongoDB: {health.database}</li>
        </ul>
      )}
    </main>
  );
}
