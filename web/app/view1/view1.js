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
    

    var ip = window.location.host;
    
    var websiteURL = "http://" + ip + "/SemesterSeed/api/api/flights/" 
//  
// "http://" + ip + "/SemesterSeed/api/api/flights/";
    var mywebsiteName = "SemesterSeed";
    
    
<<<<<<< HEAD
=======
    $("#mylist").hide();

>>>>>>> develop

    
    
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
     document.getElementById("errormessage_view1").innerHTML = "";


};

$scope.bookvacation = function()
{
 
  //  alert("Airline URL: " + AirlineURL + " Flight ID: " + FlightID);
    
    
 
        
       
    
    reserveePhone = $("#phone").val();
    reserveeName = $("#name").val();
    reserveeEmail = $("#email").val();
    numberofseats = $("#member").val();
  
  var alertstring = "";
  
  var nrofseats = numberofseats;
  var phone = reserveePhone;   
  nrofseats = nrofseats.replace(/[^0-9]/g, '');
phone = phone.replace(/[^0-9]/g, '');
if(phone.length !== 8) 
   alertstring += "You're Phone number is suppoused to be within 8 digits.<br>";

       if(!validateEmail(reserveeEmail))
        alertstring += "You need to provide a valid email.<br>";
        
       if(nrofseats.length < 1)
           alertstring += "You need some travel companions. <br>";
       
       
       if(reserveeName.length < 1)
           alertstring += "John you need a name, like llama";
        
   
    var erroronnames = "";
    var times = -1;
 $('.myawesomefuckingclass').each(function(i, obj) {
     
     if(obj.value === "")
     {
      erroronnames =  "Ooho ho there cowboy you need to provide a name and lastname for all your traveling companions.";   
     
     }
     times++;
     
    
}); 

  alertstring += erroronnames;
  
        if(alertstring.length !== 0)
        {
        document.getElementById("errormessage_view1").innerHTML = alertstring;
        }
        
        else
        {
    document.getElementById("errormessage_view1").innerHTML = ""; 

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

<<<<<<< HEAD
=======






>>>>>>> develop
var JSONtoSend = "";
 JSONtoSend = '{ "url" : "http://dummyairline6-pagh.rhcloud.com/dummyAirline6/api/reservation/",'+
 '"flightID":"'+ FlightID + '",' +
 '"numberOfSeats":"'+numberofseats+'",' +
 '"reserveeName":"'+reserveeName+'",' +
 '"reserveePhone":"'+reserveePhone+'",' +
 '"reserveeEmail":"'+reserveeEmail+'",' +
 '"passengers" :[' +
 myString +
 ']}';
 
// var JSONtoSend = "";
// JSONtoSend = '{ "url" : "'+ AirlineURL +'/",'+
// '"flightID":"'+ FlightID + '",' +
// '"numberOfSeats":"'+numberofseats+'",' +
// '"reserveeName":"'+reserveeName+'",' +
// '"reserveePhone":"'+reserveePhone+'",' +
// '"reserveeEmail":"'+reserveeEmail+'",' +
// '"passengers" :[' +
// myString +
// ']}'; backup of this shit..
 
//  '{"firstName":"'+FlightID+'","lastName":"'+FlightID+'"},' +
// '{"firstName":"'+FlightID+'","lastName":"'+FlightID+'"},' +
//  '{"firstName":"'+FlightID+'","lastName":"'+FlightID+'"}' +

var send = {
 "url" : "http://dummyairline6-pagh.rhcloud.com/dummyAirline6/api/reservation/",
 "flightID":"CPH-STN-020",
 "numberOfSeats":3,
 "reserveeName":"johny",
 "reserveePhone":"12345678",
 "reserveeEmail":"peter@peter.com",
 "passengers":[
 {
 "firstName":"Petera",
 "lastName":"Peterson"
 },
 {
 "firstName":"Jane",
 "lastName":"Peterson"
 },
  {
 "firstName":"Peter",
 "lastName":"Hansen"
 }
 ]
};

//$.post("/"+ mywebsiteName +"/api/api/reservation/",
//        send,
//        function(data,status){
//            alert("Data: " + data + "\nStatus: " + status);
//        });



var json = JSON.stringify(send);

//        $.ajax({
//      type: "POST",
//      url: "/"+ mywebsiteName +"/api/api/reservation/",
//      data: json,
//      contentType: "application/json",
//      success: function(data, status) {
//        alert("Data: " + data + "\nStatus: " + status);
//      }
//    });
    
                   $.ajax({
                    url: "/"+ mywebsiteName +"/api/api/reservation/" + FlightID + "/",
                    type: "POST",
                    data: JSONtoSend,
                    contentType: "application/json",
                    dataType: "json",
                    success: function (data, status) {
                       //  success code
                       
                       window.location.href = "http://" + ip + "/" + mywebsiteName + "/#/view2";
                       
                    },
                    error: function(data, status){
                   //  error code
                   
                    }
                });
  
  
    
 console.log(JSONtoSend);


   
        } // if validation failed will not do code encapsled.



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
               
               searchstring = websiteURL +
                        getFrom + "/" + getDate + time + "/" + tickets;
            }
            else
            {
                searchstring = websiteURL +
                        getFrom + "/" + getTo + "/" + getDate + time + "/" + tickets;
            }
            
            console.log(searchstring);

            $http({method: 'GET', url: searchstring,
                skipAuthorization: true})
                    .then(function (response) {
                        
             
                if(Object.keys(response.data[0].flightOffer.flights).length === 0)
                {
                    $scope.test = [];
                   document.getElementById("resulterrormessage").innerHTML = "No Flights found, try with other search values.";
                   document.getElementById("result").hide();
                }
                else
                {
                    $("#resulterrormessage").innerHTML = "";
                    $("#result").show();
                        $scope.fl = [];
                        

                        $scope.test = response.data;
                    }


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
            $("#result").show();
    $("#bookingPage").hide();
    };

//    $scope.book = function (fID) {
//
//        $scope.makeBooking(fID);
//
//
//    };
//
//
//    $scope.makeBooking = function (fID) {
//
//        $http({method: 'POST', url: "/SemesterSeed/api/api/reservation/" + fID,
//            skipAuthorization: true})
//                .success(function (response) {
//                    alert("it works");
//                })
//                .error(function (data, status) {
//                    alert(status);
//
//                });
//    };
});



<<<<<<< HEAD
=======


>>>>>>> develop
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


if(number > 10)
{
document.getElementById("errormessage_view1").innerHTML = "Dude... dont try to crash our server! (max 10 passengers)";
}
else
{

document.getElementById("errormessage_view1").innerHTML = "";
    var container = document.getElementById("passengers");

    while (container.hasChildNodes()) {
        container.removeChild(container.lastChild);
    }
<<<<<<< HEAD
=======
    
>>>>>>> develop
    for (var i = 0; i < number; i++) {
        container.appendChild(document.createTextNode("Passenger"));
        var input = document.createElement("input");
        var input2 = document.createElement("input");
        input.type = "text";
        input.placeholder = "firstname";
        input.style = "margin-left:15px;";
        input.className = "myawesomefuckingclass";
        
<<<<<<< HEAD
                container.appendChild(input);
=======
        container.appendChild(input);
>>>>>>> develop
        
        input2.type = "text";
        input2.placeholder = "lastname";
        input2.style = "margin-left:15px;";
        input2.className = "myawesomefuckingclass";

        container.appendChild(input2);
        container.appendChild(document.createElement("br"));
        container.appendChild(document.createElement("br"));
    }
    }
}




function validateEmail(email) {
    var re = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
    return re.test(email);
}



