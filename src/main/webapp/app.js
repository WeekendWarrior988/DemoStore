$(function() {

    var skip = 0;
    var rowsPerPage = 5;

    $('#cart').hide();
    printProducts(skip, rowsPerPage);

    // [<<]
    $('#prev').click(function () {
        skip -= rowsPerPage;
        if (skip < 0) skip = 0;
        printProducts(skip, rowsPerPage);
    });

    // [>>]
    $('#next').click(function () {
        skip += rowsPerPage;
        printProducts(skip, rowsPerPage);
    });

    // [Show Cart]
    $('#showCart').click(function () {
        $('#ml-products-cart').html('');
        $('#products').hide();
        $('#cart').show();
        printCart(skip, rowsPerPage)
    });

    // [Show Products]
    $('#cart-showProduct').click(function () {
        $('#products').show();
        $('#cart').hide();
    });
});

function printProducts(skip, rowsPerPage) {

    var token = window.localStorage.token;
    $.ajax({
        url: 'api/product/list',
        method: 'GET',
        headers: {
            Authorization: "Bearer " + token
        },
        dataType: 'json',
        data: {
            size: rowsPerPage + 1,
            skip: skip
        }
    }).done(function (data) {

        // << >> show/hide
        if (skip === 0) $('#prev').hide();
        else $('#prev').show();
        if (data.length <= rowsPerPage) $('#next').hide();
        else $('#next').show();
        // [<<]
        // $('#prev').click(function () {
        //     skip -= rowsPerPage;
        //     if (skip <= 0) {
        //         skip = 0;
        //         $('#prev').addClass('disabled');
        //
        //         console.log('addClass');
        //
        //     } else {
        //         $('#prev').removeClass('disabled');
        //         // printProducts(skip, rowsPerPage);
        //
        //         console.log('removeClass');
        //     }
        // });
        // [>>]
        // logika jeigu data.len=6vykdyti
        // else disabled
        // $('#next').click(function () {
        //
        //     if (data.length === rowsPerPage + 1) {
        //         skip += rowsPerPage;
        //         $('#prev').addClass('disabled');
        //     } else {
        //         $('#prev').removeClass('disabled');
        //     }
        //     printProducts(skip, rowsPerPage);
        // });
        // if (skip === 0) $('#prev').addClass('disabled');
        // else $('#prev').removeClass('disabled');
        // if (data.length <= rowsPerPage) $('#next').addClass('disabled');
        // else $('#next').removeClass('disabled');

        bildHtmlProductsRows(data, rowsPerPage); // iškelti į funkciją bildHtmlProductsRows

        // $('.ml-add-krepselis').on('click', function () {
        $('.ml-add-krepselis').click(function () {

            // nustatome kuri eilutė aktyvuota
            var $row = $(this).closest('tr');
            var $columns = $row.find('td');
            var id = Number($columns[0].innerHTML); // paima table row pirmojo td reikšmę kuri yra product_id

            // įkelti json į POST ir sukelti duomenis į DB
            $.ajax({
                url: 'api/cart/',
                method: 'POST',
                dataType: 'json',
                contentType: 'application/json',
                data: JSON.stringify({
                    // id: id,
                    userId: id,
                    qty: 1,
                    product: id
                    // {
                    //     id: 1,
                    //     name: "Testinis_5",
                    //     description: "test.penktas 5",
                    //     price: 1.01,
                    //     image: "https://www.google.com/url?sa=i&source=images&cd=&cad=rja&uact=8&ved=2ahUKEwim4sm6t6ThAhWBYlAKHbuWCDQQjRx6BAgBEAU&url=http%3A%2F%2Funcyclopedia.wikia.com%2Fwiki%2FFile%3AWindows_Flag.jpg&psig=AOvVaw2CSzJPpcteV89ntBHgIk4m&ust=1553848651546871"
                    // }
                })
            }).done(function (data) {

                console.log('į krepšelį įdėta ' + id + ' prekė');
                // console.log(data);
                // alert("Product id=" + id);

            }).fail(function () {

                alert('neįdėta į krepšelį')
            });
        });
    }).fail(function (jrXHR) {

        // kartoti autentifikavimą
        if (jrXHR.status === 401) {

            $('#login').click(function () {
                login($('#username').val(), $('#password').val(), skip, rowsPerPage);
            });

            $('#loginModal').modal('show');

            // alert("fail Product List eror 401");
        } else {
            alert("fail Product List eror " + jrXHR.status);
        }

        // .done(function){} veiks jei gaus response "200"
        // .fail(function){} veiks tuo atveju, jei gaus code response ne "200";
        // .always(function){} same as finally in java, will be done eventually, always.
    });
}

function bildHtmlProductsRows(data, rowsPerPage) {

    var html = '';
    for (var i = 0; i < Math.min(data.length, rowsPerPage); i++) {
        html += '<tr class="ml-product">';
        html += ' <td>' + data[i].id + '</td>';
        html += ' <td><img src="' + data[i].image + '" height="30"></td>';
        html += ' <td>' + data[i].name + '</td>';
        html += ' <td>' + data[i].description + '</td>';
        html += ' <td class="text-right">' + data[i].price + '</td>';
        html += ' <td class="text-right">';
        html += '  <a href="#" class="nav-link btn btn-info btn-sm ml-add-krepselis">';
        html += '   <span class="glyphicon glyphicon-shopping-cart"></span> Į krepšelį</a>';
        html += ' </td>';
        html += '</tr>';
    }
    $('#products').html(html);
}

var token = window.localStorage.token;

function login(username, password, skip, rowsPerPage) {

    $.ajax({
        url: 'api/auth/login',
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
        printProducts(skip, rowsPerPage);
    }).fail(function () {
        alert('login fail')
    });
}

function printPaginationButtons(rowsPerPage) {

    // nevisai geras sprendimas nes reikia pasisiųsti visą table kad gautum vieną skaičiuką - data.length
    $.getJSON('api/product/listall', function (data) {

        html = '';

        for (var i = 0; i < data.length; i += 5) {

            html +=
                '<li class="page-item">\n' +
                ' <a class="page-link ml-num77" href="#" aria-label="Next">\n' +
                '  <span aria-hidden="true">' + ((i / 5) + 1) + '</span>\n' +
                ' </a>\n' +
                '</li>\n';
        }

        $('#ml-pagination').html(html);

        // todo taisyklė: script gali būti vykdomas tada kai suskuriami elementai
        // [1][2][3] - nereaguoja į mygtukus nors turi tą pačią klasę
        $('.ml-num77').click(function () {
            skip = 5;
            console.log("reaguoja į paspaudimą [][][]");
            printProducts(skip, rowsPerPage);
        });
    });
}

//----------------------------------------------------------------------------------
//----------------------------------------------------------------------------------
//----------------------------------------------------------------------------------
function printCart(skip, rowsPerPage) {
    // gauti prekių sarašą
    var token = window.localStorage.token;
    $.ajax({
        url: 'api/cart/listAll',
        method: 'GET',
        headers: {
            Authorization: "Bearer " + token
        },
        dataType: 'json'
    }).done(function (data) {

        bildHtmlCartRows(data);

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
                    id: 2
                }
            }).done(function (data) {
                console.log('ištrinta ' + id + ' prekė');

            }).fail(function () {
                alert('prekė neištrinta')
            });
        });

    }).fail(function (jrXHR) {

        if (jrXHR.status === 401) {

            $('#login').click(function () {
                login($('#username').val(), $('#password').val(), skip, rowsPerPage);
            });

            $('#loginModal').modal('show');

            // alert("fail Product List eror 401");
        } else {

            alert("fail Product List eror " + jrXHR.status);
        }

        // .done(function){} veiks jei gaus response "200"
        // .fail(function){} veiks tuo atveju, jei gaus code response ne "200";
        // .always(function){} same as finally in java, will be done eventually, always.
    });
}

function bildHtmlCartRows(cartList) {

    cartList.forEach(function (product) {

        console.log('item=' + product.id);

        var id = product.id;
        var qty = product.qty;


        // todo     perdaryti entity.Cart [foreignKey to product_id] kad turėtų prekę,
        // todo     tad nereikės traukti atskirai iš products table
        $.ajax({
            url: 'api/product/' + id,
            method: 'GET',
            headers: {Authorization: "Bearer " + token},
            dataType: 'json'
            // data: {}
        }).done(function (product) {

            addHtmlCartRow(product, qty);

        }).fail(function () {

            console.log('bildHtmlCartRows: produktas pagal id nerastas');
        });
    });
}

function addHtmlCartRow(product, qty) {

    var html = '';
    html += '<tr>';
    html += ' <td data-th="Product">';
    html += '   <div class="row">';
    html += '     <div class="col-sm-2 hidden-xs"><img src="' + product.image + '"  alt="..." class="img-responsive"/></div>';
    html += '     <div class="col-sm-10">';
    html += '       <h4 class="nomargin">' + product.name + '</h4>';
    html += '       <p>' + product.description + '</p>';
    html += '     </div>';
    html += '   </div>';
    html += ' </td>';
    html += ' <td data-th="Price">€ ' + product.price + '</td>';
    html += ' <td data-th="Quantity"><input type="number" class="form-control text-center" value="' + qty + '"></td>';
    html += ' <td data-th="Subtotal" class="text-center">€ ' + (product.price * qty) + '</td>';
    html += ' <td class="actions" data-th="">';
    html += '   <button class="btn btn-info btn-sm"><i class="fa fa-refresh"></i></button>';
    html += '   <button class="btn btn-danger btn-sm" id=""><i class="fa fa-trash-o"></i></button>';
    html += ' </td>';
    html += '</tr>';
    $('#ml-products-cart').html($('#ml-products-cart').html() + html);
}


//     $('#prev').click(function () {
//         skip -= rowsPerPage;
//         if (skip < 0) skip = 0;
//         printRows(skip, rowsPerPage);
//     });
//
//     $('#next').click(function () {
//         skip += rowsPerPage;
//         printRows(skip, rowsPerPage);
//     });
//
//     printRows(skip, rowsPerPage);
//
// });
//
// function printRows(skip, rowsPerPage) {
//
//     var token = window.localStorage.token;
//
//     $.ajax('api/product/list', {
//         method: 'GET',
//         headers: {
//             Authorization: "Bearer " + token
//         },
//         dataType: 'json',
//         data: {
//             size: rowsPerPage + 1,
//             skip: skip
//         }
//
//     }).done(function (data) {
//
//         // if (skip === 0) $('#prev').hide();
//         // else $('#prev').show();
//         //
//         // if (data.length <= rowsPerPage) $('#next').hide();
//         // else $('#next').show();
//
//         if (skip === 0) $('#prev').addClass('disabled');
//         else $('#prev').removeClass('disabled');
//
//         if (data.length <= rowsPerPage) $('#next').addClass('disabled');
//         else $('#next').removeClass('disabled');
//
//         var html = '';
//         for (i = 0; i < Math.min(data.length, rowsPerPage); i++) {
//             html +=
//                 '<tr>' +
//                 '<td><img src="' + data[i].image + '" width="100"></td>' +
//                 '<td>' + data[i].name + '</td>' +
//                 '<td>' + data[i].description + '</td>' +
//                 '<td class="text-right">' + data[i].price + '</td>' +
//                 '</tr>';
//         }
//         $('#products').html(html);
//
//     }).fail(function(jqXHR) {
//         if (jqXHR.status === 401) {
//             $('#login').click(function() {
//                 login($('#username').val(), $('#password').val(), skip, rowsPerPage);
//             });
//             $('#loginModal').modal('show')
//         } else {
//             alert("Other error");
//         }
//
//     });
// }
//
// function login(username, password, skip, rowsPerPage) {
//     //$('#login').off();
//     $.ajax('api/auth/login', {
//         method: 'POST',
//         dataType: 'json',
//         contentType : 'application/json',
//         data: JSON.stringify({
//             username: username,
//             password: password
//         })
//     }).done(function(data) {
//         $('#loginModal').modal('hide');
//         window.localStorage.token = data.token;
//         printRows(skip, rowsPerPage);
//
//     }).fail(function() {
//         alert('Login fail')
//     });
//
//
//
//
// //     $('#SmartCart').smartCart({
// //         onAdded: function(pObj,quantity){ cartAdded(pObj,quantity);},
// //         onRemoved: function(pObj){ cartRemoved(pObj);},
// //         onUpdated: function(pObj,quantity){ cartUpdated(pObj,quantity); },
// //     });
// //
// //     function cartAdded(obj,qty){
// //         var product_id = obj.attr("pid");
// //         var quantity = qty;
// // // Ajax calls for adding product to cart
// //         function (pObj,quantity){
// //             cartAdded(pObj,quantity);}
// //     }
// //
// //     function cartRemoved(obj){
// //         var product_id = obj.attr("pid");
// // // Ajax call for removing product from cart
// //     }
// //
// //     function cartUpdated(obj,qty){
// //         var product_id = obj.attr("pid");
// //         var quantity = qty;
// // // Ajax call for updating product on cart
// //     }
// //
// //     function cartAdded(obj,qty){
// //         var partNum = obj.attr("partNumber");
// //         var quantity = qty;
// //         $.ajax({
// //             type: 'POST',
// //             url: "json/sessionManager",
// //             data : "partNum=" + partNum + "&qty=" + quantity,
// //             dataType: "text/json",
// //             success: function(msg){
// //                 obj.attr("qty", msg[1]);
// //             },
// //             error: function(httpRequest, textStatus, errorThrown) {
// //                 alert("status=" + textStatus + ",error=" + errorThrown);
// //             }
// //         });
// //     }
// }