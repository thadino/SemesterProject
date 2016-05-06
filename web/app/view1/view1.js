'use strict';

var app = angular.module('myApp.view1', ['ngRoute'])

        .config(['$routeProvider', function ($routeProvider) {
                $routeProvider.when('/view1', {
                    templateUrl: 'app/view1/view1.html',
                    controller: 'View1Ctrl',
                    controllerAs: 'ctrl'
                });
            }]);

app.controller('View1Ctrl', function ($scope, $http)
{


  $("#bookingPage").hide();

if(AirlineURL !== null) // so it does not reset the value of AirlineURL
var AirlineURL;

if(FlightID !== null) // so it does not reset the value of flightID
var FlightID;

if(numberofseats !== null) // so it does not reset the value of flightID
var numberofseats;

if(reserveePhone !== null) // so it does not reset the value of flightID
var reserveePhone;

if(reserveeName !== null) // so it does not reset the value of flightID
var reserveeName;

if(reserveeEmail !== null) // so it does not reset the value of flightID
var reserveeEmail;


$scope.switchPage = function(url, id) {
    AirlineURL = url;
    FlightID = id;
    
    
    
    
    
    
    

    
    $("#result").hide();
    $("#bookingPage").show();
};

$scope.bookvacation = function()
{
  
  //  alert("Airline URL: " + AirlineURL + " Flight ID: " + FlightID);
    

    reserveePhone = $("#phone").val();
    reserveeName = $("#name").val();
    reserveeEmail = $("#email").val();
    numberofseats = $("#member").val();
    
    var times = -1;
 $('.myawesomefuckingclass').each(function(i, obj) {
     
     times++;
     
    
});  

var myString = ""; 

 $('.myawesomefuckingclass').each(function(i, obj) {

     if(i%2 === 0)
     myString += '{"firstName":"'+obj.value+'",';
 
     if(i%2 === 1 && i !== times )
     myString += '"lastName":"'+obj.value +'"},';
 
     if(i === times)
     {
      myString += '"lastName":"'+obj.value +'"}';  
     }
 
    
     
    
});

var JSONtoSend = "";
 JSONtoSend = '{ "url" : "'+ AirlineURL +'",'+
 '"flightID":"'+ FlightID + '",' +
 '"numberOfSeats":"'+numberofseats+'",' +
 '"reserveeName":"'+reserveeName+'",' +
 '"reserveePhone":"'+reserveePhone+'",' +
 '"reserveeEmail":"'+reserveeEmail+'",' +
 '"passengers" :[' +
 myString +
 ']}';
 
//  '{"firstName":"'+FlightID+'","lastName":"'+FlightID+'"},' +
// '{"firstName":"'+FlightID+'","lastName":"'+FlightID+'"},' +
//  '{"firstName":"'+FlightID+'","lastName":"'+FlightID+'"}' +
 console.log(JSONtoSend);

   




};







    $scope.getfromapi = function () {



        var getTo = document.getElementById("searchTo").value;
        var getFrom = document.getElementById("searchFrom").value;
        var getDate = document.getElementById("departureDate").value;
        var tickets = document.getElementById("tickets").value;
        var time = "T00:00:00Z";

        var searchstring = "";

        if (getFrom.length > 0 && getDate.length > 0) {

            var items = [];
            if (getTo.length < 1) {
                searchstring = "http://localhost:8080/SemesterSeed/api/api/flights/" +
                        getFrom + "/" + getDate + time + "/" + tickets;
            }
            else
            {
                searchstring = "http://dummyairline6-pagh.rhcloud.com/dummyAirline6/api/flightinfo/" +
                        getFrom + "/" + getTo + "/" + getDate + time + "/" + tickets;
            }

            $http({method: 'GET', url: searchstring,
                skipAuthorization: true})
                    .then(function (response) {
                        $scope.fl = [];
                        

                        $scope.test = response.data;


                    });
        }
        else
        {
            alert("you need to give a start destination, depature date and number of tickets as a minimum");
        }

    };

    $scope.search = function ()
    {
        $scope.getfromapi();
    };

    $scope.book = function (fID) {

        $scope.makeBooking(fID);


    };


    $scope.makeBooking = function (fID) {

        $http({method: 'POST', url: "/SemesterSeed/api/api/reservation/" + fID,
            skipAuthorization: true})
                .success(function (response) {
                    alert("it works");
                })
                .error(function (data, status) {
                    alert(status);

                });
    };
});


app.filter('myFilter', function () {

    return function (obj) {
        var a = {};
        angular.forEach(obj, function (value, key) {
            if (key !== "flightID") {
                a[key] = value;
            }
        });
        return a;


    };
});



function addFields() {
    var number = document.getElementById("member").value;

    var container = document.getElementById("passengers");

    while (container.hasChildNodes()) {
        container.removeChild(container.lastChild);
    }
    for (var i = 0; i < number; i++) {
        container.appendChild(document.createTextNode("Passenger"));
        var input = document.createElement("input");
        var input2 = document.createElement("input");
        input.type = "text";
        input.placeholder = "firstname";
        input.style = "margin-left:15px;";
        input.className = "myawesomefuckingclass";
        
                container.appendChild(input);
        
        input2.type = "text";
        input2.placeholder = "lastname";
        input2.style = "margin-left:15px;";
        input2.className = "myawesomefuckingclass";

        container.appendChild(input2);
        container.appendChild(document.createElement("br"));
        container.appendChild(document.createElement("br"));
    }
}








