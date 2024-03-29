let tasksTableEl;
let tasksTableBodyEl;

function onTaskClicked() {
    const taskId = this.dataset.taskId;

    const params = new URLSearchParams();
    params.append('id', taskId);

    const xhr = new XMLHttpRequest();
    xhr.addEventListener('load', onTaskResponse);
    xhr.addEventListener('error', onNetworkError);
    xhr.open('GET', 'protected/task?' + params.toString());
    xhr.send(params);
}


function appendTask(task) {
    const idTdEl = document.createElement('td');
    idTdEl.textContent = task.id;

    const nameTdEl = document.createElement('td');
    nameTdEl.textContent = task.name;
    nameTdEl.setAttribute('id', task.id);
    nameTdEl.dataset.taskId = task.id;

    nameTdEl.addEventListener('click', onTaskClicked);

    const modifyButtonTdEl = document.createElement('td');
    const modifyButtonEl = document.createElement('input');
    modifyButtonEl.setAttribute('type', 'image');
    modifyButtonEl.setAttribute('src', 'icons/modify.png');
    modifyButtonEl.setAttribute('id', task.id);
    modifyButtonTdEl.append(modifyButtonEl);

    modifyButtonEl.addEventListener('click', onModifyButtonClicked);

    const deleteButtonTdEl = document.createElement('td');
    const deleteButtonEl = document.createElement('input');
    deleteButtonEl.setAttribute('type', 'image');
    deleteButtonEl.setAttribute('src', 'icons/delete.png');
    deleteButtonTdEl.append(deleteButtonEl);
    deleteButtonEl.dataset.taskId = task.id;

    deleteButtonEl.addEventListener('click', onDeleteTaskButtonClicked);

    const trEl = document.createElement('tr');
    trEl.setAttribute('id', task.id);
    trEl.append(idTdEl);
    trEl.append(nameTdEl);
    trEl.append(modifyButtonTdEl);
    trEl.append(deleteButtonTdEl);
    tasksTableBodyEl.append(trEl);
}

function appendTasks(tasks) {
    removeAllChildren(tasksTableBodyEl);

    for (let i = 0; i < tasks.length; i++) {
        const task = tasks[i];
        appendTask(task);
    }
}

function onTasksLoad(tasks) {
    tasksTableEl = document.getElementById('tasks');
    tasksTableBodyEl = document.getElementById('tasks-content-table');

    appendTasks(tasks);
}

function onTasksResponse() {
    if (this.status === OK) {
        showContents(['menu-content', 'tasks-content', 'add-task-button-content', 'save-task-button']);
        onTasksLoad(JSON.parse(this.responseText));
    } else {
        showContents(['menu-content', 'error-message-content', 'add-task-button-content', 'save-task-button']);
        onOtherResponse(errorMessageContentDivEl, this);
    }
}

function onTasksButtonClicked() {
    clearMessages();
    const xhr = new XMLHttpRequest();
    xhr.addEventListener('load', onTasksResponse);
    xhr.addEventListener('error', onNetworkError);
    xhr.open('GET', 'protected/tasks');
    xhr.send();
}
