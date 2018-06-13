let address = "http://localhost:8080/schedule-master-2000-GMLM/guestschedule?id=hasdáÉFLAsd";

function onPublishScheduleResponse(scheduleId) {
    const params = new URLSearchParams();
    params.append('id', scheduleId);

    address = address + params.toString();

    const xhr = new XMLHttpRequest();
    xhr.addEventListener('load', onSchedulesButtonClicked);
    xhr.addEventListener('error', onNetworkError);
    xhr.open('PUT', 'protected/publish?' + params.toString());
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

function onUnpublishButtonClicked() {
    const scheduleId = this.dataset.scheduleId;
    onPublishScheduleResponse(scheduleId);
}

