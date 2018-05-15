let tasksTableEl;
let tasksTableBodyEl;

function appendTask(task) {
    const idTdEl = document.createElement('td');
    idTdEl.textContent = task.id;

    const nameTdEl = document.createElement('td');
    nameTdEl.textContent = task.name;
    nameTdEl.setAttribute('id', task.id);
    nameTdEl.dataset.taskId = task.id;

    // nameTdEl.addEventListener('click', onTaskClicked);

    const modifyButtonTdEl = document.createElement('td');
    const modifyButtonEl = document.createElement('button');
    modifyButtonEl.textContent = 'Modify';
    modifyButtonTdEl.append(modifyButtonEl);

    //modifyButtonEl.addEventListener('click', onModifyButtonClicked);

    const deleteButtonTdEl = document.createElement('td');
    const deleteButtonEl = document.createElement('button');
    deleteButtonEl.textContent = 'Delete';
    deleteButtonTdEl.append(deleteButtonEl);

    //deleteButtonEl.addEventListener('click', onDeleteButtonClicked);

    const trEl = document.createElement('tr');
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
    tasksTableBodyEl = tasksTableEl.querySelector('tbody');

    appendTasks(tasks);
}

function onTasksResponse() {
    if (this.status === OK) {
        showContents(['menu-content', 'tasks-content']);
        onTasksLoad(JSON.parse(this.responseText));
    } else {
        onOtherResponse(tasksContentDivEl, this);
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