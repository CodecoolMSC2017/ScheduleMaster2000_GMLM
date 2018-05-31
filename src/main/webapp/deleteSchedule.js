function onDeleteScheduleResponse(scheduleId) {
    const params = new URLSearchParams();
    params.append('id', scheduleId);

    const xhr = new XMLHttpRequest();
    xhr.addEventListener('load', onSchedulesButtonClicked);
    xhr.addEventListener('error', onNetworkError);
    xhr.open('DELETE', 'protected/schedule?' + params.toString());
    xhr.send(params);
}

function onScheduleDeleteButtonClicked() {
    const scheduleId = this.dataset.scheduleId;

    if (confirm("Do you want to delete this schedule?")) {
        onDeleteScheduleResponse(scheduleId);
        const trElementToRemove = document.getElementById(scheduleId);
        trElementToRemove.parentNode.removeChild(trElementToRemove);
    } else {
        onSchedulesButtonClicked();
    }
}
