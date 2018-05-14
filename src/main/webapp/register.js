function onSignUpButtonClicked() {
    clearMessages();
    if (this.status === OK) {
        alert('Register Success');
    } else {
        onOtherResponse(registerContentDivEl, this);
    }

}



function onRegisterButtonClicked() {
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