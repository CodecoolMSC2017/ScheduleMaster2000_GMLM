function onProfileLoad(user) {
    clearMessages();
    showContents(['menu-content']);

    const userNameSpanEl = document.getElementById('user-name');
    userNameSpanEl.textContent = user.name;

    onSchedulesButtonClicked();
}