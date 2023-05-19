"use strict";

const url = "http://localhost:8080/api/v1/user"

async function getAdminPage() {
    let page = await fetch(url);
    if (page.ok) {
        let listAllUser = await page.json();
        loadTableData(listAllUser);
    } else {
        alert(`Error, ${page.status}`)
    }
}

const pills = document.querySelectorAll('.pill');
const pillsContent = document.querySelectorAll('.pillContent');
pills.forEach((clickedPill) => {
    clickedPill.addEventListener('click', async () => {
        pills.forEach((pill) => {
            pill.classList.remove('active');
        });
        clickedPill.classList.add('active');
        let tabId = clickedPill.getAttribute('id');
        await activePillContent(tabId);
    });
});

async function activePillContent(tabId) {
    pillsContent.forEach((clickedPillContent) => {
        clickedPillContent.classList.contains(tabId) ?
            clickedPillContent.classList.add('active') :
            clickedPillContent.classList.remove('active');
    })
}

async function getMyUser() {
    let res = await fetch('/api/auth');
    let resUser = await res.json();
    userNavbarDetails(resUser);
}

window.addEventListener('DOMContentLoaded', getMyUser);

function userNavbarDetails(resUser) {
    let userList = document.getElementById('myUserDetails');
    let roles = ''
    for (let role of resUser.roles) {
        roles += role.name + ' '
    }
    userList.insertAdjacentHTML('beforeend', `
        <b> ${resUser.username} </b> with roles: <a>${roles} </a>`);
}

function loadTableData(listAllUser) {
    let tableBody = document.getElementById('tbody');
    let dataHtml = '';
    for (let user of listAllUser) {
        let roles = [];
        for (let role of user.roles) {
            roles.push(" " + role.role)
        }
        dataHtml +=
            `<tr>
    <td>${user.id}</td>
    <td>${user.name}</td>
    <td>${user.username}</td>
    <td>${user.years}</td>
    <td>${user.password}</td>
    <td>${roles}</td>
    <td>
        <button class="btn btn-success" data-bs-toogle="modal"
        data-bs-target="#editModal"
        onclick="editModalData(${user.id})">Edit</button>
    </td>
        <td>
        <button class="btn btn-danger" data-bs-toogle="modal"
        data-bs-target="#deleteModal"
        onclick="deleteModalData(${user.id})">Delete</button>
    </td>
</tr>`
    }
    tableBody.innerHTML = dataHtml;
}

getAdminPage();

window.addEventListener('DOMContentLoaded', loadUserTable);

async function loadUserTable() {
    let tableBody = document.getElementById('tableUser');
    let page = await fetch("/api/auth");
    let currentUser;
    if (page.ok) {
        currentUser = await page.json();
    } else {
        alert(`Error, ${page.status}`)
    }
    let dataHtml = '';
    let roles = [];
    for (let role of currentUser.roles) {
        roles.push(" " + role.role)
    }
    dataHtml +=
        `<tr>
    <td>${currentUser.id}</td>
    <td>${currentUser.name}</td>
    <td>${currentUser.username}</td>
    <td>${currentUser.years}</td>
    <td>${currentUser.password}</td>
    <td>${roles}</td>
</tr>`
    tableBody.innerHTML = dataHtml;
}

const tabs = document.querySelectorAll('.taba');
const tabsContent = document.querySelectorAll('.tabaContent');
tabs.forEach((clickedTab) => {
    clickedTab.addEventListener('click', async () => {
        tabs.forEach((tab) => {
            tab.classList.remove('active');
        });
        clickedTab.classList.add('active');
        let tabaId = clickedTab.getAttribute('id');
        await activeTabContent(tabaId);
    });
});

async function activeTabContent(tabaId) {
    tabsContent.forEach((clickedTabContent) => {
        clickedTabContent.classList.contains(tabaId) ?
            clickedTabContent.classList.add('active') :
            clickedTabContent.classList.remove('active');

    })
}

const form_new = document.getElementById('formForNewUser');

async function newUser() {
    form_new.addEventListener('submit', addNewUser);
}

async function addNewUser(event) {
    event.preventDefault();
    let listOfRole = [];
    for (let i = 0; i < form_new.registrationRole.options.length; i++) {
        if (form_new.registrationRole.options[i].selected) {
            listOfRole.push({id: form_new.registrationRole.options[i].value,
                role: form_new.registrationRole.options[i].text});
        }
    }
    let modal = $('#nav-profile')
    let user = {
        name: modal.find('#registrationName').val(),
        username: modal.find('#registrationUsername').val(),
        years: modal.find('#registrationAge').val(),
        password: modal.find('#registrationPassword').val(),
        roles: listOfRole
    };

    let method = {
        method: 'POST',
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(user)
    }
    await fetch(url, method).then(() => {
        form_new.reset();
        getAdminPage();
        activeTabContent('nav-home-tab');
        let activateTab = document.getElementById('nav-home-tab');
        let activateTab2 = document.getElementById('nav-home');
        activateTab.classList.add('active');
        activateTab2.classList.add('show');
        let deactivateTab = document.getElementById('nav-profile-tab');
        deactivateTab.classList.remove('active');
    });
}

const form_edit = document.getElementById('formForEditing');
const id_edit = document.getElementById('id_edit');
const name_edit = document.getElementById('name_edit');
const username_edit = document.getElementById('username_edit');
const age_edit = document.getElementById('age_edit');
const password_edit = document.getElementById('password_edit');



async function editModalData(id) {
    $('#editModal').modal('show');
    const urlDataEd = 'http://localhost:8080/api/v1/user/' + id;
    let usersPageEd = await fetch(urlDataEd);
    if (usersPageEd.ok) {
        await usersPageEd.json().then(user => {
            id_edit.value = `${user.id}`;
            name_edit.value = `${user.name}`;
            username_edit.value = `${user.username}`;
            age_edit.value = `${user.years}`;
            password_edit.value = `${user.password}`;
        })
    } else {
        alert(`Error, ${usersPageEd.status}`)
    }
}

async function editUser() {
    let urlEdit = 'http://localhost:8080/api/v1/user/' + id_edit.value;
    let listOfRole = [];
    for (let i = 0; i < form_edit.role_edit.options.length; i++) {
        if (form_edit.role_edit.options[i].selected) {
            listOfRole.push({id: form_edit.role_edit.options[i].value,
                name: form_edit.role_edit.options[i].text});
        }
    }
    let modal = $('#editModal')
    let user = {
        id: modal.find('#id_edit').val(),
        name: modal.find('#name_edit').val(),
        username: modal.find('#username_edit').val(),
        years: modal.find('#age_edit').val(),
        password: modal.find('#password_edit').val(),
        roles: listOfRole
    };
    let method = {
        method: 'PATCH',
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(user)
    }
    await fetch(urlEdit, method).then(() => {
        $('#editCloseBtn').click();
        getAdminPage();
    })
}

const id_delete = document.getElementById('delete_id');
const name_delete = document.getElementById(`delete_name`);
const username_delete = document.getElementById('delete_username');
const age_delete = document.getElementById('delete_age');
const password_delete = document.getElementById('delete_password');


async function deleteModalData(id) {
    $('#deleteModal').modal('show');
    const urlForDel = 'http://localhost:8080/api/v1/user/' + id;
    let usersPageDel = await fetch(urlForDel);
    if (usersPageDel.ok) {
        await usersPageDel.json().then(user => {
            id_delete.value = `${user.id}`;
            name_delete.value = `${user.name}`;
            username_delete.value = `${user.username}`;
            age_delete.value = `${user.years}`;
            password_delete.value = `${user.password}`;
        })
    } else {
        alert(`Error, ${usersPageDel.status}`)
    }
}

async function deleteUser() {
    let urlDel = 'http://localhost:8080/api/v1/user/' + id_delete.value;
    let modal = $('#deleteModal')
    let user = {
        id: modal.find('#delete_id').val(),
        name: modal.find('#delete_name').val(),
        username: modal.find('#delete_username').val(),
        years: modal.find('#delete_age').val(),
        password: modal.find('#delete_password').val()
    };
    let method = {
        method: 'DELETE',
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(user)
    }
    await fetch(urlDel, method).then(() => {
        $('#deleteCloseBtn').click();
        getAdminPage();
    })
}


