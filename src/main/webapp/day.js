let tasksOfDayTableEl;
let tasksOfDayTableBodyEl;

function appendTaskToDay(task) {
    const nameTdEl = document.createElement('td');
    nameTdEl.textContent = task.name;
    nameTdEl.dataset.taskId = task.id;
    nameTdEl.addEventListener('click', onTaskClicked);

    const contentTdEl = document.createElement('td');
    content.textContent = task.content;

    const modifyButtonTdEl = document.createElement('td');
    const modifyTaskButtonEl = document.createElement('button');
    modifyTaskButtonEl.textContent = "Modify";
    modifyButtonTdEl.append(modifyTaskButtonEl);

    //modifyTaskButtonEl.addEventListener('click', onModifyButtonClicked);

    const deleteButtonTdEl = document.createElement('td');
    const deleteButtonEl = document.createElement('button');
    deleteButtonEl.textContent = "Delete";
    deleteButtonTdEl.append(deleteButtonEl);

    //deleteButtonEl.addEventListener('click', onDeleteButtonClicked);

    const trEl = document.createElement('tr');
    trEl.append(nameTdEl);
    trEl.append(contentTdEl);
    trEl.append(modifyButtonTdEl);
    trEl.append(deleteButtonEl);

    tasksOfDayTableBodyEl.append(trEl);
}


function appendTasksToDay(tasks) {
    removeAllChildren(tasksOfDayTableBodyEl);

    for (let i = 0; i < tasks.length; i++) {
        const task = tasks[i];
        appendTaskToDay(task);
    }
}

function onDayLoad(tasks) {
    tasksOfDayTableEl = document.getElementById('tasks-of-day');
    tasksOfDayTableBodyEl = tasksOfDayTableEl.querySelector('tbody');

    appendTasksToDay(tasks);
}

function onDayResponse() {
    clearMessages();
    if (this.status === OK) {
        showContents(['menu-content', 'tasks-of-day-content', 'schedule-content']);
        onDayLoad(JSON.parse(this.responseText));
    } else {
        onOtherResponse(tasksOfDayContentDivEl, this);
    }
}

function onDayClicked() {
    const dayId = this.dataset.dayId;

    const params = new URLSearchParam();
    params.append('id', dayId);

    const xhr = new XMLHttpRequest();
    xhr.addEventListener('load', onDayResponse);
    xhr.addEventListener('error', onNetworkError);
    xhr.open('GET', 'protected/day?' + params.toString());
    xhr.send();
}
