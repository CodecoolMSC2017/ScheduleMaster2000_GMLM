<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!doctype html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta name="google-signin-scope" content="profile email">
        <meta name="google-signin-client_id" content="221395632683-memhr17ad59qsc43ak5haaq3r0fj1h7e.apps.googleusercontent.com">

        <c:url value="/style.css" var="styleUrl"/>
        <c:url value="/index.js" var="indexScriptUrl"/>
        <c:url value="/login.js" var="loginScriptUrl"/>
        <c:url value="/register.js" var="registerScriptUrl"/>
        <c:url value="/profile.js" var="profileScriptUrl"/>
        <c:url value="/schedules.js" var="schedulesScriptUrl"/>
        <c:url value="/schedule.js" var="scheduleScriptUrl"/>
        <c:url value="/logout.js" var="logoutScriptUrl"/>
        <c:url value="/tasks.js" var="tasksScriptUrl"/>
        <c:url value="/users.js" var="usersScriptUrl"/>
        <c:url value="/task.js" var="taskScriptUrl"/>
        <c:url value="/day.js" var="dayScriptUrl"/>
        <c:url value="/addtask.js" var="addTaskScriptUrl"/>
        <c:url value="/modifytask.js" var="modifyTaskScriptUrl"/>
        <c:url value="/deleteTask.js" var="deleteTaskScriptUrl"/>
        <c:url value="/addDay.js" var="addDayScriptUrl"/>
        <c:url value="/deleteDay.js" var="deleteDayScriptUrl"/>
        <c:url value="/addschedule.js" var="addScheduleScriptUrl"/>
        <c:url value="/modifyday.js" var="modifyDayScriptUrl"/>
        <c:url value="/modifyschedule.js" var="modifyScheduleScriptUrl"/>
        <c:url value="/deleteSchedule.js" var="deleteScheduleScriptUrl"/>
        <c:url value="/addtasktoday.js" var="addtasktodayScriptUrl"/>
        <c:url value="/google-sign-in.js" var="googleSignInScriptUrl"/>
        <c:url value="/google-sign-out.js" var="googleSignOutScriptUrl"/>

        <link rel="stylesheet" type="text/css" href="${styleUrl}">

        <script src="//ajax.googleapis.com/ajax/libs/jquery/2.1.1/jquery.min.js"></script>
        <script src="https://apis.google.com/js/platform.js?onload=renderButton" async defer></script>
        <script src="https://apis.google.com/js/platform.js" async defer></script>
        <script src="${indexScriptUrl}"></script>
        <script src="${loginScriptUrl}"></script>
        <script src="${registerScriptUrl}"></script>
        <script src="${profileScriptUrl}"></script>
        <script src="${schedulesScriptUrl}"></script>
        <script src="${scheduleScriptUrl}"></script>
        <script src="${logoutScriptUrl}"></script>
        <script src="${tasksScriptUrl}"></script>
        <script src="${usersScriptUrl}"></script>
        <script src="${taskScriptUrl}"></script>
        <script src="${dayScriptUrl}"></script>
        <script src="${addTaskScriptUrl}"></script>
        <script src="${modifyTaskScriptUrl}"></script>
        <script src="${deleteTaskScriptUrl}"></script>
        <script src="${addDayScriptUrl}"></script>
        <script src="${deleteDayScriptUrl}"></script>
        <script src="${addScheduleScriptUrl}"></script>
        <script src="${modifyDayScriptUrl}"></script>
        <script src="${deleteScheduleScriptUrl}"></script>
        <script src="${modifyScheduleScriptUrl}"></script>
        <script src="${addtasktodayScriptUrl}"></script>
        <script src="${googleSignInScriptUrl}"></script>
        <script src="${googleSignOutScriptUrl}"></script>

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
            <div id="google-signin2"></div>
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
            <li><button id="users-button">Users</button></li>
            <li><button id="schedules-button" onclick="onSchedulesButtonClicked();">Schedules</button></li>
            <li><button id="tasks-button">Tasks</button></li>
            <li><button id="logout-button" onclick="onLogoutButtonClicked();">Log out</button></li>
            <li><p id = "welcome-string">Welcome <span id="user-name"></span>!</p></li>
            <li>
                <div id="add-schedule-button-content" class="hidden content">
                    <button id="add-new-schedule" onclick="onAddScheduleButtonClicked();">Add new schedule</button>
                </div>
            </li>
            <li>
                <div id="add-day-button-content" class="hidden content">
                    <button id="add-new-day" onclick="onAddDayButtonClicked();">Add new day</button>
                </div>
            </li>
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
                <h2>Task</h2>
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

    <div id="add-day-modal" class="modal">
        <div class="modal-content">
            <div class="modal-header">
                <span id="close-day-modal" class="close">&times;</span>
                <h2>Day</h2>
            </div>
            <div class="modal-body">
                <form id="add-day-form">
                    Day title:<br>
                    <input type="text" id="new-day-title"><br>
                </form>
                <div id="day-info-content"></div>
            </div>
            <div class="modal-footer">
                <button id="save-day-button" class="hidden content">Save</button>
                <button id="modify-day-button" class="hidden content">Modify</button>
            </div>
        </div>
    </div>

    <div id="add-schedule-modal" class="modal">
        <div class="modal-content">
            <div class="modal-header">
                <span id="close-schedule-modal" class="close">&times;</span>
                <h2>Schedule</h2>
            </div>
            <div class="modal-body">
                <form id="add-schedule-form">
                    Schedule name:<br>
                    <input type="text" id="new-schedule-name"><br>
                </form>
                <div id="schedule-info-content"></div>
            </div>
            <div class="modal-footer">
                <button id="save-schedule-button" class="hidden content">Save</button>
                <button id="modify-schedule-button" class="hidden content">Modify</button>
            </div>
        </div>
    </div>

    <div id="addtask-to-day-modal" class="modal">
        <div class="modal-content">
            <div class="modal-header">
                <span id="close-addtask-to-day-modal" class="close">&times;</span>
                <h2>Add task to day</h2>
            </div>
            <div class="modal-body">
                <form id="addtask-to-day-form">
                    Choose task:
                    <select id="task">
                        <option selected="selected">Choose</option>
                    </select><br>
                    <br>Choose start hour:
                    <select id="start-hour">
                        <option selected="selected">Choose</option>
                    </select><br>
                    <br>Choose end hour:
                    <select id="end-hour">
                        <option selected="selected">Choose</option>
                    </select><br>
                </form>
                <div id="addtask-to-day-info-content"></div>
            </div>
            <div class="modal-footer">
                <button id="addtask-button">Add!</button>
            </div>
        </div>
    </div>

    <div id="users-content" class="hidden content">
        <h1>Users</h1><br>
        <table id="users">
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Name</th>
                    <th>Email</th>
                    <th>Password</th>
                    <th>Role</th>
                </tr>
            </thead>
            <tbody id="users-tbody">
            </tbody>
        </table>
    </div>

    <div id="schedules-content" class="hidden content">
        <h1>Schedules</h1><br>
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
                    <th>Add task</th>
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
