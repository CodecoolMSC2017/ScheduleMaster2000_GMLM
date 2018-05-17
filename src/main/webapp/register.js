function onRegisterResponse() {
    clearMessages();
    if (this.status === OK) {
        const text = JSON.parse(this.responseText);
        clearForm(registerFormEl);
        alert(text.message);
        showContents('login-content');
    } else {
        onOtherResponse(registerContentDivEl, this);
    }
}

function onSignUpButtonClicked() {
    const registerFormEl = document.forms['register-form'];

    const nameInputEl = registerFormEl.querySelector('input[name="name"]');
    const emailInputEl = registerFormEl.querySelector('input[name="email"]');
    const passwordInputEl = registerFormEl.querySelector('input[name="password"]');

    const name = nameInputEl.value;
    const email = emailInputEl.value;
    const password = passwordInputEl.value;

    const params = new URLSearchParams();
    params.append('name', name);
    params.append('email', email);
    params.append('password', password);

    const xhr = new XMLHttpRequest();
    xhr.addEventListener('load', onRegisterResponse);
    xhr.addEventListener('error', onNetworkError);
    xhr.open('POST', 'register');
    xhr.send(params);
}

function onRegisterButtonClicked() {
    clearMessages();
    showContents('register-content');
    clearForm(registerFormEl);
}

function onBackToLoginButtonClicked() {
    clearMessages();
    showContents('login-content');
    clearForm(loginFormEl);
}
