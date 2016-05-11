
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
       
        var fullname = document.getElementById("newusername").value;
        var email = document.getElementById("newusermail").value;
        var password = document.getElementById("newuserpassword").value;
        var role = $('#roledropdown').val();
            var JsonToSend = "{" +
    '"userName" : "'+ fullname +'",' +
    '"passWord" : "' + password + '",' +
    '"email" : "' + email + '",' +
    '"role" : "' + role + '"}';
    
    console.log(JsonToSend);
 
                   $.ajax({
                    url: "http://" + ip + "/"+ mywebsiteName +"/api/api/user/new/",
                    type: "POST",
                    data: JsonToSend,
                    contentType: "application/json",
                    dataType: "json",
                    success: function (data, status) {
                       //  success code
                       
                       document.getElementById(errorid).innerHTML = "Successfully created a new user.";
                       
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
       
        
        
        var NAME = document.getElementById("newairlinename").value;
        var URLLINK = document.getElementById("newairlinelink").value;
        
        var JsonToSend = "{" +
    '"name" : "'+ NAME +'",' +
    '"url" : "' + URLLINK + '"}';
    
    console.log(JsonToSend);

                                 $.ajax({
                                        url: "http://" + ip + "/"+ mywebsiteName +"/api/api/airline/",
                                                type: "POST",
                                                data: JsonToSend,
                                                contentType: "application/json",
                                                dataType: "json",
                                                success: function (data, status) {
                                                //  success code
                       document.getElementById(errorid).innerHTML = "Successfully created a new airline.";
                        $scope.getairlines();
                                                },
                                                error: function(data, status){
                                                //  error code
                    document.getElementById(errorid).innerHTML = "Failed to created a new airline.";
                    console.log("Returned status code : " + status);
                                                }
                                    });


     
     
   };
   
            $scope.deleteairline = function(airlineURL)
   {
       

    
        var JsonToSend = "";

                   $.ajax({
                    url: "http://" + ip + "/"+ mywebsiteName +"/api/api/airline/" + airlineURL + "/",
                    type: "DELETE",
                    data: JsonToSend,
                    contentType: "application/json",
                    dataType: "json",
                    success: function (data, status) {
                       //  success code
                       
                       document.getElementById(errorid).innerHTML = "Successfully deleted a new airline.";
                       $scope.getairlines();
                       
                    },
                    error: function(data, status){
                   //  error code
                    document.getElementById(errorid).innerHTML = "Failed to deleted a new airline.";
                    console.log("Returned status code : " + status);
                   
                    }
                });
     
     
   };
   
   
   
         $scope.deleteuser = function(id)
   {
       

              var JsonToSend = "";
 
 console.log("http://" + ip + "/"+ mywebsiteName +"/api/api/user/" + id + "/");
                   $.ajax({
                    url: "http://" + ip + "/"+ mywebsiteName +"/api/api/user/" + id + "/",
                    type: "DELETE",
                    data: JsonToSend,
                    contentType: "application/json",
                    dataType: "json",
                    success: function (data, status) {
                       //  success code
                       
                       document.getElementById(errorid).innerHTML = "Successfully deleted this user.";
                       
                    },
                    error: function(data, status){
                   //  error code
                    document.getElementById(errorid).innerHTML = "Failed to edit this user.";
                    console.log("Returned status code : " + status);
                   
                    }
                });
     
     
   };
   
   var usertoedit = "";
   var usertoeditmail = "";
   
   $scope.edituserview = function(name, mail, role)
   {
       usertoedit = name; 
       usertoeditmail = mail;
        document.getElementById("editusername").value = name;
        document.getElementById("editusermail").value = mail;
        
       $("#edituser").show();
       $("#getallusers").hide();
   };
   
   

   
   
            $scope.edituser = function()
   {
       
        var fullname = document.getElementById("editusername").value;
        var email = document.getElementById("editusermail").value;
        var password = document.getElementById("edituserpass").value;
        var role = $('#edituserrole').val();
        

        
        
        
                  var JsonToSend = "{" +
    '"userName" : "'+ fullname +'",' +
    '"passWord" : "' + password + '",' +
    '"email" : "' + email + '",' +
    '"role" : "' + role + '"}';

                   $.ajax({
                    url: "http://" + ip + "/"+ mywebsiteName +"/api/api/user/",
                    type: "PUT",
                    data: JsonToSend,
                    contentType: "application/json",
                    dataType: "json",
                    success: function (data, status) {
                       //  success code
                       
                       document.getElementById(errorid).innerHTML = "Successfully edited this user.";
                       
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
            url: "/SemesterSeed/api/api/user/all"
          }).then(function successCallback(res) {
    
             $scope.userscopedata = res.data;

          }, function errorCallback(res, status) {
                    document.getElementById(errorid).innerHTML = "Failed to retrieve all reservations.";
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
     document.getElementById("error_view4").innerHTML = "";
     document.getElementById("errorid").innerHTML = "";
     $("#adminmenu").show();           
     $("#getallreservations").hide();
     $("#getallusers").hide();
     $("#createuser").hide();
     $("#getallairlines").hide();
     $("#createairline").hide();
     $("#edituser").hide();
     
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

