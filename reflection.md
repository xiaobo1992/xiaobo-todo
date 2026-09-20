# Reflection

## How did you break down the problem?

1. Separated the frontend, backend, and database work, and defined the function of each part.
2. Chose the technologies to use for the relevant architecture.
3. Defined the UI for the frontend.
4. Defined the backend work, including the API and its interaction with the database.
5. Defined the database schema using MongoDB, since it's more flexible.

## What did the AI get wrong, and how did you fix it?

1. I asked the AI to build with the latest framework version, but since we're not in the US, the dependency pulls didn't work well. I had to fall back to a lower version and ask the AI to fix the resulting bugs.
2. The AI was fairly slow to locate problems, so I ran `docker compose` manually to see what was failing and fixed it myself.

## What did you deliberately not delegate to the AI?

1. The overall architecture — it's better kept under my control, and defining it myself let me think it through more clearly.
2. The database schema and API endpoints, for the same reason: keeping them under my control helped me plan the next steps.
3. The frontend interactions, because handling these myself let me think more carefully about the user experience.

## What would you do differently with more time?

1. Add authentication and multi-user support so the app could be used by more people.
2. Improve database performance so queries run faster and more efficiently.
