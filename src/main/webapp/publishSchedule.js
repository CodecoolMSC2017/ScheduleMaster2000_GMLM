function onPublishScheduleResponse(scheduleId) {
    const params = new URLSearchParams();
    params.append('scheduleId', scheduleId);

    const xhr = new XMLHttpRequest();
    xhr.addEventListener('load', onSchedulesButtonClicked);
    xhr.addEventListener('error', onNetworkError);
    xhr.open('PUT', 'guestschedule?', + params.toString());
    xhr.send(params);
}

function onPublishButtonClicked() {
    const scheduleId = this.dataset.scheduleId;

    if (confirm("Do you want to publish this schedule?")) {
        onPublishScheduleResponse(scheduleId);
    } else {
        onSchedulesButtonClicked();
    }
}
