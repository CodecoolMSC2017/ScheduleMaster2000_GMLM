function onSignIn(googleUser) {
    var profile = googleUser.getBasicProfile();
    //console.log('ID: ' + profile.getId()); // Do not send to your backend! Use an ID token instead.
    //console.log('Name: ' + profile.getName());
    //console.log('Image URL: ' + profile.getImageUrl());
    //console.log('Email: ' + profile.getEmail()); // This is null if the 'email' scope is not present.

    const email = profile.getEmail();
    const fullName = profile.getName();

    const params = new URLSearchParams();
    params.append('name', fullName);
    params.append('email', email);

    const xhr = new XMLHttpRequest();
    xhr.addEventListener('load', onLoginResponse);
    xhr.addEventListener('error', onNetworkError);
    xhr.open('POST', 'googleLogin');
    xhr.send(params);

}

function onSuccess(googleUser) {
    console.log('Logged in as: ' + googleUser.getBasicProfile().getName());
    onSignIn(googleUser);
}

function onFailure(error) {
    console.log(error);
}

function renderButton() {
    gapi.signin2.render('google-signin2', {
        'scope': 'profile email',
        'width': 274,
        'height': 50,
        'longtitle': true,
        'theme': 'dark',
        'onsuccess': onSuccess,
        'onfailure': onFailure
    });
}
