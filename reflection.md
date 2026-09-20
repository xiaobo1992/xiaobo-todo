# Reflection

## How did you break down the problem?

1. Separated the frontend, backend, and database work, and defined the function of each part.
2. Chose the technologies to use for the relevant architecture.
3. Defined the UI for the frontend.
4. Defined the backend work, including the API and its interaction with the database.
5. Originally defined the database schema using MongoDB, since it's more flexible. Later I dropped MongoDB (see below) and stored tasks in a local JSON file instead.

## What did the AI get wrong, and how did you fix it?

1. I asked the AI to build with the latest framework version, but since we're not in the US, the dependency pulls didn't work well. I had to fall back to a lower version and ask the AI to fix the resulting bugs.
2. The AI was fairly slow to locate problems, so I ran `docker compose` manually to see what was failing and fixed it myself.
3. Even so, I could not get Docker to connect and run successfully: image pulls from Docker Hub and its mirrors kept timing out or returning `401 Unauthorized`, so MongoDB and the containers never came up. To finish the work, I changed the design: I removed MongoDB and the Docker dependency for storage, and the backend now stores tasks in a local JSON file (`data/tasks.json`, configurable via `TODO_DATA_FILE`): it loads the file at startup and writes every change back to it. The trade-off is that it is single-user and not suited to concurrent or large-scale use. (I first tried doing the file access from the browser, but the right place for it is the backend, so I moved it there.)

## What did you deliberately not delegate to the AI?

1. The overall architecture — it's better kept under my control, and defining it myself let me think it through more clearly.
2. The database schema and API endpoints, for the same reason: keeping them under my control helped me plan the next steps.
3. The frontend interactions, because handling these myself let me think more carefully about the user experience.

## What would you do differently with more time?

1. Add authentication and multi-user support so the app could be used by more people.
2. Bring a real database back (MongoDB or similar) once Docker works reliably, and improve query performance.
3. Fix the Docker registry access problem (a working mirror or proxy) so the full `docker compose up` flow can be verified end to end.
