function onDeleteTaskResponse(taskId) {
    const params = new URLSearchParams();
    params.append('id', taskId);

    const xhr = new XMLHttpRequest();
    xhr.addEventListener('load', onTasksButtonClicked);
    xhr.addEventListener('error', onNetworkError);
    xhr.open('DELETE', 'protected/task?' + params.toString());
    xhr.send(params);
}

function onDeleteTaskInTable(taskId) {
    const deletedTaskTr = document.getElementById(taskId);
    deletedTaskTr.parentNode.removeChild(deletedTaskTr);
}

function onDeleteTaskButtonClicked() {
    const taskId = this.dataset.taskId;

    if (confirm("Do you want to delete this task?")) {
        onDeleteTaskResponse(taskId);
        onDeleteTaskInTable(taskId);
    } else {
        onTasksButtonClicked();
    }
}