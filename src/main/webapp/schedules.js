let schedulesTableEl;
let schedulesTableBodyEl;

function appendSchedule(schedule) {
    const nameAEl = document.createElement('a');
    nameAEl.textContent = schedule.name;
    nameAEl.href = 'javascript:void(0);';
    nameAEl.dataset.scheduleId = schedule.id;
    nameAEl.addEventListener('click', onScheduleClicked);

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
    schedulesTableEl = document.getElementById('schedules');
    schedulesTableBodyEl = schedulesTableEl.querySelector('tbody');

    appendSchedules(schedules);
}

function onSchedulesResponse() {
    showContents(['menu-content', 'schedules-content']);    
    if (this.status === OK) {
        onSchedulesLoad(JSON.parse(this.responseText));
    } else {
        removeAllChildren(schedulesContentDivEl);
        onOtherResponse(schedulesContentDivEl, this);
    }
}

function onSchedulesButtonClicked() {
    const xhr = new XMLHttpRequest();
    xhr.addEventListener('load', onSchedulesResponse);
    xhr.addEventListener('error', onNetworkError);
    xhr.open('GET', 'protected/schedules');
    xhr.send();
}