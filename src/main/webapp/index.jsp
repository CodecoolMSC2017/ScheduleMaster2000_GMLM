<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!doctype html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <c:url value="/style.css" var="styleUrl"/>
        <c:url value="/index.js" var="indexScriptUrl"/>
        <c:url value="/login.js" var="loginScriptUrl"/>
        <c:url value="/register.js" var="registerScriptUrl"/>
        <c:url value="/profile.js" var="profileScriptUrl"/>
        <c:url value="/logout.js" var="logoutScriptUrl"/>
        <c:url value="/tasks.js" var="tasksScriptUrl"/>
        <link rel="stylesheet" type="text/css" href="${styleUrl}">
        <script src="${indexScriptUrl}"></script>
        <script src="${loginScriptUrl}"></script>
        <script src="${registerScriptUrl}"></script>
        <script src="${profileScriptUrl}"></script>
        <script src="${logoutScriptUrl}"></script>
        <script src="${tasksScriptUrl}"></script>
        <title>Schedule Master 2000</title>
    </head>
<body>

    <div id="login-content" class="content">
        <h1>Login</h1>
        <form id="login-form" onsubmit="return false;">
            E-mail:<input type="text" name="email">
            Password:<input type="password" name="password">
            <button id="login-button">Login</button>
            <button id="register-button">Sign up</button>
        </form>
    </div>

    <div id="register-content" class="hidden content">
        <h1>Sign up</h1>
        <form id="register-form" onsubmit="return false;">
            Name:<input type="text" name="name">
            E-mail:<input type="text" name="email">
            Password:<input type="password" name="password">
            <button id="sign-up-button">Sign up</button>
            <button id="back-to-login">Back to login</button>
        </form>
    </div>

    <div id="menu-content" class="hidden content">
        <button id="profile-button">Profile</button>
        <button id="schedules-button">Schedules</button>
        <button id="tasks-button">Tasks</button>
        <p>Welcome <span id="user-name"></span></p>
        <button id="logout-button" onclick="onLogoutButtonClicked();">Log out</button>
    </div>

    <div id="tasks-content" class="hidden content">
        <h1>Tasks</h1>
        <table id="tasks">
            <thead>
            <tr>
                <th>ID</th>
                <th>Name</th>
            </tr>
            </thead>
            <tbody>
            </tbody>
        </table>
    </div>

</body>
</html>
