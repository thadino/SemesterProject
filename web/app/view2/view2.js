'use strict';

angular.module('myApp.view2', ['ngRoute'])

        .config(['$routeProvider', function ($routeProvider) {
                $routeProvider.when('/view2', {
                    templateUrl: 'app/view2/view2.html',
                    controller: 'View2Ctrl'
                });
            }])



        .filter('myFilter2', function () {

            return function (obj) {
                var a = {};
                angular.forEach(obj, function (value, key) {
                    if (key !== "passengers") {
                        a[key] = value;
                    }
                });
                return a;
            }
        })






        .controller('View2Ctrl', function ($http, $scope) {

            $http({
                method: 'GET',
                url: 'api/demouser'
            }).then(function successCallback(res) {
                $scope.data = res.data.message;
            }, function errorCallback(res) {
                $scope.error = res.status + ": " + res.data.statusText;
            });



            $http({
                method: 'GET',
                url: "/SemesterSeed/api/api/reservation/" + $scope.username,
            }).then(function successCallback(res) {

                $scope.mydata = res.data;

                console.log($scope.mydata);


            }, function errorCallback(res) {

            });






        });



        