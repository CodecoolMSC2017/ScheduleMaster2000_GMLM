let popupTaskFormDivEl;
let addTaskForm;

function onAddTaskResponse() {
    removeAllChildren(infoContentDivEl);
    if(this.status === OK) {
        addTaskForm.reset();
        onCloseTaskSpanClicked();
        onTasksButtonClicked();
    }
    const pEl = document.createElement('p');
    const response = JSON.parse(this.responseText);
    pEl.textContent = response.message;
    infoContentDivEl.append(pEl);
}

function onSaveTaskButtonClicked() {
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


function onCloseTaskSpanClicked() {
    onTasksButtonClicked();
    popupTaskFormDivEl.style.display = "none"; 
}

function onAddTaskButtonClicked() {
    popupTaskFormDivEl = document.getElementById('add-task-modal');

    addTaskForm = document.getElementById('add-task-form');
    addTaskForm.reset();
    
    const addTaskButtonEl = document.getElementById('save-task-button');
    addTaskButtonEl.addEventListener('click', onSaveTaskButtonClicked);

    const closeSpan = document.getElementById('close');
    closeSpan.addEventListener('click', onCloseTaskSpanClicked);

    removeAllChildren(infoContentDivEl);
    
    popupTaskFormDivEl.style.display = "block";
    showContents(['save-task-button', 'tasks-content', 'menu-content', 'add-task-button-content']);
    onTasksButtonClicked();
}
