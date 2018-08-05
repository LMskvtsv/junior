<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html lang="en">
<head>
    <title>Cars</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <script>
        var jsonArray;
        $(
            $.ajax('./jsonCars', {
                method: 'get',
                complete: function (data) {
                    var result = "";
                    jsonArray = JSON.parse(data.responseText);
                    console.log(jsonArray);
                    for (var i = 0; i < jsonArray.length; i++) {
                        if(jsonArray[i].isSold == 1){
                            result += '<tr><td>' + jsonArray[i].id + '</td><td>' + jsonArray[i].brand.name + '</td><td>' + jsonArray[i].transmission.name + '</td><td>' + jsonArray[i].body.name + '</td><td>' + jsonArray[i].engine.name + '</td><td>' + jsonArray[i].price + '</td><td><input type="checkbox" class="check-box-table-cell" name="isSold" checked disabled></td><td>';
                            result+= '<form id="form" class="form-horizontal" action="/viewCar"><input type="hidden" name="id" value = "' + jsonArray.id + '"><button type="submit" class="btn btn-default" name="View"></form></td></tr>';
                        } else {
                            result += '<tr><td>' + jsonArray[i].id + '</td><td>' + jsonArray[i].brand.name + '</td><td>' + jsonArray[i].transmission.name + '</td><td>' + jsonArray[i].body.name + '</td><td>' + jsonArray[i].engine.name + '</td><td>' + jsonArray[i].price + '</td><td><input type="checkbox" class="check-box-table-cell" name="isSold" disabled></td><td>';
                            result+= '<form id="form" class="form-horizontal" action="/viewCar"><input type="hidden" name="id" value = "' + jsonArray.id + '"><button type="submit" class="btn btn-default" name="View"></form></td></tr>';
                        }
                    }
                    result+="</tbody>";
                    var t = document.getElementById("body-items");
                    t.innerHTML = result;
                }
            })
        );
    </script>
</head>
<body>

<div class="container">
    <form id="form" class="form-horizontal" action="/add">
        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
                <button type="submit" class="btn btn-default">Add new car</button>
            </div>
        </div>
    </form>

    <div class="container">
        <h2>Items</h2>
        <table id="items" name="mytable" class="table table-hover">
            <thead>
            <tr id = "header-items">
                <th>Id</th>
                <th>Brand</th>
                <th>Transmission</th>
                <th>Body</th>
                <th>Engine</th>
                <th>Price</th>
                <th>Sold</th>
                <th></th>
            </tr>
            </thead>
            <tbody id = "body-items">
            </tbody>
        </table>
    </div>
</div>
</body>
</html>
