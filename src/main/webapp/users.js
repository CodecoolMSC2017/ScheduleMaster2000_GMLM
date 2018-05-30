let usersTableEl;
let usersTableBodyEl;
function appendUser(user) {
    const idTdEl = document.createElement('td');
    idTdEl.textContent = user.id;

    const nameTdEl = document.createElement('td');
    nameTdEl.textContent = user.name;

    const emailTdEl = document.createElement('td');
    emailTdEl.textContent = user.email;

    const pwTdEl = document.createElement('td');
    pwTdEl.textContent = user.password;

    const roleTdEl = document.createElement('td');

    if (user.admin) {
        roleTdEl.textContent = 'Admin';
    } else {
        roleTdEl.textContent = 'Regular';
    }

    const trEl = document.createElement('tr');
    trEl.append(idTdEl);
    trEl.append(nameTdEl);
    trEl.append(emailTdEl);
    trEl.append(pwTdEl);
    trEl.append(roleTdEl);

    usersTableBodyEl.append(trEl);
}

function appendUsers(users) {
    removeAllChildren(usersTableBodyEl);

    for (let i = 0; i < users.length; i++) {
        const user = users[i];
        appendUser(user);
    }
}


function onUsersLoad(users) {
    usersTableEl = document.getElementById('users');
    usersTableBodyEl = document.getElementById('users-tbody');

    appendUsers(users);
}

function onUsersResponse() {
    clearMessages();

    if (this.status === OK) {
        showContents(['menu-content', 'users-content', ]);
        onUsersLoad(JSON.parse(this.responseText));
    } else {
        showContents(['menu-content', 'error-message-content']);
        onOtherResponse(errorMessageContentDivEl, this);
    }
}

function onUsersButtonClicked() {
    const xhr = new XMLHttpRequest();
    xhr.addEventListener('load', onUsersResponse);
    xhr.addEventListener('error', onNetworkError);
    xhr.open('GET', 'protected/users');
    xhr.send();
}
