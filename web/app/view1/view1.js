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

                   if  (getFrom.length > 0 && getDate.length > 0) {
     
                         var items = [];
                        if (getTo.length < 1) {
                         searchstring = "http://dummyairline6-pagh.rhcloud.com/dummyAirline6/api/flightinfo/" + 
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
                    $scope.firmName = response.data.name;
                    $scope.firm = response.data;
                    $scope.productionunits = response.data.productionunits;
                    console.log($scope.firm);


                    
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
});