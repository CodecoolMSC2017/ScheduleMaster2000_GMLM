let publishedSchedule;
let ulEl;

function guestAppendDay(day) {
    const hEl = document.createElement('h1');
    hEl.textContent = publishedSchedule;

    const titleAEl = document.createElement('a');
    titleAEl.textContent = day.title;
    titleAEl.dataset.dayId = day.id;
    titleAEl.href = 'javascript:void(0);';
    titleAEl.addEventListener('click', onGuestDayClicked);

    const titleLiEl = document.createElement('li');
    titleLiEl.appendChild(titleAEl);

    ulEl.appendChild(titleLiEl);

    guestScheduleContentDivEl.appendChild(ulEl);
}

function onGuestScheduleLoad(dto) {
    removeAllChildren(guestScheduleContentDivEl);
    const days = dto.daysOfSchedule;
    publishedSchedule = dto.schedule;
    const hEl = document.createElement('h1');
    hEl.textContent = publishedSchedule.name;
    ulEl = document.createElement('ul');
    guestScheduleContentDivEl.appendChild(hEl);
    for (let i = 0; i < days.length; i++) {
        const day = days[i];
        guestAppendDay(day);
    }
}

function onGuestScheduleResponse() {
    clearMessages();

    if (this.status === OK) {
        showContents(['guest-schedule-content']);
        onGuestScheduleLoad(JSON.parse(this.responseText));
    } else {
        showContents(['error-message-content']);
        onOtherResponse(errorMessageContentDivEl, this);
    }
}

function onGuestScheduleRequest(pathname) {
    console.log(pathname);
    const xhr = new XMLHttpRequest();
    xhr.addEventListener('load', onGuestScheduleResponse);
    xhr.addEventListener('error', onNetworkError);
    xhr.open('GET', pathname);
    xhr.send();
}

document.addEventListener('DOMContentLoaded', checkRequest);

