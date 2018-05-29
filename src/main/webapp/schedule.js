let scheduleTableEl;
let scheduleTableBodyEl;

function appendDay(day) {
    const titleAEl = document.createElement('a');
    titleAEl.textContent = day.title;
    titleAEl.dataset.dayId = day.id;
    titleAEl.href = 'javascript:void(0);';
    titleAEl.addEventListener('click', onDayClicked);

    const modifyButtonEl = document.createElement('button');
    modifyButtonEl.textContent = 'Modify';

    const deleteButtonEl = document.createElement('button');
    deleteButtonEl.textContent = 'Delete';
    deleteButtonEl.dataset.dayId = day.id;

    deleteButtonEl.addEventListener('click', onDeleteDayButtonClicked);

    const titleTdEl = document.createElement('td');
    titleTdEl.appendChild(titleAEl);

    const modifyTdEl = document.createElement('td');
    modifyTdEl.appendChild(modifyButtonEl);

    const deleteTdEl = document.createElement('td');
    deleteTdEl.appendChild(deleteButtonEl);

    const trEl = document.createElement('tr');
    trEl.appendChild(titleTdEl);
    trEl.appendChild(modifyTdEl);
    trEl.appendChild(deleteTdEl);
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
        showContents(['menu-content', 'schedule-content']);
        onScheduleLoad(JSON.parse(this.responseText));
    } else {
        showContents(['menu-content', 'error-message-content']);
        onOtherResponse(errorMessageContentDivEl, this);
    }
}

function onScheduleClicked() {
    const scheduleId = this.dataset.scheduleId;

    const params = new URLSearchParams();
    params.append('id', scheduleId);

    const xhr = new XMLHttpRequest();
    xhr.addEventListener('load', onScheduleResponse);
    xhr.addEventListener('error', onNetworkError);
    xhr.open('GET', 'protected/schedule?' + params.toString());
    xhr.send();
}