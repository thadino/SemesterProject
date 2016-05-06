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

//    $scope.start = function(){
//        $scope.startBooking();
//    };
//    
//    $scope.startBooking = function(){
//            var number = document.getElementById("member").value;
//            var container = document.getElementById("container");
//            while (container.hasChildNodes()) {
//                container.removeChild(container.lastChild);
//            }
//            for (i=0;i<number;i++){
//                container.appendChild(document.createTextNode("Member " + (i+1)));
//                var input = document.createElement("input");
//                input.type = "text";
//                container.appendChild(input);
//                container.appendChild(document.createElement("br"));
//            }
//        };

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
    alert(document.getElementById("passengers"));
    while (container.hasChildNodes()) {
        container.removeChild(container.lastChild);
    }
    for (i = 0; i < number; i++) {
        container.appendChild(document.createTextNode("Passenger " + (i + 1) + " "));
        var input = document.createElement("input");
        input.type = "text";
        container.appendChild(input);
        container.appendChild(document.createElement("br"));
        container.appendChild(document.createElement("br"));
    }
}
;




function switchPage() {

    $("#result").hide();
    $("#bookingPage").show();
}
;





