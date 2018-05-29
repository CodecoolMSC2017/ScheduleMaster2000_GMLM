function onDeleteTaskResponse(taskId) {
    const params = new URLSearchParams();
    params.append('id', taskId);

    const xhr = new XMLHttpRequest();
    xhr.addEventListener('load', onTasksButtonClicked);
    xhr.addEventListener('error', onNetworkError);
    xhr.open('DELETE', 'protected/task?' + params.toString());
    xhr.send(params);
}

function onDeleteTaskButtonClicked() {
    const taskId = this.dataset.taskId;

    if (confirm("Do you want to delete this task?")) {
        onDeleteTaskResponse(taskId);
    } else {
        onTasksButtonClicked();
    }

}