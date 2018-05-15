function onTaskLoad(task) {
    const taskContentDivEl = document.getElementById('task-content');
    removeAllChildren(taskContentDivEl);

    const nameHEl = document.createElement('h3');
    nameHEl.textContent = "Name:";

    const nameSpanEl = document.createElement('span');
    nameSpanEl.textContent = task.name;

    const contentHEl = document.createElement('h3');
    contentHEl.textContent = "Content:";

    const contentSpanEl = document.createElement('span');
    contentSpanEl.textContent = task.content;

    taskContentDivEl.append(nameHEl);
    taskContentDivEl.append(nameSpanEl);
    taskContentDivEl.append(contentHEl);
    taskContentDivEl.append(contentSpanEl);
}

function onTaskResponse() {
    if (this.status === OK) {
        clearMessages();
        showContents(['menu-content', 'task-content']);
        onTaskLoad(JSON.parse(this.responseText));
    } else {
        onOtherResponse(tasksContentDivEl, this);
    }
}
