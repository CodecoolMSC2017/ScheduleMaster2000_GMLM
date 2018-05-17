function onLogoutResponse() {
    if (this.status === OK) {
        setUnauthorized();
        clearMessages();
        showContents(['login-content']);
        clearForm(loginFormEl);
    } else {
        onOtherResponse(menuContentDivEl, this);
    }
}

function onLogoutButtonClicked() {
    const xhr = new XMLHttpRequest();
    xhr.addEventListener('load', onLogoutResponse);
    xhr.addEventListener('error', onNetworkError);
    xhr.open('POST', 'protected/logout');
    xhr.send();
}
