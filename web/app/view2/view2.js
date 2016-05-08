'use strict';

angular.module('myApp.view2', ['ngRoute'])

        .config(['$routeProvider', function ($routeProvider) {
            $routeProvider.when('/view2', {
              templateUrl: 'app/view2/view2.html',
              controller: 'View2Ctrl'
            });
          }])

        .controller('View2Ctrl', function ($http, $scope) {
            
          $http({
            method: 'GET',
            url: 'api/demouser'
          }).then(function successCallback(res) {
            $scope.data = res.data.message;
          }, function errorCallback(res) {
            $scope.error = res.status + ": "+ res.data.statusText;
          });
          
                    
          $scope.searchmail = function()
          {
              
              var mail = $("#searchmail").val();
              
                 $http({
            method: 'GET',
            url: "/SemesterSeed/api/api/reservation/" + mail
          }).then(function successCallback(res) {
    
             $scope.mydata = res.data;

          }, function errorCallback(res) {
            
          });
          


};


        });