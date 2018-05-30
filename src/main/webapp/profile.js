let usersButtonEl;
function onProfileLoad(user) {
    usersButtonEl = document.getElementById('users-button');

    clearMessages();

    if (user.admin) {
        showContents(['menu-content']);
        usersButtonEl.style.display = "inline";
    } else {
        showContents(['menu-content']);
        usersButtonEl.style.display = "none";
    }
    const userNameSpanEl = document.getElementById('user-name');
    userNameSpanEl.textContent = user.name;

    onSchedulesButtonClicked();
}
