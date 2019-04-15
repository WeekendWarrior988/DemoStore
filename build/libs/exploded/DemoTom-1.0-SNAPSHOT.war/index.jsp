<%--
  Created by IntelliJ IDEA.
  User: mrmin
  Date: 21/03/2019
  Time: 12:10
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!doctype html>
<html>
<head>
    <title>$DEMO$</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
          integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
</head>
<body>

<div class="container">
    <nav class="nav">
        <a type="button" class="btn btn-outline-primary" href="#">Active</a>
        <a type="button" class="btn btn-outline-success" href="#">Link</a>
        <a type="button" class="btn btn-outline-warning" href="#">Link</a>
        <a href="#" class="nav-link btn btn-info btn-sm" id="showCart">
            <span class="glyphicon glyphicon-shopping-cart"></span> Shopping Cart
        </a>
    </nav>

    <nav aria-label="Page navigation example">
        <ul class="pagination justify-content-center">
            <li class="page-item">
                <a class="page-link" href="#" id="prev">&laquo;</a>
            </li>
            <%--<li class="page-item"><a class="page-link" href="#">1</a></li>--%>
            <%--<li class="page-item"><a class="page-link" href="#">2</a></li>--%>
            <%--<li class="page-item"><a class="page-link" href="#">3</a></li>--%>
            <li class="page-item">
                <a class="page-link" href="#" id="next">&raquo;</a>
            </li>
        </ul>
    </nav>

    <table class="table table-bordered table-hover" id="products-table">
        <thead>
        <tr>
            <th></th>
            <th>Product</th>
            <th>Description</th>
            <th>Price</th>
            <th></th>
        </tr>
        </thead>
        <tbody id="products">
        </tbody>
    </table>
</div>

<div class="container" id="cart">

    <h1 class="justify-content-center">Shopping Cart</h1>

    <table id="table-cart" class="table table-hover table-condensed">
        <thead>
        <tr>
            <th style="width:50%">Product</th>
            <th style="width:50%">Name</th>
            <th style="width:10%">Price</th>
            <th style="width:8%">Quantity</th>
            <th style="width:22%"></th>
            <th style="width:10%"></th>
        </tr>
        </thead>

        <tbody id="products-cart">

        </tbody>
        <tfoot>
        <tr class="visible-xs">
            <td class="text-center"><strong>$0.00</strong></td>
        </tr>
        <tr>
            <td><a href="#" class="btn btn-info" id="cart-showProduct">
                <span class="glyphicon glyphicon-shopping-cart"></span>Continue shopping</a></td>
            <td colspan="2" class="hidden-xs"></td>
            <td class="hidden-xs text-center"><strong>Total $0.00</strong></td>
            <td><a href="#" class="btn btn-success btn-block">Checkout <i class="fa fa-angle-right"></i></a></td>
        </tr>
        </tfoot>
    </table>
</div>

<div class="modal" tabindex="-1" role="dialog" id="loginModal">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title">Login</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <form>
                    <div class="form-group row">
                        <label for="username" class="col-sm-2 col-form-label">Username</label>
                        <div class="col-sm-10">
                            <input type="text" class="form-control" id="username" placeholder="Username">
                        </div>
                    </div>
                    <div class="form-group row">
                        <label for="password" class="col-sm-2 col-form-label">Password</label>
                        <div class="col-sm-10">
                            <input type="password" class="form-control" id="password" placeholder="Password">
                        </div>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-primary" id="login">Login</button>
                <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
            </div>
        </div>
    </div>
</div>

<script src="https://code.jquery.com/jquery-3.3.1.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"
        integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1"
        crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"
        integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM"
        crossorigin="anonymous"></script>
<script src="app.js"></script>
</body>
</html>