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


    $scope.records = [
        "Alfreds Futterkiste",
        "Berglunds snabbköp",
        "Centro comercial Moctezuma",
        "Ernst Handel",
    ];

                    var getTo = document.getElementById("searchTo").value;
                    var getFrom = document.getElementById("searchFrom").value;
                    var getDate = document.getElementById("departureDate").value;
                    var tickets = document.getElementById("tickets").value;
                    
                    var searchstring = "";

                   if  (getFrom.length > 0 && getDate.length > 0) {
                      //  alert("firstIf");
                         var items = [];
                        if (getTo.length < 1) {
                         searchstring = "http://cvrapi.dk/api?search=cph&country=dk";
                        }
                        else
                        {
                            searchstring = "http://cvrapi.dk/api?search=leffe&country=dk";
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
                alert("you need to give a from destination, and depature date at minimum");
            }
                
      };

              $scope.search = function ()
    {
        $scope.getfromapi();
    };   
});