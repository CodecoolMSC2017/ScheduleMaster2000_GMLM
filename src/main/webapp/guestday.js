let guestTasksOfDayTableEl;
let guestTasksOfDayTableBodyEl;


function guestAppendTaskToDay(task) {
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

    const trEl = document.createElement('tr');
    trEl.setAttribute('class', task.id);
    trEl.append(nameTdEl);
    trEl.append(contentTdEl);
    trEl.append(startHourTdEl);
    trEl.append(endHourTdEl);

    guestTasksOfDayTableBodyEl.append(trEl);
}


function guestAppendTasksToDay(tasks) {
    removeAllChildren(guestTasksOfDayTableBodyEl);

    for (let i = 0; i < tasks.length; i++) {
        const task = tasks[i];
        guestAppendTaskToDay(task);
    }
}

function onGuestDayLoad(tasks) {
    guestTasksOfDayTableEl = document.getElementById('guest-tasks-of-day');
    guestTasksOfDayTableBodyEl = document.getElementById('guest-tasks-of-day-tbody');

    guestAppendTasksToDay(tasks);
}

function onGuestDayResponse() {
    clearMessages();

    if (this.status === OK) {
        showContents(['guest-schedule-content', 'guest-tasks-of-day-content']);
        onGuestDayLoad(JSON.parse(this.responseText));
    } else {
        showContents(['guest-schedule-content', 'error-message-content']);
        onOtherResponse(errorMessageContentDivEl, this);
    }
}

function onGuestDayClicked() {
    currentDayId = this.dataset.dayId;

    const params = new URLSearchParams();
    params.append('id', currentDayId);

    const xhr = new XMLHttpRequest();
    xhr.addEventListener('load', onGuestDayResponse);
    xhr.addEventListener('error', onNetworkError);
    xhr.open('GET', 'guesttasks?' + params.toString());
    xhr.send();
}