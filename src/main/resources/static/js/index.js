
function getAll() {
    fetch('http://localhost:8080/admin/getAll')
        .then(response => response.json())
        // .then(s => console.log(s))
        .then(res => fillTable(res));
}

function newUser() {
    let form = document.forms.create;

    let roles = [];
    for (let i = 0; i < form.role.options.length; i++) {
        if (form.role.options[i].selected) roles.push({
            roleId: form.role.options[i].value,
            name: form.role.options[i].text
        });
    }
    fetch('http://localhost:8080/admin/new', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            name: document.getElementById("name").value,
            surname: document.getElementById("surname").value,
            password: document.getElementById("password").value,
            role: roles
        })
    }).then(() => {
        form.reset();
        document.getElementById("tableButton").click();
        getAll();
    });
}

function editUser(id) {
    let form = document.getElementById("edit" + id);
    let options = document.getElementById("editRoles" + id);

    let roles = [];
    for (let i = 0; i < options.options.length; i++) {
        if (options.options[i].selected) roles.push({
            roleId: options.options[i].value,
            name: options.options[i].text
        });
    }
    console.log(JSON.stringify(roles));
    fetch('http://localhost:8080/admin/edit/' + id, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            name: document.getElementById("editName" + id).value,
            surname: document.getElementById("editSurname" + id).value,
            password: document.getElementById("editPassword" + id).value,
            role: roles
        })
    }).then(() => {
        form.reset();
        document.getElementById("closeEdit" + id).click();
        getAll();
    });
}

function deleteUser(id) {
    let form = document.getElementById("delete" + id);
    let options = document.getElementById("delRoles" + id);

    let roles = [];
    for (let i = 0; i < options.options.length; i++) {
        if (options.options[i].selected) roles.push({
            roleId: options.options[i].value,
            name: options.options[i].text
        });
    }
    fetch('http://localhost:8080/admin/delete/' + id, {
        method: 'DELETE',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            name: document.getElementById("delName" + id).value,
            surname: document.getElementById("delSurname" + id).value,
            password: document.getElementById("delPassword" + id).value,
            role: roles
        })
    }).then(() => {
        form.reset();
        document.getElementById("delClose" + id).click();
        getAll();
    });
}

function fillTable(data) {
    let user_table = document.getElementById("users");
    if (user_table.hasChildNodes()) {
        user_table.innerHTML = "";
    }
    for (let i = 0; i < data.length; i++) {
        let tr = document.createElement('tr');
        let roles = "";
        console.log(data);
        data[i].authorities.forEach(a => {
            console.log(a);
            roles = roles + " " + a.authority;
        });
        tr.innerHTML = "<td>" + data[i].userId + "</td>" +
            "<td>" + data[i].name + "</td>" +
            "<td>" + data[i].surname + "</td>" +
            "<td>" + roles + "</td>" +
            "<td> <a type='button' class='btn btn-sm btn-primary' data-bs-toggle='modal'" +
            "data-bs-target='#editModal" + data[i].userId + "'>Edit</a></td>" +
            "<td> <a type='button' class='btn btn-sm btn-danger' data-bs-toggle='modal'" +
            "data-bs-target='#DELETE" + data[i].userId + "'>Delete</a></td>" +
            "<div class='modal fade' id='editModal" + data[i].userId + "' " +
            "                                 aria-labelledby='exampleModalLabel' aria-hidden='true'>" +
            "                                <div class='modal-dialog' modal-dialog-centered>" +
            "                                    <div class='modal-content'>" +
            "                                        <div class='modal-header'>" +
            "                                            <h5 class='modal-title' id='editModalLabel'>Edit user</h5>" +
            "                                            <button type='button' class='btn-close' data-bs-dismiss='modal'" +
            "                                                    aria-label='Close'></button>" +
            "                                        </div>" +
            "                                        <form id='edit" + data[i].userId + "'>" +
            "                                            <div class='modal-body col-md text-center'>" +
            "                                                <label for='name'><b>Name</b></label>" +
            "                                                <input value='" + data[i].name + "' class='form-control' id='editName" + data[i].userId + "' required/>" +
            "                                                <br>" +
            "                                                <label for='surname'><b>Surname</b></label>" +
            "                                                <input value='" + data[i].surname + "' class='form-control' id='editSurname" + data[i].userId + "' required/>" +
            "                                                <br>" +
            "                                                <label for='password'><b>Password</b></label>" +
            "                                                <input class='form-control'" +
            "                                                       id='editPassword" + data[i].userId + "'/>" +
            "                                                <br>" +
            "                                                <label for='roles1'><b>Role</b></label>" +
            "                                                <select multiple class='form-control form-control-sm' id='editRoles" + data[i].userId + "' name='role' size='2' required>" +
            "                                                    <option value='1'>USER</option>" +
            "                                                    <option value='2'>ADMIN</option>" +
            "                                                </select>" +
            "                                                <br><br>" +
            "                                            </div>" +
            "                                            <div class='modal-footer'>" +
            "                                                <button type='submit' class='btn btn-primary' onclick='editUser(" + data[i].userId + ")'>" +
            "                                                    Edit" +
            "                                                </button>" +
            "                                                <button id='closeEdit" + data[i].userId + "' type='button' class='btn btn-secondary'" +
            "                                                        data-bs-dismiss='modal'>Close" +
            "                                                </button>" +
            "                                            </div>" +
            "                                            <br>" +
            "                                        </form>" +
            "                                    </div>" +
            "                                </div>" +
            "                            </div>" +
            "                            <div class='modal fade' id='DELETE" + data[i].userId + "' " +
            "                                 aria-labelledby='exampleModalLabel' aria-hidden='true'>" +
            "                                <div class='modal-dialog' role='document'>" +
            "                                    <div class='modal-content'>" +
            "                                        <div class='modal-header'>" +
            "                                            <h5 class='modal-title' id='exampleModalLabel'>Delete" +
            "                                                user</h5>" +
            "                                            <button type='button' class='btn-close'" +
            "                                                    data-bs-dismiss='modal'" +
            "                                                    aria-label='Close'></button>" +
            "                                        </div>" +
            "                                        <form id='delete" + data[i].userId + "'>" +
            "                                            <div class='modal-body col-md text-center'>" +
            "                                                <label for='name'><b>Name</b></label>" +
            "                                                <input value='" + data[i].name + "' disabled class='form-control' id='delName" + data[i].userId + "' required/>" +
            "                                                <br>" +
            "                                                <label for='surname'><b>Surname</b></label>" +
            "                                                <input value='" + data[i].surname + "' disabled class='form-control' id='delSurname" + data[i].userId + "' required/>" +
            "                                                <br>" +
            "                                                <label for='password'><b>Password</b></label>" +
            "                                                <input value='" + data[i].password + "' type='password' disabled class='form-control'" +
            "                                                       id='delPassword" + data[i].userId + "'/>" +
            "                                                <br>" +
            "                                                <label for='roles2'><b>Role</b></label>" +
            "                                                <select multiple class='form-control form-control-sm' id='delRoles" + data[i].userId + "' name='role' size='2' disabled>" +
            "                                                    <option value='1'>USER</option>" +
            "                                                    <option value='2'>ADMIN</option>" +
            "                                                </select>" +
            "                                                <br><br>" +
            "                                            </div>" +
            "                                            <div class='modal-footer'>" +
            "                                                <button type='submit' class='btn btn-danger' onclick='deleteUser(" + data[i].userId + ")'>" +
            "                                                    Delete" +
            "                                                </button>" +
            "                                                <button id ='delClose" + data[i].userId + "' name='close' type='button' class='btn btn-secondary'" +
            "                                                        data-bs-dismiss='modal'>Close" +
            "                                                </button>" +
            "                                            </div>" +
            "                                            <br>" +
            "                                        </form>" +
            "                                    </div>" +
            "                                </div>" +
            "                            </div>";
        user_table.append(tr);
    }
}
window.onload = function f(){
    let addButton = document.getElementById("addButton");
    addButton.onclick = function () {
        newUser();
    }
    getAll();
}
