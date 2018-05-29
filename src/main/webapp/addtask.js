let popupFormDivEl;
let addTaskForm;

function onAddTaskResponse() {
    removeAllChildren(infoContentDivEl);
    if(this.status === OK) {
        addTaskForm.reset();
        onCloseSpanClicked();
        onTasksButtonClicked();
    }
    const pEl = document.createElement('p');
    const response = JSON.parse(this.responseText);
    pEl.textContent = response.message;
    infoContentDivEl.append(pEl);
}

function onSaveButtonClicked() {
    const taskTitleInputEl = document.getElementById('new-task-title');
    const taskContentInputEl = document.getElementById('task-content-area');

    taskTitle = taskTitleInputEl.value;
    taskContent = taskContentInputEl.value;

    const params = new URLSearchParams();
    params.append('title', taskTitle);
    params.append('content', taskContent);

    const xhr = new XMLHttpRequest();
    xhr.addEventListener('load', onAddTaskResponse);
    xhr.addEventListener('error', onNetworkError);
    xhr.open('POST', 'protected/task');
    xhr.send(params);

}


function onCloseSpanClicked() {
    popupFormDivEl.style.display = "none";
}

function onAddTaskButtonClicked() {
    addTaskForm = document.getElementById('add-task-form');
    addTaskForm.reset();
    const addTaskButtonEl = document.getElementById('save-task-button');
    addTaskButtonEl.addEventListener('click', onSaveButtonClicked);

    popupFormDivEl = document.getElementById('add-task-modal');

    const closeSpan = document.getElementById('close');
    closeSpan.addEventListener('click', onCloseSpanClicked);

    removeAllChildren(infoContentDivEl);
    popupFormDivEl.style.display = "block";
    showContents(['save-task-button', 'tasks-content', 'menu-content', 'add-task-button-content']);
}
