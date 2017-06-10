
$(document).ready(function showAllDepartments() {
    $.getJSON("/allDepartments", function (departments) {

        drawDepartments(departments);
    });
});


var deleteDepartment = function (event) {
    var departmentId = event.target.name;

    var settings = {
        "async": true,
        "url": "/deleteDepartment?departmentId=" + departmentId,
        "method": "POST",
    };

    $.ajax(settings).done(function deleteDepartment(response) {

        drawDepartments(response);
    });
};


var saveOrUpdateFormDep = function (event) {

    var departmentId = event.target.name;

    $.ajax({
        url: '/createOrUpdateDepartment',
        data: {departmentId: departmentId},
        type: 'GET',
        dataType: 'json',
        success: function (response) {
            $('#xyj').text('');
            showDeppForm(response);
        }
    });
};


var showDeppForm = function (department) {
    var departmentName = department.name;
    var departmentId = department.id;

    var table = $('<table>');

    table.addClass('main');

    table
        .append($('<form id="myFormDep">')
            .append($('<tr>').append($('<td>')
                    .append($('<input type="hidden" id="idDep" value="'+(departmentId === null ? '' : departmentId)+'"/>')
                        .text(department.id)
                    )
                )
            )
            .append($('<tr>').append($('<td>')
                    .append($('<input type="text" id="nameDep" placeholder="departmentName" name="departmentName" value="'+(departmentName === null ? '' : departmentName)+'"/>')
                        .text(department.name)).append($('<td>')
                    )
                )
            )
            .append($('<p id="errMess"></p>'))

            .append($('<tr>')
                .append($('<button class="btn-success btn-sm active" type="submit">Save department</button>'))
                .append($('<button class="btn-primary btn-sm active"  onclick="showNewDepartments()">Return</button>')))
            .append($('<td>')));

    $('#xyj').append(table);


    validDepartment();
};


function validDepartment() {
    jQuery.validator.setDefaults({
        debug: true,
        success: "valid"
    });
    $.validator.addMethod(
        "regex",
        function(value, element, regexp) {
            var check = false;
            return this.optional(element) || regexp.test(value);
        },
        "No special Characters allowed here.Use only upper-, lowercase letters (A through Z; a through z) and numbers"
    );

    $("#myFormDep").validate({
        rules: {
            departmentName: {
                required: true,
                minlength: 3,
                maxlength: 15,
                regex: /^[a-zA-Z0-9 ]+$/
            },
        },
        submitHandler: function () {
            saveDepartment();
        }
    });
}


var saveDepartment = function () {
    var departmentId = $('#idDep').val();
    var departmentName = $('#nameDep').val();

    $.ajax({
        url: '/createOrUpdateDepartmentAction',
        data: {departmentId: departmentId,
            departmentName: departmentName},
        type: 'POST',
        dataType: 'json',
        success: function (response) {
            drawDepartments(response);
        },
        error: function (xhr) {
            var errMessage = JSON.parse(xhr.responseText).message;
            document.getElementById("errMess").innerHTML = errMessage;
        }
    });
};


var showUsers = function (event) {

    $('#xyj').text('');
    var departmentId = event.target.name;

        var settings = {
        "async": true,
        "url": "/allUsers/" + departmentId,
        "method": "GET"
    };
    $.ajax(settings).done(function UserById(response) {
        drawUsers(response, departmentId);
    })
};


var deleteUser = function (event) {

var userId = event.target.name;
var departmentId = $('#departmentIdU').val();

    var settings = {
        "async": true,
        "url": "/deleteUser?userId=" + userId,
        "method": "POST",
    };

    $.ajax(settings).done(function deleteUser() {
        showNewUsers(departmentId);
    });

};

function showNewUsers(id) {
    getUsers(id)
        .then(drawUsers);
}

function getUsers(departmentId) {
    return $.get('/allUsers/' + departmentId);
}


var saveFormUser = function (event) {

    var departmentId = event.target.name;

    $.ajax({
        url: '/createOrUpdateUser',
        data: {
            userId: null,
            departmentId: departmentId
        },
        type: 'GET',
        dataType: 'json',
        success: function (response) {
            $('#xyj').text('');
            showInsertFormUser(response);
        }
    });
};


var updateFormUser = function (event) {

    var userId = event.target.name;
    var departmentId = $('#departmentId').val();


    $.ajax({
        url: '/createOrUpdateUser',
        data: {
            userId: userId,
            departmentId: departmentId
        },
        type: 'GET',
        dataType: 'json',
        success: function (response) {
            $('#xyj').text('');
            showInsertFormUser(response);
        }
    });
};


var showInsertFormUser = function (user) {
    var userId = user.id;
    var userName = user.name;
    var userSurname = user.surname;
    var userEmail = user.email;
    var userSalary = user.salary;
    var userBirthday = user.birthday;
    var departmentId = user.department.id;

    var table = $('<table class="table">');
    table.addClass('main');
    table
        .append(
            $('<form id="myForm">')
                .append(
                    $('<table>')
                        .append($('<tr>')

                            .append($('<input type="hidden" id="id-U" />')
                                .val(userId === null ? '' : userId))


                            .append($('<input type="hidden" id="depId-U"/>')
                                .val(departmentId))


                            .append($('<td>').text('Name:'))
                            .append($('<td>')
                                .append($('<input type="text" id="name-U" name="name"/>')
                                    .val(userName !== null ? user.name : "")
                                )

                            )
                        )
                        .append($('<tr>').append($('<td>').text('Surname:'))
                            .append($('<td>')
                                .append($('<input  type="text"  id="surname-U" name="surName"/>')
                                    .val(userSurname !== null ? user.surname : ""))
                            ))
                        .append($('<tr>')
                            .append($('<td>').text('Email:'))
                            .append($('<td>')
                                .append($('<input type="text" id="email-U" name="email"/>')
                                    .val(userEmail !== null ? user.email : ""))


                            ).append($('<p id="emailError"></p>'))
                        )
                        .append($('<tr>')
                            .append($('<td>').text('Salary:'))
                            .append($('<td>')
                                .append($('<input type="text" id="salary-U" name="salary"/>')
                                    .val(userSalary !== null ? user.salary : ""))
                            )
                        )
                        .append($('<tr>')
                            .append($('<td>').text('Birth Date:'))
                            .append($('<td>')
                                .append($('<input type="date" id="birthday-U" name="birthDate"/>')
                                    .val(userBirthday !== null ? user.birthday : ""))
                            )
                        )

                        .append($('<tr>')
                            .append($('<td>')
                                .append($('<button class="btn btn-success" type="submit">Save</button>'))
                            )
                        )
                )
        );
    $('#xyj').append(table);

    validUser();

};


function validUser() {
    jQuery.validator.setDefaults({
        debug: true,
        success: "valid"
    });
    $("#myForm").validate({
        rules: {
            name: {
                required: true,
                minlength: 3,
                maxlength: 15
            },
            surName:{
                required: true,
                minlength: 3,
                maxlength: 15
            },
            email:{
                required: true,
                email: true
            },
            salary:{
              required: true,
                number: true,
                digits: true,

            },
        },
        messages: {
            name: {
                required: "field 'name' can't be empty, insert something",
                minlength: jQuery.validator.format( "It's too small symbols, you have to type {0} symbols at least"),
                maxlength: jQuery.validator.format("You can insert less than {0} symbols")
            },
            surName:{
                required:"Field can't be empty, insert something",
                minlength: jQuery.validator.format( "It's too small symbols, you have to type {0} symbols at least"),
                maxlength: jQuery.validator.format("You can insert less than {0} symbols")
            },
            email:{
                required: "field 'e-mail' can't be empty, type something!",
                email: "Uncorrected format e-mail, try to put other"
            },
            salary: {
                required: "field 'salary' can't be empty, type something!",
                number: "these symbol types isn't allowed here!",
                digits: "salary can't be negative!"
            }
        },
        submitHandler: function () {
            saveUser(event);
        }
    });
}


var saveUser = function (event) {

    event.preventDefault();

    var userId = $('#id-U').val();
    var userName = $('#name-U').val();
    var userSurname = $('#surname-U').val();
    var userEmail = $('#email-U').val();
    var userSalary = $('#salary-U').val();
    var userBirthday = $('#birthday-U').val();
    var departmentId = $('#depId-U').val();

    $.ajax({
        url: '/createOrUpdateUserAction',
        data: {
            userId: userId,
            userName: userName,
            userSurname: userSurname,
            userEmail: userEmail,
            userSalary: userSalary,
            userBirthday: userBirthday,
            departmentId: departmentId
        },
        type: 'POST',
        dataType: 'json',
        success: function (response) {
            drawUsers(response, departmentId);

        },
        error: function (xhr) {
            document.getElementById("emailError").innerText = "";
            var errMessage = JSON.parse(xhr.responseText).message;
            document.getElementById("emailError").innerHTML = errMessage;
        }
    });
};


function drawUsers(users, departmentId) {

    var container = $('#xyj'),

        table = $('<table class="table-bordered">');
    table.addClass('main');

    $.each(users, function (key, user) {
        var tr = $('<tr></tr>');

        ($('<p id="departmentIdU" />').val(user['department'].id).hide()).appendTo(tr);

        $('<td>' + user.name + '</td>').appendTo(tr);
        $('<td>' + user['surname'] + '</td>').appendTo(tr);
        $('<td>' + user['salary'] + '</td>').appendTo(tr);
        $('<td>' + user.email + '</td>').appendTo(tr);
        $('<td>' + user['created'] + '</td>').appendTo(tr);
        $('<td>' + user['birthday'] + '</td>').appendTo(tr);
        $('<td hidden>' + user['department'].id + '</td>').appendTo(tr);

        $('<td>' + '<button class="btn btn-primary" onclick="updateFormUser(event)" name="'+user.id +'">Edit</button>' + '</td>').appendTo(tr);
        $('<td>' + '<button class="btn btn-danger" onclick="deleteUser(event)" name="'+user.id+'">Delete</button>' + '</td>').appendTo(tr);

        table.append(tr);
    });

    table
        .append($('<tr>')
            .append($('<td>')
                .append('<button id="insertU" onclick="saveFormUser(event)" name="'+departmentId+'" class="btn-primary btn-sm active">Create user</button>')
                .append('<button onclick="showNewDepartments()" class="btn-success btn-sm active">Departments</button>')));

    container.empty().append(table);
}


function showNewDepartments() {
    getDepartments()
        .then(drawDepartments);
}

function getDepartments() {
    return $.get('/allDepartments');
}

function drawDepartments(departments) {
    var container = $('#xyj'),

    table= $('<table class="table-bordered">');
    table.addClass('main');

    $.each(departments, function (key, department) {
        var tr = $('<tr></tr>');

        ($('<p id="departmentId" />').val(department.id).hide()).appendTo(tr);

        $('<td>' + department.name + '</td>').appendTo(tr);
        $('<td>' + department['created'] + '</td>').appendTo(tr);
        $('<td>' + '<button class="btn btn-success" onclick="saveOrUpdateFormDep(event)" name="'+department.id+'">Edit</button>' + '</td>').appendTo(tr);
        $('<td>' + '<button class="btn btn-danger" onclick="deleteDepartment(event)" name="'+department.id+'">Delete</button>' + '</td>').appendTo(tr);
        $('<td>' + '<button class="btn btn-info" onclick="showUsers(event)" name="'+department.id+'">Users</button>' + '</td>').appendTo(tr);

        table.append(tr);
    });
    table.append('<button id="insertDep" onclick="saveOrUpdateFormDep(event)" class="btn-primary btn-sm active">Create department</button>');
    container.empty().append(table);
}