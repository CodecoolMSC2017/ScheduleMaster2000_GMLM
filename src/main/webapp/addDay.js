let popupAddDayFormDivEl
let addDayForm;
let scheduleId;

function getModifiedScheduleAfterDelete() {
    const params = new URLSearchParams();
    params.append('id', scheduleId);

    const xhr = new XMLHttpRequest();
    xhr.addEventListener('load', onScheduleResponse);
    xhr.addEventListener('error', onNetworkError);
    xhr.open('GET', 'protected/schedule?' + params.toString());
    xhr.send(params);
}

function onAddDayResponse() {
    removeAllChildren(infoContentDivEl);
    if(this.status === OK) {
        addDayForm.reset();
        onCloseSpanClicked();
        getModifiedScheduleAfterDelete();
    }
    const pEl = document.createElement('p');
    const response = JSON.parse(this.responseText);
    pEl.textContent = response.message;
    infoContentDivEl.append(pEl);
}

function onSaveDayButtonClicked() {
    const dayTitleInputEl = document.getElementById('new-day-title');
    dayTitle = dayTitleInputEl.value;


    const params = new URLSearchParams();
    params.append('id',scheduleId);
    params.append('title', dayTitle);

    const xhr = new XMLHttpRequest();
    xhr.addEventListener('load', onAddDayResponse);
    xhr.addEventListener('error', onNetworkError);
    xhr.open('POST', 'protected/day');
    xhr.send(params);

}

function onCloseSpanClicked() {
    popupAddDayFormDivEl.style.display = "none";
}

function onAddDayButtonClicked() {
    const addNewDayButtonEl = document.getElementById("add-new-day");

    scheduleId = addNewDayButtonEl.dataset.scheduleId;
    addDayForm = document.getElementById('add-day-form');
    addDayForm.reset();
    const addDayButtonEl = document.getElementById('save-day-button');
    addDayButtonEl.addEventListener('click', onSaveDayButtonClicked);

    popupAddDayFormDivEl = document.getElementById('add-day-modal');

    const closeSpan = document.getElementById('close');
    closeSpan.addEventListener('click', onCloseSpanClicked);

    removeAllChildren(infoContentDivEl);
    popupAddDayFormDivEl.style.display = "block";
    showContents(['save-day-button', 'schedule-content', 'menu-content', 'add-day-button-content']);
}
