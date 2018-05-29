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
        <c:url value="/schedules.js" var="schedulesScriptUrl"/>
        <c:url value="/schedule.js" var="scheduleScriptUrl"/>
        <c:url value="/logout.js" var="logoutScriptUrl"/>
        <c:url value="/tasks.js" var="tasksScriptUrl"/>
        <c:url value="/task.js" var="taskScriptUrl"/>
        <c:url value="/day.js" var="dayScriptUrl"/>
        <c:url value="/addtask.js" var="addTaskScriptUrl"/>
        <c:url value="/modifytask.js" var="modifyTaskScriptUrl"/>
        <c:url value="/deleteTask.js" var="deleteTaskScriptUrl"/>
        <link rel="stylesheet" type="text/css" href="${styleUrl}">
        <script src="${indexScriptUrl}"></script>
        <script src="${loginScriptUrl}"></script>
        <script src="${registerScriptUrl}"></script>
        <script src="${profileScriptUrl}"></script>
        <script src="${schedulesScriptUrl}"></script>
        <script src="${scheduleScriptUrl}"></script>
        <script src="${logoutScriptUrl}"></script>
        <script src="${tasksScriptUrl}"></script>
        <script src="${taskScriptUrl}"></script>
        <script src="${dayScriptUrl}"></script>
        <script src="${addTaskScriptUrl}"></script>
        <script src="${modifyTaskScriptUrl}"></script>
        <script src="${deleteTaskScriptUrl}"></script>
        <title>Schedule Master 2000</title>
    </head>
<body>

    <div id="login-content" class="content">
        <h1>Login</h1>
        <form id="login-form" onsubmit="return false;">
            <input type="text" name="email" placeholder="E-mail">
            <input type="password" name="password" placeholder="Password">
            <input type="submit" id="login-button" name="login" class="login login-submit" value="login">
            <input type="submit" id="register-button" name="register" class="register register-submit" value="sign up">
            <!-- <button id="login-button">Login</button> -->
            <!--<button id="register-button">Sign up</button> -->
        </form>
    </div>

    <div id="register-content" class="hidden content">
        <h1>Sign up</h1>
        <form id="register-form" onsubmit="return false;">
            <input type="text" name="name" placeholder="Name">
            <input type="text" name="email" placeholder="E-mail">
            <input type="password" name="password" placeholder="Password">
            <input type="submit" id="sign-up-button" name="sign-up" class="sign-up sign-up-submit" value="sign up">
            <input type="submit" id="back-to-login" name="back-to-login" class="back-to-login back-to-login-submit" value="back to login">
            <!-- <button id="sign-up-button">Sign up</button> -->
            <!-- <button id="back-to-login">Back to login</button> -->
        </form>
    </div>

    <div id="menu-content" class="hidden content">
        <ul>
            <li><button id="schedules-button" onclick="onSchedulesButtonClicked();">Schedules</button></li>
            <li><button id="tasks-button">Tasks</button></li>
            <li><button id="logout-button" onclick="onLogoutButtonClicked();">Log out</button></li>
            <li><p id = "welcome-string">Welcome <span id="user-name"></span>!</p></li>
            <li>
                <div id="add-task-button-content" class="hidden content">
                    <button id="add-new-task">Add new task</button>
                </div>
            </li>
        </ul>
    </div>

    <div id="add-task-modal" class="modal">
        <div class="modal-content">
            <div class="modal-header">
                <span id="close" class="close">&times;</span>
                <h2>Add new task</h2>
            </div>
            <div class="modal-body">
                <form id="add-task-form">
                    Task title:<br>
                    <input type="text" id="new-task-title"><br>
                    Task content:<br>
                    <textarea id="task-content-area" rows="4" cols="50"></textarea>
                </form>
                <div id="info-content"></div>
            </div>
            <div class="modal-footer">
                <button id="save-task-button" class="hidden content">Save</button>
                <button id="modify-task-button" class="hidden content">Modify</button>
            </div>
        </div>
    </div>


    <div id="schedules-content" class="hidden content">
        <h1>Schedules</h1>
        <table id="schedules">
            <thead>
                <tr>
                    <th>Name</th>
                    <th>Modify</th>
                    <th>Delete</th>
                </tr>
            </thead>
            <tbody id="schedules-tbody">
            </tbody>
        </table>
    </div>

    <div id="schedule-content" class="hidden content">
        <h1>Days</h1>
        <table id="schedule">
            <thead>
                <tr>
                    <th>Title</th>
                    <th>Modify</th>
                    <th>Delete</th>
                </tr>
            </thead>
            <tbody id="schedule-tbody">
            </tbody>
        </table>
    </div>

    <div id="tasks-content" class="hidden content">
        <h1>Tasks</h1>
        <table id="tasks">
            <thead>
            <tr>
                <th>ID</th>
                <th>Name</th>
                <th>Modify</th>
                <th>Delete</th>
            </tr>
            </thead>
            <tbody id="tasks-content-table">
            </tbody>
        </table>
    </div>

    <div id="task-content" class="hidden content"></div>

    <div id="tasks-of-day-content" class="hidden content">
        <h1>Tasks</h1>
        <table id="tasks-of-day">
            <thead>
            <tr>
                <th>Name</th>
                <th>Content</th>
                <th>Start hour</th>
                <th>End hour</th>
                <th>Modify</th>
                <th>Delete</th>
            </tr>
            </thead>
            <tbody id="tasks-of-day-tbody">
            </tbody>
        </table>
    </div>

    <div id="error-message-content" class="hidden content"></div>

</body>
</html>
