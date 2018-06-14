let usersButtonEl;
function onProfileLoad(user) {
    usersButtonEl = document.getElementById('users-button');
    clearMessages();

    const userNameSpanEl = document.getElementById('user-name');
    userNameSpanEl.textContent = user.name;

    if (user.admin) {
        showContents(['menu-content']);
        usersButtonEl.style.display = "inline";
        onUsersButtonClicked();
    } else {
        showContents(['menu-content']);
        usersButtonEl.style.display = "none";
        onSchedulesButtonClicked();
    }   
}
