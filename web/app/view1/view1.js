'use strict';

angular.module('myApp.view1', ['ngRoute'])

        .config(['$routeProvider', function ($routeProvider) {
                $routeProvider.when('/view1', {
                    templateUrl: 'app/view1/view1.html',
                    controller: 'View1Ctrl',
                    controllerAs: 'ctrl'
                });
            }])

        .controller('View1Ctrl', ["InfoFactory", "InfoService", function (InfoFactory, InfoService, $http) {
                this.msgFromFactory = InfoFactory.getInfo();
                this.msgFromService = InfoService.getInfo();


                function search1() {
                    var getTo = document.getElementById("searchTo").value;
                    var getFrom = document.getElementById("searchFrom").value;
                    var getDate = document.getElementById("departureDate").value;
                    var tickets = document.getElementById("tickets").value;
                    alert(getTo);



                    if (getFrom.length > 0 && getDate.length > 0) {
                        alert("firstIf");
                        if (getTo.length > 0) {
                            alert("2ndIf");
                            this.getFlightsWithoutTo = function () {
                                $http({method: 'GET', url: '/api/flights/:' + getFrom + '/:' + getDate + '/:' + tickets})
                                        .success(function (response) {
                                            this.AlName = response.data.airline;
                                            this.res1Flights = [];
                                            //Denne list har alle de specifikke 'offers' fra airline ALName
                                            this.res1Flights = response.data.flights;
                                            console.log(this.res1Flights);
                                        });

                            };
                            getFlightsWithoutTo();
                        } else {
                             alert("3rdIf");
                            this.getFlightsWithTo = function () {
                                $http({method: 'GET', url: 'api/flights/:' + getFrom + '/:' + getTo + '/' + getDate + '/:' + tickets})
                                        .success(function (response) {
                                            this.AlName2 = response.data.airline;
                                            this.res2Flights = [];
                                            //Denne list har alle de specifikke 'offers' fra airline ALName
                                            this.res2Flights = response.data.flights;
                                            console.log(this.res2Flights);
                                        });
                            };
                            this.getFlightsWithTo();
                        }
                    }
                };

            }]);





//        .controller('search', ["search", function ($scope, $http) {
//                
//                $scope.search = function () {
//                var getTo = document.getElementById("searchTo");
//                var getFrom = document.getElementById("searchFrom");
//                var getDate = document.getElementById("departureDate");
//                var tickets = document.getElementById("tickets");
//                alert("search");
//
//                if (getFrom !== null && getDate !== null) {
//                    if (getTo === null) {
//                        $scope.getFlightsWithoutTo = function () {
//                            $http({method: 'GET', url: '/api/flights/:' + getFrom + '/:' + getDate + '/:' + tickets})
//                                    .success(function (response) {
//                                        $scope.AlName = response.data.airline;
//                                        $scope.res1Flights = [];
//                                        //Denne list har alle de specifikke 'offers' fra airline ALName
//                                        $scope.res1Flights = response.data.flights;
//                                        console.log($scope.res1Flights);
//                                    });
//
//                        };
//
//                    }
//                    $scope.getFlightsWithTo = function () {
//                        $http({method: 'GET', url: 'api/flights/:'+ getFrom +'/:'+ getTo +'/' + getDate + '/:' + tickets})
//                                .success(function (response) {
//                                    $scope.AlName2 = response.data.airline;
//                                    $scope.res2Flights = [];
//                                    //Denne list har alle de specifikke 'offers' fra airline ALName
//                                    $scope.res2Flights = response.data.flights;
//                                    console.log($scope.res2Flights);
//                                });
//                    };
//
//                }
//            };
//
//            }]);

