# Todo App

A simple todo list. Next.js frontend, Spring Boot backend, local JSON file storage (no database), all run with Docker Compose.

> **Note:** While developing this project, `docker pull` from Docker Hub was unreliable from my network in China (timeouts, `401 Unauthorized` from mirrors), so I could not get a full `docker compose up` run to complete and verify the app end to end. Please review the entire work in the repository: https://github.com/xiaobo1992/xiaobo-todo. It contains the full backend (Task CRUD API and health check), frontend (list, create and edit pages), Docker configuration, and the original requirements in [prompt.md](prompt.md).

| Service  | Tech                     | URL                          |
|----------|--------------------------|------------------------------|
| frontend | Next.js 15 (Node 20)     | http://localhost:3000        |
| backend  | Spring Boot 3 (Java 17)  | http://localhost:8080        |

> The backend uses Spring Boot rather than Micronaut because the Micronaut dependencies kept failing to install (Gradle plugin and artifact downloads).

## Prerequisites

- Docker Desktop (or Docker Engine + Compose v2), running

## Start the app

```bash
docker compose up --build
```

The first build downloads base images and dependencies and takes a few minutes. Add `-d` to run in the background.

When it is up:

- Open http://localhost:3000 for the todo UI.
- Tasks are saved by the backend in `data/tasks.json` (mounted from `./data`); the file is loaded on start and rewritten on every change. Set `TODO_DATA_FILE` to use another path.
- Check the backend: `curl http://localhost:8080/health` returns `{"status":"UP"}`.

## Run locally (without Docker)

Start the backend (http://localhost:8080):

```bash
cd ./backend
./gradlew bootrun
```

In another terminal, start the frontend (http://localhost:3000):

```bash
cd ./frontend
npm start
```

## Stop the app

```bash
docker compose down
```

## Using the app

- `/` lists tasks in **Not Completed** and **Completed** sections. Tick the checkbox to complete or reopen a task.
- **+ New task** opens the create page; **Edit** (unfinished tasks only) opens the edit page.

## Backend API

| Method | Path        | Body                                                      |
|--------|-------------|-----------------------------------------------------------|
| GET    | `/task`     |                                                           |
| POST   | `/task`     | `{ title, description, dueDate?, additionalContent? }`    |
| PUT    | `/task/:id` | `{ title, description, dueDate?, additionalContent?, completed? }` |
| DELETE | `/task/:id` |                                                           |

The browser calls the backend through the frontend's `/api/*` proxy (e.g. `/api/task`), so no CORS setup is needed.

## Docker registry mirror (China)

Docker Hub can be slow or blocked. Base images are pulled through a configurable mirror, set in [.env](.env):

```
REGISTRY=docker.m.daocloud.io/library
```

To use Docker Hub directly, set `REGISTRY=docker.io/library`. The backend build also fetches Gradle plugins and dependencies through Aliyun Maven mirrors (see `backend/gradle-mirror.init.gradle`).

## Troubleshooting

- **`401 Unauthorized` or TLS timeout pulling an image:** the mirror is flaky; retry, or pre-pull the image, e.g. `docker pull docker.m.daocloud.io/library/node:20-alpine`.
- **Backend build times out downloading Gradle artifacts:** re-run `docker compose up --build`; downloads are cached between attempts.
- **Frontend shows "Backend error":** check `docker compose logs backend` and that `/health` reports `UP`.

## Project layout

```
backend/    Spring Boot API (Task CRUD + /health)
frontend/   Next.js UI
docker-compose.yml
.env        Registry mirror setting
```
