# Loxy User Guide

Loxy is a simple task management chatbot that helps you track todos, deadlines, and events.

## Quick Start
1. Download the latest version of Loxy from the repository.
2. Run the `Loxy.jar` file (or run the `Loxy` class in your IDE).
3. You will see a welcome message, and you can start entering commands.

## Features

### 1. Add a Todo Task
Create a task without a specific date.
- **Command**: `todo [description]`
- **Example**: `todo read book`
- **Output**: `Got it. Added this task: [T][ ] read book Now you have 1 tasks.`
### 2. Add a Deadline Task
Create a task with a due date (format: yyyy-MM-dd).
- **Command**: `deadline [description] /by [date]`
- **Example**: `deadline return book /by 2026-06-06`
- **Output**: `Got it. Added this task:[D][ ]  return book (by: Jun 06 2026) Now you have 2 tasks.`
### 3. Add an Event Task
Create a task with start and end dates (format: yyyy-MM-dd).
- **Command**: `event [description] /from [start date] /to [end date]`
- **Example**: `event team meeting /from 2026-03-10 /to 2026-03-10`
- **Output**: `Got it. Added this task: [E][ ] team meeting (from: Mar 10 2026 to: Mar 10 2026) Now you have 3 tasks.`
### 4. List All Tasks
View all your tasks with their status.
- **Command**: `list`
- **Output**: `Your tasks:1.[T][ ] read book 2.[D][ ] return book (by: Jun 06 2026) 3.[E][ ] team meeting (from: Mar 10 2026 to: Mar 10 2026)`
### 5. Mark a Task as Done
Mark a task as completed (use task number from `list`).
- **Command**: `mark [task number]`
- **Example**: `mark 1`
- **Output**: `Marked as done:[T][X] read book`
### 6. Unmark a Task
Mark a completed task as not done.
- **Command**: `unmark [task number]`
- **Example**: `unmark 1`
- **Output**: `Marked as not done:[T][ ] read book`
### 7. Delete a Task
Remove a task from the list.
- **Command**: `delete [task number]`
- **Example**: `delete 2`
- **Output**: `Removed this task:[D][ ] return book (by: Jun 06 2026) Now you have 2 tasks.`
### 8. Find Tasks
Search tasks by keyword or date.
- **Search by keyword**: `find [keyword]` (e.g., `find book`)
- **Search by date**: `find [date]` (e.g., `find 2026-03-10`)
- **Output**: `Matching tasks:1.[T][ ] read book2.[D][ ] return book (by: Jun 06 2026)`
### 9. Exit the Program
Quit Loxy.
- **Command**: `bye`
- **Output**: `Bye! See you soon.`

## Error Handling
Loxy will show clear error messages for invalid commands:
- Empty task description: `The description of a todo cannot be empty.`
- Invalid date format: `Invalid date format! Use yyyy-MM-dd`
- Non-existent task number: `Task number does not exist!`
- Unknown command: `Unknown command!`