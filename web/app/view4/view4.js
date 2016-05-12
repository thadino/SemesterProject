
'use strict';


var app = angular.module('myApp.view4', ['ngRoute']);

app.config(['$routeProvider', function ($routeProvider) {
        $routeProvider.when('/view4', {
            templateUrl: 'app/view4/view4.html',
            controller: 'View4Ctrl'
        });
    }]);

app.controller('View4Ctrl', function ($http, $scope) {




    var ip = window.location.host;
    var mywebsiteName = "SemesterSeed";




    if ($scope.isAdmin === false)
    {
        window.location.assign("#/view1");
    }

    $http.get('api/demoadmin')
            .success(function (data, status, headers, config) {

            })
            .error(function (data, status, headers, config) {

            });




    $scope.getreservations = function ()
    {




        $http({
            method: 'GET',
            url: "/SemesterSeed/api/api/reservation/all"
        }).then(function successCallback(res) {

            $scope.reservations = res.data[0];
            console.log($scope.reservations);
            $scope.passengerlist = res.data.passengers;

        }, function errorCallback(res, status) {
            document.getElementById("getallreservationsID").innerHTML = "Failed to retrieve all reservations.";
            console.log("Returned status code : " + status);
        });



    };

    $scope.getairlines = function ()
    {




        $http({
            method: 'GET',
            url: "/SemesterSeed/api/api/airline/all"
        }).then(function successCallback(res) {

            $scope.airlines = res.data;
            var length = res.data.length;
    

            while(length > 0)
            {
                
                if(length > 4)
                {
                  length -= 5;  
                }  
                    else
                {
                  length = 0;  
                }
                
            }

        }, function errorCallback(res, status) {
            document.getElementById("createairlineerrorid").innerHTML = "Failed to retrieve all reservations.";
            console.log("Returned status code : " + status);
        });



    };
   
    
//    
//    var airlinelimitfrom;
//    var airlinelimitto;
//    if(airlinelimitfrom == null)
//    airlinelimitfrom = 0;
//
//    if(airlinelimitto == null)
//     airlinelimitto = 10;
//
//
//$scope.setairlinelimit = function(from, to)
//{
//  $scope.getairlinelimitfrom = from;
//  $scope.getairlinelimitto = to;
//};
//    
//    
//$scope.getairlinelimitfrom = airlinelimitfrom;
//$scope.getairlinelimitto = airlinelimitto; did not get this to work..
// was tryint to limit output and then u could click a button to get next 5 in the repeater.

    $scope.newuser = function ()
    {

        var fullname = document.getElementById("newusername").value;
        var email = document.getElementById("newusermail").value;
        var password = document.getElementById("newuserpassword").value;
        var role = $('#roledropdown').val();
        var JsonToSend = "{" +
                '"userName" : "' + fullname + '",' +
                '"passWord" : "' + password + '",' +
                '"email" : "' + email + '",' +
                '"role" : "' + role + '"}';

        var errorcheck = "";
        if (fullname.length < 1)
            errorcheck += "You need to enter a name <br>";
        if (email.length < 1)
            errorcheck += "You need to enter a email <br>";
        if (password.length < 1)
            errorcheck += "You need to enter a password <br>";


        if (!errorcheck.length > 0)
        {
            $.ajax({
                url: "http://" + ip + "/" + mywebsiteName + "/api/api/user/new/",
                type: "POST",
                data: JsonToSend,
                contentType: "application/json",
                dataType: "json",
                success: function (data, status) {
                    //  success code

                    document.getElementById("createuserid").innerHTML = "Successfully created a new user.";
                    $scope.getusers();
                },
                error: function (data, status) {
                    //  error code
                    document.getElementById("createuserid").innerHTML = "Failed to created a new user.";
                    console.log("Returned status code : " + status);

                }
            });

        }
        else
        {
            document.getElementById("createuserid").innerHTML = errorcheck;
        }


    };



    $scope.newairline = function ()
    {



        var NAME = document.getElementById("newairlinename").value;
        var URLLINK = document.getElementById("newairlinelink").value;

        var JsonToSend = "{" +
                '"name" : "' + NAME + '",' +
                '"url" : "' + URLLINK + '"}';



        if (!NAME.length < 1 && !URLLINK < 1)
        {

            $.ajax({
                url: "http://" + ip + "/" + mywebsiteName + "/api/api/airline/",
                type: "POST",
                data: JsonToSend,
                contentType: "application/json",
                dataType: "json",
                success: function (data, status) {
                    //  success code
                    document.getElementById("createairlineerrorid").innerHTML = "Successfully created a new airline.";
                    $scope.getairlines();
                },
                error: function (data, status) {
                    //  error code
                    document.getElementById("createairlineerrorid").innerHTML = "Failed to created a new airline.";
                    console.log("Returned status code : " + status);
                }
            });
        }
        else
        {
            document.getElementById("createairlineerrorid").innerHTML = "You need to enter values to create a new airline..";
        }




    };

    $scope.deleteairline = function (airlineURL)
    {

if (confirm("Are you sure you wanna delete this airline?") == true) {
  


        var JsonToSend = "";

        $.ajax({
            url: "http://" + ip + "/" + mywebsiteName + "/api/api/airline/" + airlineURL + "/",
            type: "DELETE",
            data: JsonToSend,
            contentType: "application/json",
            dataType: "json",
            success: function (data, status) {
                //  success code

                document.getElementById("createairlineerrorid").innerHTML = "Successfully deleted a airline.";
                $scope.getairlines();

            },
            error: function (data, status) {
                //  error code
                document.getElementById("createairlineerrorid").innerHTML = "Failed to deleted a new airline.";
                console.log("Returned status code : " + status);

            }
        });

        } else {
        document.getElementById("createairlineerrorid").innerHTML = "Cancelled delete airline";
    }
    
    };

    



    $scope.deleteuser = function (id)
    {

if (confirm("Are you sure you wanna delete this user?") == true) {
        var JsonToSend = "";

        console.log("http://" + ip + "/" + mywebsiteName + "/api/api/user/" + id + "/");
        $.ajax({
            url: "http://" + ip + "/" + mywebsiteName + "/api/api/user/" + id + "/",
            type: "DELETE",
            data: JsonToSend,
            contentType: "application/json",
            dataType: "json",
            success: function (data, status) {
                //  success code

                document.getElementById("createuserid").innerHTML = "Successfully deleted this user.";
                $scope.getusers();
            },
            error: function (data, status) {
                //  error code
                document.getElementById("createuserid").innerHTML = "Failed to edit this user.";
                console.log("Returned status code : " + status);

            }
        });
        }
        else
        {
          document.getElementById("createuserid").innerHTML = "Cancelled delete of user.";  
        }

    };

    var usertoedit = "";
    var usertoeditmail = "";

    $scope.edituserview = function (name, mail, role)
    {
        usertoedit = name;
        usertoeditmail = mail;
        document.getElementById("editusername").value = name;
        document.getElementById("editusermail").value = mail;
        document.getElementById("edituserpass").value = "";
        $("#edituser").show();
        $("#getallusers").hide();
    };





    $scope.edituser = function ()
    {

        var fullname = document.getElementById("editusername").value;
        var email = document.getElementById("editusermail").value;
        var password = document.getElementById("edituserpass").value;
        var role = $('#edituserrole').val();


        var errorcheck = "";
        if (fullname.length < 1)
            errorcheck += "You need to enter a name <br>";
        if (email.length < 1)
            errorcheck += "You need to enter a email <br>";
        if (password.length < 1)
            errorcheck += "You need to enter a password <br>";


        if (!errorcheck.length > 0)
        {


            var JsonToSend = "{" +
                    '"userName" : "' + fullname + '",' +
                    '"passWord" : "' + password + '",' +
                    '"email" : "' + email + '",' +
                    '"role" : "' + role + '"}';

            $.ajax({
                url: "http://" + ip + "/" + mywebsiteName + "/api/api/user/",
                type: "PUT",
                data: JsonToSend,
                contentType: "application/json",
                dataType: "json",
                success: function (data, status) {
                    //  success code

                    document.getElementById("edituserid").innerHTML = "Successfully edited this user.";

                },
                error: function (data, status) {
                    //  error code
                    document.getElementById("edituserid").innerHTML = "Failed to edit this user.";
                    console.log("Returned status code : " + status);

                }
            });
        }
        else
        {
            document.getElementById("edituserid").innerHTML = errorcheck;
        }


    };



    $scope.getusers = function ()
    {
        $http({
            method: 'GET',
            url: "/SemesterSeed/api/api/user/all"
        }).then(function successCallback(res) {

            $scope.userscopedata = res.data;

        }, function errorCallback(res, status) {
            document.getElementById("createuserid").innerHTML = "Failed to retrieve all reservations.";
            console.log("Returned status code : " + status);
        });

    };








});



function activateuserview()
{
    resetviews();
    $("#adminmenu").hide();
    $("#getallusers").show();
    $("#createuser").show();
    document.getElementById("createuserid").innerHTML = "";
    
}


function getallreservations()
{
    resetviews();
    $("#adminmenu").hide();
    $("#getallreservations").show();
}

function activateairlineview()
{
    resetviews();
    $("#adminmenu").hide();
    $("#getallairlines").show();
    $("#createairline").show();
}



function resetviews()
{

    document.getElementById("errorid").innerHTML = "";
    document.getElementById("edituserid").innerHTML = "";
    $("#adminmenu").show();
    $("#getallreservations").hide();
    $("#getallusers").hide();
    $("#createuser").hide();
    $("#getallairlines").hide();
    $("#createairline").hide();
    $("#edituser").hide();

}

function backtousersfromedit()
{
     $("#edituser").hide();
     $("#getallusers").show();
}


app.filter('myFilterView4', function () {

    return function (obj) {
        var a = {};
        angular.forEach(obj, function (value, key) {
            if (key !== "passengers") {
                a[key] = value;
            }
        });
        return a;


    };
});


