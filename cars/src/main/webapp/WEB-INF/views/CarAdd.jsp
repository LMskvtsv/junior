<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html lang="en">
<head>
    <title>Add car</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <script>
        $(
           $.ajax('./jsonBodies', {
                    method: 'get',
                complete: function (data) {
                    console.log(JSON.parse(data.responseText));
                    var result = "<option value=\"None\">Please select body type</option>";
                    var bodies = JSON.parse(data.responseText);
                    console.table(bodies);
                    for (var i = 0; i < bodies.length; i++) {
                        result += '<option value=\"' + bodies[i].id + '\">' + bodies[i].name + '</option>';
                        }
                    console.log(result);
                    var bodies_element = document.getElementById("body");
                    bodies_element.innerHTML = result;
                }
            })
        );

        $(
            $.ajax('./jsonBrands', {
                method: 'get',
                complete: function (data) {
                    console.log(JSON.parse(data.responseText));
                    var result = "<option value=\"None\">Please select brand</option>";
                    var bodies = JSON.parse(data.responseText);
                    console.table(bodies);
                    for (var i = 0; i < bodies.length; i++) {
                        result += '<option value=\"' + bodies[i].id + '\">' + bodies[i].name + '</option>';
                    }
                    console.log(result);
                    var bodies_element = document.getElementById("brand");
                    bodies_element.innerHTML = result;
                }
            })
        );

        $(
            $.ajax('./jsonTransmissions', {
                method: 'get',
                complete: function (data) {
                    console.log(JSON.parse(data.responseText));
                    var result = "<option value=\"None\">Please select transmission type</option>";
                    var bodies = JSON.parse(data.responseText);
                    console.table(bodies);
                    for (var i = 0; i < bodies.length; i++) {
                        result += '<option value=\"' + bodies[i].id + '\">' + bodies[i].name + '</option>';
                    }
                    console.log(result);
                    var bodies_element = document.getElementById("transmission");
                    bodies_element.innerHTML = result;
                }
            })
        );

        $(
            $.ajax('./jsonEngines', {
                method: 'get',
                complete: function (data) {
                    console.log(JSON.parse(data.responseText));
                    var result = "<option value=\"None\">Please select engine type</option>";
                    var bodies = JSON.parse(data.responseText);
                    console.table(bodies);
                    for (var i = 0; i < bodies.length; i++) {
                        result += '<option value=\"' + bodies[i].id + '\">' + bodies[i].name + '</option>';
                    }
                    console.log(result);
                    var bodies_element = document.getElementById("engine");
                    bodies_element.innerHTML = result;
                }
            })
        );


        function validate() {
            console.log("inside validate");
            var ids = ["#price", "#description",];
            var rslt = true;
            for (var i = 0; i < ids.length; i++) {
                console.log($(ids[i]));
                if ($(ids[i]).val() == '') {
                    alert('Parameter \''+ $(ids[i]).attr('name')+ '\' cannot not be empty.');
                    rslt = false;
                    break;
                }
            }
            if(rslt) {
                rslt = validateSelection();
            }
            console.log("final result :" + rslt);

            return rslt;
        }


        function validateSelection() {
            var ids = ["#body", "#brand", "#transmission", "#engine"];
            var rslt = true;
            for (var i = 0; i < ids.length; i++) {
                if(!isSelected(ids[i])){
                    alert('Parameter \''+ $(ids[i]).attr('name') + '\' cannot not be empty.');
                    rslt = false;
                    break;
                }
            }
            return rslt;
        }

        function isSelected(id) {
            console.log("inside body" + id);
            var isSelected = false;
            var string = id + ' option:selected';
            var value = $(string).val();
            if (value != 'None' && value != ''){
                isSelected =  true;
            }
            return isSelected;
        }

    </script>
</head>
<body>

<div class="container">
    <h2>Add user</h2>
    <form class="form-horizontal" action="${pageContext.servletContext.contextPath}/add" method="post">
        <div class="form-group">
            <label class="control-label col-sm-2" for="body">Body type:</label>
            <div class="col-sm-10">
                <select class="form-control" id= "body" name="body">
                    //from ajax
                </select>
            </div>
        </div>
        <div class="form-group">
            <label class="control-label col-sm-2" for="brand">Brand:</label>
            <div class="col-sm-10">
                <select class="form-control" id= "brand" name="brand">
                    //from ajax
                </select>
            </div>
        </div>
        <div class="form-group">
            <label class="control-label col-sm-2" for="transmission">Transmission:</label>
            <div class="col-sm-10">
                <select class="form-control" id= "transmission" name="transmission">
                    //from ajax
                </select>
            </div>
        </div>
        <div class="form-group">
            <label class="control-label col-sm-2" for="engine">Engine:</label>
            <div class="col-sm-10">
                <select class="form-control" id= "engine" name="engine">
                    //from ajax
                </select>
            </div>
        </div>
        <div class="form-group">
            <label class="control-label col-sm-2" for="price">Price:</label>
            <div class="col-sm-10">
                <input type="text" class="form-control" id="price" placeholder="Enter price, integer" name="price">
            </div>
        </div>
        <div class="form-group">
            <label class="control-label col-sm-2" for="description">Description:</label>
            <div class="col-sm-10">
                <input type="description" class="form-control" id="description" placeholder="Enter description" name="description">
            </div>
        </div>
        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
                <button type="submit" class="btn btn-default" onclick="return validate()">Add new car</button>
            </div>
        </div>
    </form>
</div>
</body>
</html>
