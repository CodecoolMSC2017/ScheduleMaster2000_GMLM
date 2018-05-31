let popupModifyScheduleFormDivEl;

function onModifyScheduleResponse() {
    removeAllChildren(scheduleInfoContentDivId);
    if (this.status === OK) {
        onModifyScheduleCloseSpanClicked();
        onSchedulesButtonClicked();
    }
    const pEl = document.createElement('p');
    const response = JSON.parse(this.responseText);
    pEl.textContent = response.message;
    infoContentDivEl.append(pEl);
}

function onScheduleModifyLoad(id) {
    const scheduleNameInputEl = document.getElementById('new-schedule-name');

    const params = new URLSearchParams();

    params.append('name', scheduleNameInputEl.value);
    params.append('id', id);

    const xhr = new XMLHttpRequest();
    xhr.addEventListener('load', onModifyScheduleResponse);
    xhr.addEventListener('error', onNetworkError);
    xhr.open('PUT', 'protected/schedule?' + params.toString());
    xhr.send(params);
}

function onModifyScheduleFormContentLoad(schedule) {
    const scheduleNameInputEl = document.getElementById('new-schedule-name');

    scheduleNameInputEl.value = schedule.name;
}

function onModifyScheduleContentResponse() {
    if (this.status === OK) {
        onModifyScheduleFormContentLoad(JSON.parse(this.responseText));
    } else {
        onOtherResponse(infoContentDivEl, this);
    }
}

function onModifyScheduleCloseSpanClicked() {
    popupModifyScheduleFormDivEl.style.display = "none";
}

function onModifyScheduleButtonClicked() {
    removeAllChildren(scheduleInfoContentDivId);

    const scheduleId = this.dataset.scheduleId;

    const modifyScheduleButton = document.getElementById('modify-schedule-button');
    modifyScheduleButton.addEventListener('click', function loadHandler() {
        onScheduleModifyLoad(scheduleId);
        modifyScheduleButton.removeEventListener('click', loadHandler);
    });

    popupModifyScheduleFormDivEl = document.getElementById('add-schedule-modal');

    const closeSpan = document.getElementById('close-schedule-modal');
    closeSpan.addEventListener('click', onModifyScheduleCloseSpanClicked);

    popupModifyScheduleFormDivEl.style.display = "block";
    showContents(['modify-schedule-button', 'schedules-content', 'menu-content', 'add-schedule-button-content']);

    const params = new URLSearchParams();
    params.append('id', scheduleId);

    const xhr = new XMLHttpRequest();
    xhr.addEventListener('load', onModifyScheduleContentResponse);
    xhr.addEventListener('error', onNetworkError);
    xhr.open('GET', 'protected/modschedule?' + params.toString());
    xhr.send(params);
}
