let schedulesTableEl;
let schedulesTableBodyEl;

function appendSchedule(schedule) {
    const nameAEl = document.createElement('a');
    nameAEl.textContent = schedule.name;
    nameAEl.href = 'javascript:void(0);';
    nameAEl.dataset.scheduleId = schedule.id;
    nameAEl.addEventListener('click', onScheduleClicked);

    const modifyButtonEl = document.createElement('input');
    modifyButtonEl.setAttribute('type', 'image');
    modifyButtonEl.setAttribute('src', 'icons/modify.png')
    modifyButtonEl.dataset.scheduleId = schedule.id;
    modifyButtonEl.addEventListener('click', onModifyScheduleButtonClicked);

    const publishButtonEl = document.createElement('input');
    publishButtonEl.dataset.scheduleId = schedule.id;

    if(!schedule.published) {
        publishButtonEl.setAttribute('type', 'image');
        publishButtonEl.setAttribute('src', 'icons/share2.png');
        publishButtonEl.addEventListener('click', onPublishButtonClicked);
    } else {
        publishButtonEl.setAttribute('type', 'image');
        publishButtonEl.setAttribute('src', 'icons/unshare.png');
        publishButtonEl.addEventListener('click', onUnpublishButtonClicked);
    }

    const deleteButtonEl = document.createElement('input');
    deleteButtonEl.setAttribute('type', 'image');
    deleteButtonEl.setAttribute('src', 'icons/delete.png');
    deleteButtonEl.dataset.scheduleId = schedule.id;
    deleteButtonEl.addEventListener('click', onScheduleDeleteButtonClicked);

    const nameTdEl = document.createElement('td');
    nameTdEl.appendChild(nameAEl);

    const modifyTdEl = document.createElement('td');
    modifyTdEl.appendChild(modifyButtonEl);

    const deleteTdEl = document.createElement('td');
    deleteTdEl.appendChild(deleteButtonEl);

    const publishTdEl = document.createElement('td');
    publishTdEl.appendChild(publishButtonEl);

    const trEl = document.createElement('tr');
    trEl.setAttribute('id', schedule.id);
    trEl.appendChild(nameTdEl);
    trEl.appendChild(modifyTdEl);
    trEl.appendChild(deleteTdEl);
    trEl.appendChild(publishTdEl);
    schedulesTableBodyEl.appendChild(trEl);
}

function appendSchedules(schedules) {
    removeAllChildren(schedulesTableBodyEl);

    for (let i = 0; i < schedules.length; i++) {
        const schedule = schedules[i];
        appendSchedule(schedule);
    }
}

function onSchedulesLoad(schedules) {
    schedulesTableEl = document.getElementById('schedules');
    schedulesTableBodyEl = document.getElementById('schedules-tbody');

    appendSchedules(schedules);
}

function onSchedulesResponse() {
    clearMessages();

    if (this.status === OK) {
        showContents(['menu-content', 'schedules-content', 'add-schedule-button-content', 'save-schedule-button']);
        onSchedulesLoad(JSON.parse(this.responseText));
    } else {
        showContents(['menu-content', 'error-message-content', 'add-schedule-button-content', 'save-schedule-button']);
        onOtherResponse(errorMessageContentDivEl, this);
    }
}

function onSchedulesButtonClicked() {
    const xhr = new XMLHttpRequest();
    xhr.addEventListener('load', onSchedulesResponse);
    xhr.addEventListener('error', onNetworkError);
    xhr.open('GET', 'protected/schedules');
    xhr.send();
}
