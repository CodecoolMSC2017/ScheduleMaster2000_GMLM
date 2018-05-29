let addDayForm;

function onAddDayResponse() {
    removeAllChildren(infoContentDivEl);
    if(this.status === OK) {
        addDayForm.reset();
        onCloseSpanClicked();
        onScheduleClicked();
    }
    const pEl = document.createElement('p');
    const response = JSON.parse(this.responseText);
    pEl.textContent = response.message;
    infoContentDivEl.append(pEl);
}

function onSaveButtonClicked() {
    const dayTitleInputEl = document.getElementById('new-day-title');
    
    dayTitle = dayTitleInputEl.value;
    
    const params = new URLSearchParams();
    params.append('title', dayTitle);

    const xhr = new XMLHttpRequest();
    xhr.addEventListener('load', onAddDayResponse);
    xhr.addEventListener('error', onNetworkError);
    xhr.open('POST', 'protected/day');
    xhr.send(params);

}


function onCloseSpanClicked() {
    popupFormDivEl.style.display = "none";
}

function onAddDayButtonClicked() {
    addDayForm = document.getElementById('add-day-form');
    addDayForm.reset();
    const addDayButtonEl = document.getElementById('save-day-button');
    addDayButtonEl.addEventListener('click', onSaveButtonClicked);

    popupFormDivEl = document.getElementById('add-day-modal');

    const closeSpan = document.getElementById('close');
    closeSpan.addEventListener('click', onCloseSpanClicked);

    removeAllChildren(infoContentDivEl);
    popupFormDivEl.style.display = "block";
    showContents(['save-day-button', 'schedule-content', 'menu-content', 'add-day-button-clicked']);
}
