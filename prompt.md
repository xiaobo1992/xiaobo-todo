Functional Requirements

1. Users should be able to create, edit, and delete tasks.
2. Users can mark a task as completed.

Application Architecture
We'll use Docker containers to build the app, with separate frontend and backend codebases. Docker will handle the entire packaging and running, so we need to add the relevant config files.
We'll use Next.js for the frontend and the Micronaut framework for the backend. The frontend is responsible for the todo list UI; the backend handles the API. For storage, we'll use a NoSQL database (MongoDB) to hold all task data.
Let's build a skeleton first to confirm the connection works. We'll add a health-check controller to verify that the system builds and runs successfully. Once the skeleton is working, we'll start building the actual features.
UI

1. Create two sections — Completed and Not Completed — to list all todo tasks.
2. Create an edit/create page for the relevant task operations.
3. Each unfinished task needs an option to edit it.

Backend API

1. `GET /task`
2. `POST /task` — `{ title, description, dueDate }`
3. `PUT /task/:id`
4. `DELETE /task/:id`

Entity
Task

* Required: title, description, completed (boolean), createdDate
* Optional: dueDate (date)
* Non-required: additionalContent
