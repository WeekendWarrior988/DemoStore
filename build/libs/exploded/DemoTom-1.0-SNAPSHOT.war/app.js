$(function () {

    var skip = 0;
    var rowsPerPage = 5;

    // Hide cart
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

function jamam(productId) {
    console.log("paspausta prekė " + productId);

    // 1 idedama preke i krepseli
    $.ajax({
        url: 'api/cart/add', // dedama į 1 krepseli
        method: 'POST',
        dataType: 'json',
        contentType: 'application/json',
        data: JSON.stringify({
            id: productId,
            qty: 1
        })
    }).done(function () {
        console.log("prekė " + productId + " įdėta į krepšį");

        // 2 atspausdinamos visos krepselio prekes
        printCart();

    }).fail(function () {

        console.log('neįdėta į krepšelį');
    });
}

function printCart() {
    console.log('spausdinamos cart prekės');
    // console.log('skip=' + skip + ' row=' + rowsPerPage);

    var token = window.localStorage.token;

    // gauna cart produktų sarašą
    $.ajax({
        url: 'api/cart',
        method: 'GET',
        dataType: 'json',
        headers: {
            Authorization: "Bearer " + token
        }
    }).done(function (cart) {

        // for ( i = 0; i <cart.cartLines.length ; i++) {
        //     console.log(cart.cartLines[i])
        // }
        // console.log("buildCartRows: " + cart.cartLines[1].product.name);
        buildCartRows(cart);

    }).fail(function (jrXHR) {

        console.log("fail Product List error");
    });
}

function buildCartRows(cart) {

    var cartLines = cart.cartLines;

    console.log("  cartLines.lenght=" + cartLines.length);


    var html = '';
    var nr = 0;
    cartLines.forEach(function (cartLine) {

        var cartLineId = cartLine.id;
        var cartLineQty = cartLine.qty;
        var product = cartLine.product;


        html += addHtmlCartRow(cartLineId, cartLineQty, product);


    });
    $('#products-cart').html(html);
    $('#total').html(cart.sum);
}

function addHtmlCartRow(cartLineId, cartLineQty, product) {

    var html = '<tr>';
    html += ' <td data-th="Nr.">' + product.image + '</td>';
    html += ' <td data-th="Prekė">';
    html += '   <div class="row">';
    html += '     <div class="col-sm-2 hidden-xs"></div>';
    html += '     <div class="col-sm-10">';
    html += '       <h4 class="nomargin">' + product.name + '</h4>';
    html += '       <p>' + '</p>';
    html += '     </div>';
    html += '   </div>';
    html += ' </td>';
    html += ' <td data-th="Kaina">€ ' + product.price + '</td>';
    html += ' <td data-th="Kiekis"><input type="number" class="form-control text-center" value="' + cartLineQty + '"></td>';
    html += ' <td data-th="Suma" class="text-center">€ ' + (product.price * cartLineQty) + '</td>';
    html += ' <td class="actions" data-th="">';
    html += '   <button class="btn btn-info btn-sm"><i class="fa fa-refresh"></i></button>';
    html += '   <button class="btn btn-danger btn-sm" id=""><i class="fa fa-trash-o"></i></button>';
    html += ' </td>';
    html += '</tr>';
    return html;
}

// DELETE ---------------------------------------------------------------------------------------------------------------

function deleteCartLine(productId) {
    $.ajax({
        url: 'api/cart/deleteCart',
        method: 'DELETE',
        dataType: 'json',
        contentType: 'application/json',
        data: JSON.stringify({
            id: productId
        }).done(
            console.log("success: "+productId+" producto nebeliko!")
        )
    })
}