let moddedScheduleId;

function getModdedSchedule() {
    const params = new URLSearchParams();
    params.append('id', moddedScheduleId);

    const xhr = new XMLHttpRequest();
    xhr.addEventListener('load', onScheduleResponse);
    xhr.addEventListener('error', onNetworkError);
    xhr.open('GET', 'protected/schedule?' + params.toString());
    xhr.send(params);
}

function onDeleteDayResponse(dayId) {
    const params = new URLSearchParams();
    params.append('id', dayId);

    const xhr = new XMLHttpRequest();
    xhr.addEventListener('load', getModdedSchedule);
    xhr.addEventListener('error', onNetworkError);
    xhr.open('DELETE', 'protected/day?' + params.toString());
    xhr.send(params);
}

function onDeleteDayButtonClicked() {
    const dayId = this.dataset.dayId;
    moddedScheduleId = this.dataset.scheduleId;

    if (confirm("Do you want to delete this day?")) {
        onDeleteDayResponse(dayId);
    } else {
        /*Need to retrieve schedule_id to work properly*/
        getModdedSchedule();
    }

}
