function onDeleteDayResponse(dayId) {
    const params = new URLSearchParams();
    params.append('id', dayId);

    const xhr = new XMLHttpRequest();
    xhr.addEventListener('load', onSchedulesButtonClicked);
    xhr.addEventListener('error', onNetworkError);
    xhr.open('DELETE', 'protected/day?' + params.toString());
    xhr.send(params);
}

function onDeleteDayButtonClicked() {
    const dayId = this.dataset.dayId;
    
    if (confirm("Do you want to delete this day?")) {
        onDeleteDayResponse(dayId);
    } else {
        /*Need to retrieve schedule_id to work properly*/
        onScheduleClicked();
    }

}