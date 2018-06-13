const OK = 200;
const BAD_REQUEST = 400;
const UNAUTHORIZED = 401;
const NOT_FOUND = 404;
const INTERNAL_SERVER_ERROR = 500;

let loginContentDivEl;
let loginFormEl;
let registerContentDivEl;
let registerFormEl;
let menuContentDivEl;
let schedulesContentDivEl;
let scheduleContentDivEl;
let guestScheduleContentDivEl;
let tasksContentDivEl;
let taskContentDivEl;
let tasksOfDayContentDivEl;
let guestTasksOfDayContentDivEl;
let errorMessageContentDivEl;
let infoContentDivEl;
let dayInfoContentDivEl;
let scheduleInfoContentDivId;
let addTaskToDayInfoContentDivEl;

function newInfo(targetEl, message) {
    newMessage(targetEl, 'info', message);
}

function newError(targetEl, message) {
    newMessage(targetEl, 'error', message);
}

function newMessage(targetEl, cssClass, message) {
    clearMessages();

    const pEl = document.createElement('p');
    pEl.classList.add('message');
    pEl.classList.add(cssClass);
    pEl.textContent = message;

    targetEl.appendChild(pEl);
}

function clearForm(form) {
    form.reset();
}

function clearMessages() {
    const messageEls = document.getElementsByClassName('message');
    for (let i = 0; i < messageEls.length; i++) {
        const messageEl = messageEls[i];
        messageEl.remove();
    }
}

function showContents(ids) {
    const contentEls = document.getElementsByClassName('content');
    for (let i = 0; i < contentEls.length; i++) {
        const contentEl = contentEls[i];
        if (ids.includes(contentEl.id)) {
            contentEl.classList.remove('hidden');
        } else {
            contentEl.classList.add('hidden');
        }
    }
}

function removeAllChildren(el) {
    while (el.firstChild) {
        el.removeChild(el.firstChild);
    }
}

function onNetworkError(response) {
    document.body.remove();
    const bodyEl = document.createElement('body');
    document.appendChild(bodyEl);
    newError(bodyEl, 'Network error, please try reloading the page');
}

function onOtherResponse(targetEl, xhr) {
    if (xhr.status === NOT_FOUND) {
        newError(targetEl, 'Not found');
        console.error(xhr);
    } else {
        const json = JSON.parse(xhr.responseText);
        if (xhr.status === INTERNAL_SERVER_ERROR) {
            newError(targetEl, `Server error: ${json.message}`);
        } else if (xhr.status === UNAUTHORIZED || xhr.status === BAD_REQUEST) {
            newError(targetEl, json.message);
        } else {
            newError(targetEl, `Unknown error: ${json.message}`);
        }
    }
}

function hasAuthorization() {
    return localStorage.getItem('user') !== null;
}

function setAuthorization(user) {
    return localStorage.setItem('user', JSON.stringify(user));
}

function getAuthorization() {
    return JSON.parse(localStorage.getItem('user'));
}

function setUnauthorized() {
    return localStorage.removeItem('user');
}

function checkRequest() {
    const pathname = window.location.pathname;
    if(pathname.includes("guest")) {
        const params = window.location.search;
        const reqQuery = "guestschedule"+ params.toString();
        showContents(['guest-schedule-content']);
        onGuestScheduleRequest(reqQuery);
    }
}

function onLoad() {

    loginContentDivEl = document.getElementById('login-content');
    loginFormEl = document.getElementById('login-form');
    registerContentDivEl = document.getElementById('register-content');
    registerFormEl = document.getElementById('register-form');
    menuContentDivEl = document.getElementById('menu-content');
    schedulesContentDivEl = document.getElementById('schedules-content');
    scheduleContentDivEl = document.getElementById('schedule-content');
    guestScheduleContentDivEl = document.getElementById('guest-schedule-content');
    tasksContentDivEl = document.getElementById('tasks-content');
    taskContentDivEl = document.getElementById('task-content');
    tasksOfDayContentDivEl = document.getElementById('tasks-of-day-content');
    guestTasksOfDayContentDivEl = document.getElementById('guest-tasks-of-day-content');
    errorMessageContentDivEl = document.getElementById('error-message-content');
    infoContentDivEl = document.getElementById('info-content');
    dayInfoContentDivEl = document.getElementById('day-info-content');
    scheduleInfoContentDivId = document.getElementById('schedule-info-content');
    addTaskToDayInfoContentDivEl = document.getElementById('addtask-to-day-info-content');

    const loginButtonEl = document.getElementById('login-button');
    loginButtonEl.addEventListener('click', onLoginButtonClicked);

    const registerButtonEl = document.getElementById('register-button')
    registerButtonEl.addEventListener('click', onRegisterButtonClicked);

    const signUpButtonEl = document.getElementById('sign-up-button');
    signUpButtonEl.addEventListener('click', onSignUpButtonClicked);

    const backToLoginButtonEl = document.getElementById('back-to-login');
    backToLoginButtonEl.addEventListener('click', onBackToLoginButtonClicked);

    const tasksButtonEl = document.getElementById('tasks-button');
    tasksButtonEl.addEventListener('click', onTasksButtonClicked);

    const usersButtonEl = document.getElementById('users-button');
    usersButtonEl.addEventListener('click', onUsersButtonClicked);

    const addNewTaskButtonEl = document.getElementById('add-new-task');
    addNewTaskButtonEl.addEventListener('click', onAddTaskButtonClicked);

    if (hasAuthorization()) {
        onProfileLoad(getAuthorization());
    }
}


document.addEventListener('DOMContentLoaded', onLoad);

