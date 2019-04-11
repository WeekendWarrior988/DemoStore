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
         $('#products').hide();
         $('#cart').show();
         printCart(skip, rowsPerPage);
     });

    // [Show Products]
    $('#cart-showProduct').click(function () {
        $('#products').show();
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

        // if (skip === 0) $('#prev').addClass('disabled');
        // else $('#prev').removeClass('disabled');
        //
        // if (data.length <= rowsPerPage) $('#next').addClass('disabled');
        // else $('#next').removeClass('disabled');

        var html = '';
        for (i = 0; i < Math.min(data.length, rowsPerPage); i++) {
            html +=
                '<tr>' +
                '<td><img src="' + data[i].image + '" width="100"></td>' +
                '<td>' + data[i].name + '</td>' +
                '<td>' + data[i].description + '</td>' +
                '<td class="text-right">' + data[i].price + '</td>' +
                // '<td><button onclick="jamam('+data[i].id+')">Add to basket</button>' + '</td>' +
                '<td class="text-right">' +
                '<a href="#" class="nav-link btn btn-info btn-sm ml-add-krepselis" onclick="jamam('+data[i].id+')">' +
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
    // alert("jamam"+productId);
    $.ajax({
        url: 'api/cart/add',
        method: 'POST',
        dataType: 'json',
        contentType: 'application/json',
        data: JSON.stringify({})
    }).done(function () {

        alert("krepšelis sukurtas");
        console.log('krepšelis sukurtas');

    }).fail(function () {

        console.log('krepšelis NE sukurtas');
    });

    $.ajax({
        url: 'api/cart',
        method: 'POST',
        dataType: 'json',
        contentType: 'application/json',
        data: JSON.stringify({
            id: productId,
            qty: 1
        })
    }).done(function () {

        alert("prekė " + productId + " įdėta į krepšį");
        console.log('į krepšelį įdėta ' + productId + ' prekė');

    }).fail(function () {

        console.log('neįdėta į krepšelį');
    });
}

// function printCartRows(cartId) {
//
//     var token = window.localStorage.token;
//
//     $.ajax('api/product/id=?/f', {
//         method: 'GET',
//         headers: {
//             Authorization: "Bearer " + token
//         },
//         dataType: 'json',
//         data: {
//             cart_id: cartId
//         }
//
//     }).done(function (data) {
//
//         var html = '';
//         for (i = 0; i < (data.length); i++) {
//             html +=
//             '<tr> '+
//             '<td data-th="Product">' +
//             '<div class="row">' +
//             '<div class="col-sm-2 hidden-xs"><img src="' + data.image + '"  alt="..." class="img-responsive"/></div>' +
//             '<div class="col-sm-10">' +
//             '<h4 class="nomargin">' + data.name + '</h4>' +
//             '<p>' + data.description + '</p>' +
//             '</div>' +
//             '</div>' +
//             '</td>' +
//             '<td data-th="Price">€ ' + data.price + '</td>' +
//             '<td data-th="Quantity"><input type="number" class="form-control text-center" value="' + qty + '"></td>' +
//             '<td data-th="Subtotal" class="text-center">€ ' + (data.price * qty) + '</td>' +
//             '<td class="actions" data-th="">' +
//             '<button class="btn btn-info btn-sm"><i class="fa fa-refresh"></i></button>' +
//             '<button class="btn btn-danger btn-sm" id=""><i class="fa fa-trash-o"></i></button>' +
//             '</td>' +
//             '</tr>';
//         }
//             $('#products-cart').html(html);
//
//     }).fail(function (jqXHR) {
//         if (jqXHR.status === 401) {
//             $('#login').click(function () {
//                 login($('#username').val(), $('#password').val(), skip, rowsPerPage);
//             });
//             $('#loginModal').modal('show')
//         } else {
//             alert("Other error");
//         }
//
//     });
// }

function bildCartRows(data) {

    data.forEach(function (item) {

        var id = item.id;
        var qty = item.qty;

        $.ajax({
            url: 'api/cart/' + id,
            method: 'GET',
            headers: {Authorization: "Bearer " + token},
            dataType: 'json',
            data: {
                // id: id,
                // qty: qty
            }
        }).done(function (data) {

            bildHtml(data, qty);

        }).fail(function () {
            console.log('produktas iš cart pagal id nerastas');
        });
    });
}

function bildHtml(data, qty) {

    var html = '';
    html += '<tr>';
    html += '<td data-th="Product">';
    html += '  <div class="row">';
    html += '    <div class="col-sm-2 hidden-xs"><img src="' + data.image + '"  alt="..." class="img-responsive"/></div>';
    html += '    <div class="col-sm-10">';
    html += '      <h4 class="nomargin">' + data.name + '</h4>';
    html += '      <p>' + data.description + '</p>';
    html += '    </div>';
    html += '  </div>';
    html += '</td>';
    html += '<td data-th="Price">€ ' + data.price + '</td>';
    html += '<td data-th="Quantity"><input type="number" class="form-control text-center" value="' + qty + '"></td>';
    html += '<td data-th="Subtotal" class="text-center">€ ' + (data.price * qty) + '</td>';
    html += '<td class="actions" data-th="">';
    html += '  <button class="btn btn-info btn-sm"><i class="fa fa-refresh"></i></button>';
    html += '  <button class="btn btn-danger btn-sm" id=""><i class="fa fa-trash-o"></i></button>';
    html += '</td>';
    html += '</tr>';
    $('#products-cart').html($('#products-cart').html() + html);
}

function printCart(skip, rowsPerPage) {
    // gauti prekių sarašą
    var token = window.localStorage.token;
    $.ajax({
        url: 'api/cart/list',
        method: 'POST',
        headers: {
            Authorization: "Bearer " + token
        },
        dataType: 'json'
    }).done(function (data) {

            bildCartRows(data);

            // DELETE
            $('#ml-del-krepselis').on('click', function () {
                var $row = $(this).closest('tr');
                var $columns = $row.find('td');

                // get id
                var id = Number($columns[0].innerHTML); // paima table row pirmojo td reikšmę kuri yra product_id

                console.log('id=' + id);

                // įelti json į POST ir sukelti duomenis į DB
                $.ajax({
                    url: 'api/cart',
                    method: 'DELETE',
                    dataType: 'json',
                    contentType: 'application/json',
                    data: {
                        id: id
                    }
                }).done(function (data) {
                    console.log('ištrinta ' + id + ' prekė');

                }).fail(function () {
                    alert('prekė neištrinta')
                });
            });

        }
    ).fail(function (jrXHR) {

        if (jrXHR.status === 401) {

            $('#login').click(function () {
                login($('#username').val(), $('#password').val(), skip, rowsPerPage);
            });

            $('#loginModal').modal('show');

            // alert("fail Product List eror 401");
        } else {
            alert("fail Product List eror " + jrXHR.status);
        }
    });
}
