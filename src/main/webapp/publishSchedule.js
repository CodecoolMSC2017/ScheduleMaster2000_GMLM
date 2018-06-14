
function onPublishScheduleResponse(scheduleId) {
    const params = new URLSearchParams();
    params.append('id', scheduleId);

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
        alert(generateGuestLink(scheduleId) + " has been copied to your clipboard");
    } else {
        onSchedulesButtonClicked();

    }
}

function onUnpublishButtonClicked() {
    const scheduleId = this.dataset.scheduleId;
    onPublishScheduleResponse(scheduleId);
}

function generateGuestLink(scheduleId) {
    const idLink = "id=" + scheduleId;
    const enc = window.btoa(idLink);
    let link = "http://localhost:8080/schedule-master-2000-GMLM/guest?" + enc;

    var aux = document.createElement("input");
    aux.setAttribute("value", link);
    document.body.appendChild(aux);
    aux.select();
    document.execCommand("copy");
    document.body.removeChild(aux);
    return link;
}
