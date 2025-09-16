# 📘 XiaoBai User Guide

Welcome to **XiaoBai (小白)** --- your friendly task management
chatbot.\
XiaoBai helps you **track tasks, deadlines, and events** via simple
commands in either console or GUI mode.

------------------------------------------------------------------------

## 🚀 Getting Started

### 1. Running XiaoBai

You can launch XiaoBai in **two ways**:

-   **GUI Mode** (recommended):\
    Run the application and interact in a chat window.\

-   **Console Mode**:\
    Run from the command line:

    ``` bash
    java -jar XiaoBai.jar
    ```

When XiaoBai starts, you'll see this greeting:

    (*^_^*)
     Hello! I'm XiaoBai
     What can I do for you?

------------------------------------------------------------------------

## ✨ Features

### 1. Add a To-Do

Create a simple task without a date.

    todo <description>

**Example:**

    todo read book

------------------------------------------------------------------------

### 2. Add a Deadline

Create a task with a due date/time.

    deadline <description> /by <yyyy-MM-dd HH:mm>

**Example:**

    deadline submit report /by 2025-09-20 23:59

------------------------------------------------------------------------

### 3. Add an Event

Create a task with a start and end time.

    event <description> /from <yyyy-MM-dd HH:mm> /to <yyyy-MM-dd HH:mm>

**Example:**

    event project meeting /from 2025-09-18 10:00 /to 2025-09-18 12:00

------------------------------------------------------------------------

### 4. List All Tasks

See everything you've added.

    list

------------------------------------------------------------------------

### 5. Mark & Unmark Tasks

Mark a task as done or not done.

    mark <task number>
    unmark <task number>

**Example:**

    mark 2

------------------------------------------------------------------------

### 6. Delete a Task

Remove a task permanently.

    delete <task number>

------------------------------------------------------------------------

### 7. Find Tasks by Keyword

Search for tasks matching a word.

    find <keyword>

**Example:**

    find book

------------------------------------------------------------------------

### 8. View Tasks on a Date

See deadlines or events on a specific date.

    on <yyyy-MM-dd>

**Example:**

    on 2025-09-20

------------------------------------------------------------------------

### 9. Clear All Tasks

Remove all tasks at once.

    clear

------------------------------------------------------------------------

### 10. Exit XiaoBai

Say goodbye and exit.

    bye

------------------------------------------------------------------------

## 💾 Data Storage

-   Your tasks are **saved automatically** in `data/xiaobai.txt`.\
-   They are **reloaded** every time you restart XiaoBai.

------------------------------------------------------------------------

## ⚠️ Notes & Error Messages

-   XiaoBai provides **friendly emoji-style error messages** when
    something goes wrong.\

-   Example for unknown commands:

        (・∀・*) I'm sorry, but I don't know what that means.

------------------------------------------------------------------------

## 🎨 GUI Features

-   User messages appear on the right, XiaoBai's replies on the left.\
-   Smooth scrolling chat interface.

------------------------------------------------------------------------



