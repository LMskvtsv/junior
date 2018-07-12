<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html lang="en">
<head>
    <title>Add user</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <script>
        var countries_json;

        $(
           $.ajax('./countries', {
                    method: 'get',
                complete: function (data) {
                    console.log(JSON.parse(data.responseText));
                    var result = "<option value=\"None\">Please select country</option>";
                    countries_json = JSON.parse(data.responseText);
                    console.table(countries_json);
                    for (var i = 0; i < countries_json.length; i++) {
                        console.log(countries_json[i].name);
                        result += '<option value=\"' + countries_json[i].name + '\">' + countries_json[i].name + '</option>';
                        }
                    console.log(result);
                    var t = document.getElementById("country");
                    t.innerHTML = result;
                    console.log(t);
                }
            })
        );

        function isCountrySelected() {
            var isSelected = false;
            if ($('#country option:selected').val() != 'None'){
                isSelected =  true;
            }
            console.log("Country selected = " + isSelected);
            return isSelected;
        }

        function isCitySelected() {
            var isSelected = false;
            var cityValue = $('#city option:selected').val();
            if (cityValue != 'None' && cityValue != ''){
                isSelected =  true;
            }
            console.log("City selected = " + isSelected);
            return isSelected;
        }

        function fillInCities() {
            if(isCountrySelected()){
              var countryValue = $('#country option:selected').val();
                var result="<option value=\"None\">Please select city</option>";
                for (var i = 0; i < countries_json.length; i++) {
                  if(countries_json[i].name == countryValue){
                      var arrCities = countries_json[i].cities;
                      for (var j = 0; j < arrCities.length; j++) {
                          console.log(arrCities[j].name);
                          result += '<option value=\"' + arrCities[j].name + '\">' + arrCities[j].name + '</option>';
                      }
                      break;
                  }
                }
                var cities =  document.getElementById("city");
                cities.innerHTML = result;
            }
        }

        function validate() {
            console.log("validate function");
            var ids = ["#name", "#login", "#email", "#password"];
            var rslt = true;
            for (var i = 0; i < ids.length; i++) {
                console.log($(ids[i]));
                if ($(ids[i]).val() == '') {
                    alert('Parameter \''+ $(ids[i]).attr('name')+ '\' cannot not be empty.');
                    rslt = false;
                    break;
                }
            }
            if(rslt){
                if(!isCitySelected() || !isCountrySelected()){
                    alert('Please select country and city.');
                    rslt = false;
                }
            }
            return rslt;
        }

    </script>
</head>
<body>

<div class="container">
    <h2>Add user</h2>
    <form class="form-horizontal" action="${pageContext.servletContext.contextPath}/add" method="post">
        <div class="form-group">
            <label class="control-label col-sm-2" for="email">Name:</label>
            <div class="col-sm-10">
                <input type="text" class="form-control" id="name" placeholder="Enter name" name="name">
            </div>
        </div>
        <div class="form-group">
            <label class="control-label col-sm-2" for="login">Login:</label>
            <div class="col-sm-10">
                <input type="text" class="form-control" id="login" placeholder="Enter login" name="login">
            </div>
        </div>
        <div class="form-group">
            <label class="control-label col-sm-2" for="email">Email:</label>
            <div class="col-sm-10">
                <input type="email" class="form-control" id="email" placeholder="Enter email" name="email">
            </div>
        </div>
        <div class="form-group">
            <label class="control-label col-sm-2" for="password">Password:</label>
            <div class="col-sm-10">
                <input type="password" class="form-control" id="password" placeholder="Enter password" name="password">
            </div>
        </div>
        <div class="form-group">
            <label class="control-label col-sm-2" for="role_id">Role:</label>
            <div class="col-sm-10">
                <select class="form-control" id= "role_id" name="role_id">
                    <option value="1">Admin</option>
                    <option value="2" selected>User</option>
                </select>
            </div>
        </div>
        <div class="form-group">
            <label class="control-label col-sm-2" for="country">Country:</label>
            <div class="col-sm-10">
                <select class="form-control" id= "country" name="country" onchange="fillInCities()">
                   //from ajax
                </select>
            </div>
        </div>
        <div class="form-group">
            <label class="control-label col-sm-2" for="city">City:</label>
            <div class="col-sm-10">
                <select class="form-control" id= "city" name="city">
                    //from ajax
                </select>
            </div>
        </div>
        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
                <button type="submit" class="btn btn-default" onclick="return validate()">Add new user</button>
            </div>
        </div>
    </form>
</div>
</body>
</html>
