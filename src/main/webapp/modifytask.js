function onModifyResponse() {
    removeAllChildren(infoContentDivEl);
    if (this.status === OK) {
        onCloseSpanClicked();
        onTasksButtonClicked();
    }
    const pEl = document.createElement('p');
    const response = JSON.parse(this.responseText);
    pEl.textContent = response.message;
    infoContentDivEl.append(pEl);
}

function onModifyLoad(id) {
    const taskTitleInputEl = document.getElementById('new-task-title');
    const taskContentInputEl = document.getElementById('task-content-area');

    const params = new URLSearchParams();

    params.append('title', taskTitleInputEl.value);
    params.append('content', taskContentInputEl.value);
    params.append('id', id);

    const xhr = new XMLHttpRequest();
    xhr.addEventListener('load', onModifyResponse);
    xhr.addEventListener('error', onNetworkError);
    xhr.open('PUT', 'protected/task?' + params.toString());
    xhr.send(params);
}

function onFormContentLoad(task) {
    const taskTitleInputEl = document.getElementById('new-task-title');
    const taskContentInputEl = document.getElementById('task-content-area');

    taskTitleInputEl.value = task.name;
    taskContentInputEl.value = task.content;
}

function onTaskContentResponse() {
    if (this.status === OK) {
        onFormContentLoad(JSON.parse(this.responseText));
    } else {
        onOtherResponse(infoContentDivEl, this);
    }
}

function onModifyButtonClicked() {
    removeAllChildren(infoContentDivEl);

    const taskId = this.id;

    const modifyTaskButton = document.getElementById('modify-task-button');
    modifyTaskButton.addEventListener('click', function loadHandler()  {
        onModifyLoad(taskId);
        modifyTaskButton.removeEventListener('click', loadHandler);
    });

    popupFormDivEl = document.getElementById('add-task-modal');

    const closeSpan = document.getElementById('close');
    closeSpan.addEventListener('click', onCloseSpanClicked);

    popupFormDivEl.style.display = "block";
    showContents(['modify-task-button', 'tasks-content', 'menu-content', 'add-task-button-content']);

    const params = new URLSearchParams();
    params.append('id', taskId);

    const xhr = new XMLHttpRequest();
    xhr.addEventListener('load', onTaskContentResponse);
    xhr.addEventListener('error', onNetworkError);
    xhr.open('GET', 'protected/task?' + params.toString());
    xhr.send(params);
}
