let popupScheduleFormDivEl;
let addScheduleForm;

function onAddScheduleResponse() {
    removeAllChildren(scheduleInfoContentDivId);
    if(this.status === OK) {
        addScheduleForm.reset();
        onScheduleCloseSpanClicked();
        onSchedulesButtonClicked();
    }
    const pEl = document.createElement('p');
    const response = JSON.parse(this.responseText);
    pEl.textContent = response.message;
    scheduleInfoContentDivId.append(pEl);
}

function onSaveScheduleButtonClicked() {
    const scheduleNameInputEl = document.getElementById('new-schedule-name');

    scheduleName = scheduleNameInputEl.value;

    const params = new URLSearchParams();
    params.append('name', scheduleName);

    const xhr = new XMLHttpRequest();
    xhr.addEventListener('load', onAddScheduleResponse);
    xhr.addEventListener('error', onNetworkError);
    xhr.open('POST', 'protected/schedule');
    xhr.send(params);
}

function onScheduleCloseSpanClicked() {
    onSchedulesButtonClicked();
    popupScheduleFormDivEl.style.display = "none";
}

function onAddScheduleButtonClicked() {
    removeAllChildren(scheduleInfoContentDivId);
    addScheduleForm = document.getElementById('add-schedule-form');
    addScheduleForm.reset();
    const addScheduleButtonEl = document.getElementById('save-schedule-button');
    addScheduleButtonEl.addEventListener('click', onSaveScheduleButtonClicked);

    popupScheduleFormDivEl = document.getElementById('add-schedule-modal');

    const closeSpan = document.getElementById('close-schedule-modal');
    closeSpan.addEventListener('click', onScheduleCloseSpanClicked);

    removeAllChildren(scheduleInfoContentDivId);
    popupScheduleFormDivEl.style.display = "block";
    showContents(['save-schedule-button', 'schedules-content', 'menu-content', 'add-schedule-button-content']);
    onSchedulesButtonClicked();
}
