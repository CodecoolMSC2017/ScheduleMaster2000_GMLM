let modifiedScheduleId;

function getModifiedSchedule() {
    const params = new URLSearchParams();
    params.append('id', modifiedScheduleId);

    const xhr = new XMLHttpRequest();
    xhr.addEventListener('load', onScheduleResponse);
    xhr.addEventListener('error', onNetworkError);
    xhr.open('GET', 'protected/schedule?' + params.toString());
    xhr.send(params);
}

function onDayModifyResponse() {
    removeAllChildren(dayInfoContentDivEl);
    if (this.status === OK) {
        onCloseDaySpanClicked();
        getModifiedSchedule();
    }
    const pEl = document.createElement('p');
    const response = JSON.parse(this.responseText);
    pEl.textContent = response.message;
    dayInfoContentDivEl.append(pEl);
}

function onDayModifyLoad(id) {
     const dayTitleInputEl = document.getElementById('new-day-title');

     const params = new URLSearchParams();

     params.append('id', id);
     params.append('title', dayTitleInputEl.value);

     const xhr = new XMLHttpRequest();
     xhr.addEventListener('load', onDayModifyResponse);
     xhr.addEventListener('error', onNetworkError);
     xhr.open('PUT', 'protected/day?' + params.toString());
     xhr.send(params);
}

function onDayFormContentLoad(day) {
    const dayTitleInputEl = document.getElementById('new-day-title');
    dayTitleInputEl.value = day.title;
}

function onDayContentResponse() {
    if (this.status === OK) {
        onDayFormContentLoad(JSON.parse(this.responseText));
    } else {
        onOtherResponse(dayInfoContentDivEl, this);
    }
}

function onCloseDaySpanClicked() {
    const dayPopUpFormDivEl = document.getElementById('add-day-modal');
    dayPopUpFormDivEl.style.display = "none";
}

function onModifyDayButtonClicked() {
    removeAllChildren(dayInfoContentDivEl);

    const dayId = this.id;
    modifiedScheduleId = this.dataset.scheduleId;

    const modifyDayButton = document.getElementById('modify-day-button');
    modifyDayButton.addEventListener('click', function dayLoadHandler()  {
        onDayModifyLoad(dayId);
        modifyDayButton.removeEventListener('click', dayLoadHandler);
    });

    popupFormDivEl = document.getElementById('add-day-modal');

    const closeSpan = document.getElementById('close-day-modal');
    closeSpan.addEventListener('click', onCloseDaySpanClicked);

    popupFormDivEl.style.display = "block";
    showContents(['modify-day-button', 'schedule-content', 'menu-content', 'day-info-content']);

    const params = new URLSearchParams();
    params.append('id', dayId);

    const xhr = new XMLHttpRequest();
    xhr.addEventListener('load', onDayContentResponse);
    xhr.addEventListener('error', onNetworkError);
    xhr.open('GET', 'protected/day?' + params.toString());
    xhr.send(params);
    }

