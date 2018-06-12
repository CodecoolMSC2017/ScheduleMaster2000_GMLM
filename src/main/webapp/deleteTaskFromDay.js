function onDeleteTasksFromTable(taskId) {
    const tasks = document.getElementsByClassName(taskId);

    for (let i = 0; i < tasks.length; i++) {
        removeAllChildren(tasks[i]);
    }
}

function onDeleteTaskFromDayResponse(taskId, dayId) {
    const params = new URLSearchParams();
    params.append('dayId', dayId);
    params.append('taskId', taskId);

    const xhr = new XMLHttpRequest();
    xhr.addEventListener('load', function() {onDayReload(dayId);});
    xhr.addEventListener('error', onNetworkError);
    xhr.open('DELETE', 'protected/slot?' + params.toString());
    xhr.send(params);
}

function onDeleteTaskFromDayButtonClicked() {
    const taskId = this.dataset.taskId;
    const dayId = this.dataset.dayId;

    if (confirm("Do you want to delete this task?")) {
        onDeleteTaskFromDayResponse(taskId, dayId);
        onDeleteTasksFromTable(taskId);
    }
}