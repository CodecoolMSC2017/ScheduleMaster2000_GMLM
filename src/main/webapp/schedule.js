let scheduleTableEl;
let scheduleTableBodyEl;

function appendDay(day) {
    const titleAEl = document.createElement('a');
    titleAEl.textContent = day.title;
    titleAEl.dataset.dayId = day.id;
    titleAEl.href = 'javascript:void(0);';
    titleAEl.addEventListener('click', onDayClicked);

    const modifyButtonEl = document.createElement('input');
    modifyButtonEl.setAttribute('id', day.id);
    modifyButtonEl.setAttribute('type', 'image');
    modifyButtonEl.setAttribute('src', 'icons/modify.png');
    modifyButtonEl.dataset.scheduleId = day.scheduleId;
    modifyButtonEl.addEventListener('click', onModifyDayButtonClicked);

    const deleteButtonEl = document.createElement('input');
    deleteButtonEl.setAttribute('type', 'image');
    deleteButtonEl.setAttribute('src', 'icons/delete.png');
    deleteButtonEl.dataset.dayId = day.id;
    deleteButtonEl.dataset.scheduleId = day.scheduleId;
    deleteButtonEl.addEventListener('click', onDeleteDayButtonClicked);

    const addTaskToDayButtonEl = document.createElement('input');
    addTaskToDayButtonEl.setAttribute('type', 'image');
    addTaskToDayButtonEl.setAttribute('src', 'icons/add.png');
    addTaskToDayButtonEl.dataset.dayId = day.id;
    addTaskToDayButtonEl.addEventListener('click', onAddTaskToDayButtonClicked);

    const titleTdEl = document.createElement('td');
    titleTdEl.appendChild(titleAEl);

    const modifyTdEl = document.createElement('td');
    modifyTdEl.appendChild(modifyButtonEl);

    const deleteTdEl = document.createElement('td');
    deleteTdEl.appendChild(deleteButtonEl);

    const trEl = document.createElement('tr');
    trEl.appendChild(addTaskToDayButtonEl);
    trEl.appendChild(titleTdEl);
    trEl.appendChild(modifyTdEl);
    trEl.appendChild(deleteTdEl);
    trEl.appendChild(addTaskToDayButtonEl);
    scheduleTableBodyEl.appendChild(trEl);
}

function appendDays(days) {
    removeAllChildren(scheduleTableBodyEl);

    for (let i = 0; i < days.length; i++) {
        const day = days[i];
        appendDay(day);
    }
}

function onScheduleLoad(days) {
    scheduleTableEl = document.getElementById('schedule');
    scheduleTableBodyEl = document.getElementById('schedule-tbody');

    appendDays(days);
}

function onScheduleResponse() {
    clearMessages();

    if (this.status === OK) {
        showContents(['menu-content', 'schedule-content', 'add-day-button-content','save-day-button']);
        onScheduleLoad(JSON.parse(this.responseText));
    } else {
        showContents(['menu-content', 'error-message-content', 'add-day-button-content','save-day-button']);
        onOtherResponse(errorMessageContentDivEl, this);
    }
}

function onScheduleClicked() {
    const scheduleId = this.dataset.scheduleId;
    const addNewDayButtonEl = document.getElementById("add-new-day");
    addNewDayButtonEl.dataset.scheduleId = scheduleId;
    const params = new URLSearchParams();
    params.append('id', scheduleId);

    const xhr = new XMLHttpRequest();
    xhr.addEventListener('load', onScheduleResponse);
    xhr.addEventListener('error', onNetworkError);
    xhr.open('GET', 'protected/schedule?' + params.toString());
    xhr.send();
}
