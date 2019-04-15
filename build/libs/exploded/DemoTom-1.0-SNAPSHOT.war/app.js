$(function () {

    var skip = 0;
    var rowsPerPage = 5;

    $('#cart').hide();
    printRows(skip, rowsPerPage);

    $('#prev').click(function () {
        skip -= rowsPerPage;
        if (skip < 0) skip = 0;
        printRows(skip, rowsPerPage);
    });

    $('#next').click(function () {
        skip += rowsPerPage;
        printRows(skip, rowsPerPage);
    });

    // [Show Cart]
    $('#showCart').click(function () {
        $('#products-table').hide();
        $('#cart').show();
    });

    // [Show Products]
    $('#cart-showProduct').click(function () {
        $('#products-table').show();
        $('#cart').hide();
    });

    printRows(skip, rowsPerPage);

});


function printRows(skip, rowsPerPage) {

    // var token = window.localStorage.token;

    $.ajax('api/product/list', {
        method: 'GET',
        // headers: {
        //     Authorization: "Bearer " + token
        // },
        dataType: 'json',
        data: {
            size: rowsPerPage + 1,
            skip: skip
        }

    }).done(function (data) {

        if (skip === 0) $('#prev').hide();
        else $('#prev').show();

        if (data.length <= rowsPerPage) $('#next').hide();
        else $('#next').show();


        var html = '';
        for (i = 0; i < Math.min(data.length, rowsPerPage); i++) {
            html +=
                '<tr>' +
                '<td><img src="' + data[i].image + '" width="100"></td>' +
                '<td>' + data[i].name + '</td>' +
                '<td>' + data[i].description + '</td>' +
                '<td class="text-right">' + data[i].price + '</td>' +
                '<td class="text-right">' +
                '<a href="#" class="nav-link btn btn-info btn-sm ml-add-krepselis" onclick="jamam(' + data[i].id + ')">' +
                '<span class="glyphicon glyphicon-shopping-cart"></span>Add to basket</a>' +
                '</td>' +
                '</tr>';
        }
        $('#products').html(html);

    }).fail(function (jqXHR) {
        if (jqXHR.status === 401) {
            $('#login').click(function () {
                login($('#username').val(), $('#password').val(), skip, rowsPerPage);
            });
            $('#loginModal').modal('show')
        } else {
            alert("Other error");
        }

    });
}

function login(username, password, skip, rowsPerPage) {
    //$('#login').off();
    $.ajax('api/auth/login', {
        method: 'POST',
        dataType: 'json',
        contentType: 'application/json',
        data: JSON.stringify({
            username: username,
            password: password
        })
    }).done(function (data) {
        $('#loginModal').modal('hide');
        window.localStorage.token = data.token;
        printRows(skip, rowsPerPage);

    }).fail(function () {
        alert('Login failed')
    });
}

function jamam(id) {
    $.ajax({
        url: 'api/cart/add',
        method: 'POST',
        dataType: 'json',
        contentType: 'application/json',
        data: JSON.stringify({
            id: id,
            qty: 1
        })
    }).done(function (data) {
        // data.quantity = parseInt(1 + "", 10);

        var row = $("<tr class='rowForCount'>");

        row.append($('<td><img src="' + data.image + '" width="40" alt="product"></td>'))
            .append($('<td>' + data.name + '</td>'))
            .append($('<td class="text-right">' + data.price + '</td>'))
            .append($('<td class="quantity-' + data.id + '">' + data.quantity + '</td>'))
            .append($('<td><button class="remove-cart-item">Remove</button></td>'))
            // .append($('<td class="hide-id id-class ' + "id-class-" + data.id + '">' + data.id + '</td>'));

        $("#products-cart").append(row);


    }).fail(function () {
        alert("didnt get the product")
    })
}

