let tasksOfDayTableEl;
let tasksOfDayTableBodyEl;
let currentDayId;

function appendTaskToDay(task) {
    const nameTdEl = document.createElement('td');
    nameTdEl.textContent = task.name;
    nameTdEl.dataset.taskId = task.id;
    nameTdEl.addEventListener('click', onTaskClicked);

    const contentTdEl = document.createElement('td');
    contentTdEl.textContent = task.content;

    const startHourTdEl = document.createElement('td');
    startHourTdEl.textContent = task.hour - 1 + ":00";

    const endHourTdEl = document.createElement('td');
    endHourTdEl.textContent = task.hour + ":00";

    //const modifyButtonTdEl = document.createElement('td');
    //const modifyTaskButtonEl = document.createElement('button');
    //modifyTaskButtonEl.textContent = "Modify";
    //modifyButtonTdEl.append(modifyTaskButtonEl);
    //modifyTaskButtonEl.addEventListener('click', onModifyButtonClicked);

    const deleteButtonTdEl = document.createElement('td');
    const deleteButtonEl = document.createElement('input');
    deleteButtonEl.setAttribute('type', 'image');
    deleteButtonEl.setAttribute('src', 'icons/delete.png');
    deleteButtonEl.dataset.taskId = task.id;
    deleteButtonEl.dataset.dayId = currentDayId;
    deleteButtonTdEl.append(deleteButtonEl);
    deleteButtonEl.addEventListener('click', onDeleteTaskFromDayButtonClicked);

    const trEl = document.createElement('tr');
    trEl.setAttribute('class', task.id);
    trEl.append(nameTdEl);
    trEl.append(contentTdEl);
    trEl.append(startHourTdEl);
    trEl.append(endHourTdEl);
    //trEl.append(modifyButtonTdEl);
    trEl.append(deleteButtonTdEl);

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
    tasksOfDayTableBodyEl = document.getElementById('tasks-of-day-tbody');

    appendTasksToDay(tasks);
}

function onDayResponse() {
    clearMessages();

    if (this.status === OK) {
        showContents(['menu-content', 'schedule-content', 'tasks-of-day-content', 'add-day-button-content']);
        onDayLoad(JSON.parse(this.responseText));
    } else {
        showContents(['menu-content', 'schedule-content', 'error-message-content', 'add-day-button-content']);
        onOtherResponse(errorMessageContentDivEl, this);
    }
}

function onDayClicked() {
    currentDayId = this.dataset.dayId;

    const params = new URLSearchParams();
    params.append('id', currentDayId);

    const xhr = new XMLHttpRequest();
    xhr.addEventListener('load', onDayResponse);
    xhr.addEventListener('error', onNetworkError);
    xhr.open('GET', 'protected/days?' + params.toString());
    xhr.send();
}

function onDayReload(currentDayId) {
    const params = new URLSearchParams();
    params.append('id', currentDayId);

    const xhr = new XMLHttpRequest();
    xhr.addEventListener('load', onDayResponse);
    xhr.addEventListener('error', onNetworkError);
    xhr.open('GET', 'protected/days?' + params.toString());
    xhr.send();
}
