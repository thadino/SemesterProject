'use strict';


var app = angular.module('myApp.view4', ['ngRoute']);

app.config(['$routeProvider', function($routeProvider) {
  $routeProvider.when('/view4', {
    templateUrl: 'app/view4/view4.html',
    controller: 'View4Ctrl'
  });
}]);

app.controller('View4Ctrl', function($http,$scope) {
    


    
    var ip = window.location.host;
    var mywebsiteName = "SemesterSeed";
    
    var errorid = "error_view4";
    

if($scope.isAdmin === false)
{
  window.location.assign("#/view1");  
}
    
  $http.get('api/demoadmin')
            .success(function (data, status, headers, config) {
           
            })
            .error(function (data, status, headers, config) {
              
             }); 
     


          
   $scope.getreservations = function()
   {
   
         

             
 $http({
            method: 'GET',
            url: "/SemesterSeed/api/api/reservation/all"
          }).then(function successCallback(res) {
    
             $scope.reservations = res.data;
             $scope.passengerlist = res.data.passengers;

          }, function errorCallback(res, status) {
                    document.getElementById(errorid).innerHTML = "Failed to retrieve all reservations.";
                    console.log("Returned status code : " + status);
          });

     
     
   };
   
   
   
   
      $scope.getairlines = function()
   {
   
         

             
 $http({
            method: 'GET',
            url: "/SemesterSeed/api/api/airline/all"
          }).then(function successCallback(res) {
    
             $scope.airlines = res.data;

          }, function errorCallback(res, status) {
                    document.getElementById(errorid).innerHTML = "Failed to retrieve all reservations.";
                    console.log("Returned status code : " + status);
          });

     
     
   };
   
   
      $scope.newuser = function()
   {
       
        var fullname = document.getElementById("").innerHTML;
        var email = document.getElementById("").innerHTML;
        var password = document.getElementById("").innerHTML;
        
        var JsonToSend = "";

                   $.ajax({
                    url: ip + "/"+ mywebsiteName +"/api/api/reservation/" + email + "/",
                    type: "POST",
                    data: JsonToSend,
                    contentType: "application/json",
                    dataType: "json",
                    success: function (data, status) {
                       //  success code
                       
                       document.getElementById("").innerHTML = "Successfully created a new user.";
                       
                    },
                    error: function(data, status){
                   //  error code
                    document.getElementById(errorid).innerHTML = "Failed to created a new user.";
                    console.log("Returned status code : " + status);
                   
                    }
                });
     
     
   };
   
         $scope.newairline = function()
   {
       
        var airlineURL = document.getElementById("").innerHTML;
        
        var JsonToSend = "";

                   $.ajax({
                    url: ip + "/"+ mywebsiteName +"/api/api/airline/" + airlineURL + "/",
                    type: "POST",
                    data: JsonToSend,
                    contentType: "application/json",
                    dataType: "json",
                    success: function (data, status) {
                       //  success code
                       
                       document.getElementById("").innerHTML = "Successfully created a new airline.";
                       
                    },
                    error: function(data, status){
                   //  error code
                    document.getElementById(errorid).innerHTML = "Failed to created a new airline.";
                    console.log("Returned status code : " + status);
                   
                    }
                });
     
     
   };
   
            $scope.deleteairline = function(id)
   {
       
        
                   
//                   $.ajax({
//                    url: "/SemesterSeed/api/api/airline/" + id,
//                    type: "DELETE",
//                    contentType: "application/json",
//                    dataType: "json",
//                    data: "somedata",
//                    success: function (data, status) {
//                       //  success code
//                       
//                       document.getElementById("").innerHTML = "Successfully deleted a new airline.";
//                       
//                    },
//                    error: function(data, status){
//                   //  error code
//                    document.getElementById(errorid).innerHTML = "Failed to deleted a new airline.";
//                    console.log("Returned status code : " + status);
//                   
//                    }
//                });
                
                $.ajax({
    url: "/SemesterSeed/api/api/airline/" + id,
    type: 'DELETE',
    success: function(result) {
        // Do something with the result
        
        alert("deleted");
        $scope.getairlines();
    },   
    error: function(result, status){
                   //  error code
                    document.getElementById(errorid).innerHTML = "Failed to deleted a new airline.";
                    console.log("Returned status code : " + status);
                   
                    }
});
     
     
   };
   
   
   
         $scope.deleteuser = function()
   {
       
        var fullname = document.getElementById("").innerHTML;
        var email = document.getElementById("").innerHTML;
        var password = document.getElementById("").innerHTML;


                   $.ajax({
                    url: ip + "/"+ mywebsiteName +"/api/api/reservation/" + email + "/",
                    type: "DELETE",
                    data: JsonToSend,
                    contentType: "application/json",
                    dataType: "json",
                    success: function (data, status) {
                       //  success code
                       
                       document.getElementById("").innerHTML = "Successfully edited this user.";
                       
                    },
                    error: function(data, status){
                   //  error code
                    document.getElementById(errorid).innerHTML = "Failed to edit this user.";
                    console.log("Returned status code : " + status);
                   
                    }
                });
     
     
   };
   
   
            $scope.edituser = function()
   {
       
        var fullname = document.getElementById("").innerHTML;
        var email = document.getElementById("").innerHTML;
        var password = document.getElementById("").innerHTML;
        
        var JsonToSend = "";

                   $.ajax({
                    url: ip + "/"+ mywebsiteName +"/api/api/reservation/" + email + "/",
                    type: "PUT",
                    data: JsonToSend,
                    contentType: "application/json",
                    dataType: "json",
                    success: function (data, status) {
                       //  success code
                       
                       document.getElementById("").innerHTML = "Successfully edited this user.";
                       
                    },
                    error: function(data, status){
                   //  error code
                    document.getElementById(errorid).innerHTML = "Failed to edit this user.";
                    console.log("Returned status code : " + status);
                   
                    }
                });
     
     
   };
   
   
   
   $scope.getusers = function()
   {
       
       $http({
            method: 'GET',
            url: "/SemesterSeed/api/api/reservation/all"
          }).then(function successCallback(res) {
    
             $scope.reservations = res.data;
             $scope.passengerlist = res.data.passengers;

          }, function errorCallback(res, status) {
                    document.getElementById(errorid).innerHTML = "Failed to retrieve all reservations.";
                    console.log("Returned status code : " + status);
          }); 
          
   }
   
   

   
   
   

              
          });
          
          
          
          function activateuserview()
          {
           resetviews();
            $("#adminmenu").hide(); 
     $("#getallusers").show();
     $("#createuser").show();
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
          
          
          
    function resetviews ()
          {
     $("#error_view4").innerHTML = ""; // if you reset views there should be no error message shown.
     $("#adminmenu").show();           
     $("#getallreservations").hide();
     $("#getallusers").hide();
     $("#createuser").hide();
     $("#getallairlines").hide();
     $("#createairline").hide();
          };
          
          
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
