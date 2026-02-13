# Task Manager Project Documentation

## Product Vision
Deliver a simple, reliable console-based Task Manager that lets users capture, manage, and complete tasks quickly with clear feedback and basic persistence.

## Product Backlog
Priorities: P1 (highest) to P3 (lowest). Estimates use story points (SP).

1. P1 - As a user, I can add a task with a title and optional description so that I can capture work quickly. (SP: 2)
   - Acceptance Criteria:
     - Running `add <title>` creates a new task with status `OPEN`.
     - Optional description can be provided using `add <title> | <description>`.
     - The app confirms the task id and title after creation.
2. P1 - As a user, I can list tasks so that I can see what I need to do. (SP: 2)
   - Acceptance Criteria:
     - `list` shows all tasks with id, title, and status.
     - Tasks are displayed in a stable, readable format.
3. P2 - As a user, I can complete a task so that I can track progress. (SP: 3)
   - Acceptance Criteria:
     - `complete <id>` marks the task as `DONE`.
     - Completing a task updates completion timestamp.
     - A helpful error message is shown for unknown ids.
4. P2 - As a user, I can filter tasks by status so that I can focus on open work. (SP: 3)
   - Acceptance Criteria:
     - `list open` shows only `OPEN` tasks.
     - `list done` shows only `DONE` tasks.
5. P2 - As a user, tasks persist between runs so that I don't lose my data. (SP: 5)
   - Acceptance Criteria:
     - Tasks are saved to a local file automatically.
     - Tasks are loaded when the app starts.
6. P3 - As a user, I can delete a task so that I can remove outdated items. (SP: 2)
   - Acceptance Criteria:
     - `delete <id>` removes a task permanently.
     - An error message is shown for unknown ids.
7. P3 - As a user, I can view a simple status summary so that I can see counts at a glance. (SP: 1)
   - Acceptance Criteria:
     - `status` shows total, open, and done counts.

## Definition of Done (DoD)
- Code is implemented and reviewed for clarity and correctness.
- Unit tests written and passing in CI.
- Acceptance criteria verified for the story.
- Documentation updated (project_docs.md and any usage notes).
- No critical lints or build failures.

## Sprint 0 - Planning
Deliverables: Product vision, backlog with acceptance criteria/estimates, DoD, Sprint plans.

## Sprint 1 Plan (Selected Stories)
- Story 1: Add task (SP 2)
- Story 2: List tasks (SP 2)
- Story 6: Delete task (SP 2)
Goal: Provide a usable MVP for capturing and viewing tasks, with CI and tests in place.

## Sprint 2 Plan (Selected Stories)
- Story 3: Complete task (SP 3)
- Story 4: Filter by status (SP 3)
- Story 5: Persistence (SP 5)
- Story 7: Status summary (SP 1)
Goal: Improve task lifecycle, persistence, and monitoring/logging.

## CI/CD and Testing
- CI pipeline: GitHub Actions running `mvn test` on pushes and PRs.
- Testing: JUnit 5 unit tests for service and repository behaviors.

## Monitoring/Logging
- Console logs for key user actions and error handling.

## Sprint 1 Review
- Completed: Add task, list tasks, delete tasks, CI pipeline, unit tests.
- Outcome: First usable increment delivered with automated tests.

## Sprint 1 Retrospective
Improvements to apply in Sprint 2:
1. Add persistence testing earlier to reduce integration surprises.
2. Improve CLI feedback (status summary and clearer help text).

## Sprint 2 Review
- Completed: Complete tasks, filter by status, persistence, status summary, logging improvements.
- Outcome: Full task lifecycle with saved data and basic monitoring.

## Sprint 2 Retrospective (Final)
- Improved test coverage earlier reduced rework and sped integration.
- Clearer CLI feedback reduced user confusion.
- Key lesson: Deliver thin vertical slices with tests to keep scope controlled.

## Repository
- https://github.com/Law-son/task-manager.git

