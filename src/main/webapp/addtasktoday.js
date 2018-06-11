let popupAddTaskToDayFormDivEl;
let addTaskToDayForm;

function onAddTaskToDayResponse() {
    clearMessages();

    const pEl = document.createElement('p');
    const response = JSON.parse(this.responseText);
    pEl.textContent = response.message;
    addTaskToDayInfoContentDivEl.append(pEl);
    showContents(['menu-content', 'schedule-content', 'addtask-to-day-info-content', 'tasks-of-day-content']);

    addTaskToDayForm.reset();
}

function onAddButtonClicked() {

    const chosenTaskInputEl = document.getElementById('task');
    const chosenTask = chosenTaskInputEl.value;

    const chosenStartHourInputEl = document.getElementById('start-hour');
    const chosenStartHour = chosenStartHourInputEl.value;

    const chosenEndHourInputEl = document.getElementById('end-hour');
    const chosenEndHour = chosenEndHourInputEl.value;

    const params = new URLSearchParams();
    params.append('chosenTask', chosenTask);
    params.append('chosenStartHour', chosenStartHour);
    params.append('chosenEndHour', chosenEndHour);

    const xhr = new XMLHttpRequest();
    xhr.addEventListener('load', onAddTaskToDayResponse);
    xhr.addEventListener('error', onNetworkError);
    xhr.open('POST', 'protected/slot?' + params.toString());
    xhr.send(params);
}

function loadAvailableTasks(tasks) {
    const taskSelectEl = document.getElementById('task');

    for (let i = 0; i < tasks.length; i++) {
        const task = tasks[i];
        const option = document.createElement('option');
        option.textContent = task.name;
        option.value = task.id;
        taskSelectEl.append(option);
    }
}

function loadAvailableEndHours(endHours) {
    const endHourSelectEl = document.getElementById('start-hour');

    for (let i = 0; i < endHours.length; i++) {
        const hour = endHours[i];
        const option = document.createElement('option');
        option.textContent = hour + ':00';
        option.value = hour;
        endHourSelectEl.append(option);
    }
}


function loadAvailableStartHours(startHours) {
    const startHourSelectEl = document.getElementById('start-hour');

    for (let i = 0; i < startHours.length; i++) {
        const hour = startHours[i];
        const option = document.createElement('option');
        option.textContent = hour + ':00';
        option.value = hour;
        startHourSelectEl.append(option);
    }
}

function onAddTaskDtoLoad(taskSlotsDto) {
    loadAvailableTasks(taskSlotsDto.unassignedTasks);
    loadAvailableStartHours(taskSlotsDto.startHours);
    loadAvailableEndHours(taskSlotsDto.endHours);
}


function onAddTaskDtoResponse() {
    if (this.status === OK) {
        showContents(['menu-content', 'schedule-content', 'addtask-to-day-info-content', 'tasks-of-day-content']);
        onAddTaskDtoLoad(JSON.parse(this.responseText));
        onTaskClicked();
    } else {
        showContents(['menu-content', 'schedule-content', 'addtask-to-day-info-content', 'tasks-of-day-content']);
        onOtherResponse(addTaskToDayInfoContentDivEl, this);
    }
}

function onCloseSpanClicked() {
     popupAddTaskToDayFormDivEl.style.display = "none";
 }

function onAddTaskToDayButtonClicked() {
    popupAddTaskToDayFormDivEl = document.getElementById('addtask-to-day-modal');

    addTaskToDayForm = document.getElementById('addtask-to-day-form');
    addTaskToDayForm.reset();

    const addTaskToDayButtonEl = document.getElementById('addtask-button');
    addTaskToDayButtonEl.addEventListener('click', onAddButtonClicked);

    const closeSpan = document.getElementById('close-addtask-to-day-modal');
    closeSpan.addEventListener('click', onCloseSpanClicked);

    //removeAllChildren(infoContentDivEl);

    popupAddTaskToDayFormDivEl.style.display = "block";

    const dayId = this.dataset.dayId;

    const params = new URLSearchParams();
    params.append('dayId', dayId);

    const xhr = new XMLHttpRequest();
    xhr.addEventListener('load', onAddTaskDtoResponse);
    xhr.addEventListener('error', onNetworkError);
    xhr.open('GET', 'protected/slot?' + params.toString());
    xhr.send(params);
}