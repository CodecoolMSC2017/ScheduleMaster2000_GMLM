function appendSchedule(schedule) {
    const nameAEl = document.createElement('a');
    nameAEl.textContent = schedule.name;

    const deleteButtonEl = document.createElement('button');
    deleteButtonEl.textContent = 'Delete';

    const modifyButtonEl = document.createElement('button');
    modifyButtonEl.textContent = 'Modify';

    const nameTdEl = document.createElement('td');
    nameTdEl.appendChild(nameAEl);

    const deleteTdEl = document.createElement('td');
    deleteTdEl.appendChild(deleteButtonEl);

    const modifyTdEl = document.createElement('td');
    modifyTdEl.appendChild(modifyButtonEl);

    const trEl = document.createElement('tr');
    trEl.appendChild(nameTdEl);
    trEl.appendChild(deleteTdEl);
    trEl.appendChild(modifyTdEl);
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
    const schedulesTableEl = document.getElementById('schedules');
    const schedulesTableBodyEl = schedulesTableEl.querySelector('tbody');

    appendSchedules(schedules);
}

function onSchedulesResponse() {
    if (this.status === OK) {
        showContents(['menu-content', 'schedules-content']);
        onSchedulesLoad(JSON.parse(this.responseText));
    } else {
        onOtherResponse(menuContentDivEl, this);
    }
}

function onSchedulesButtonClicked() {
    const xhr = new XMLHttpRequest();
    xhr.addEventListener('load', onSchedulesResponse);
    xhr.addEventListener('error', onNetworkError);
    xhr.open('GET', 'protected/schedules');
    xhr.send();
}