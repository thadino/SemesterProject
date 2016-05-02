var setactive = function (viewid)
{
    
   
   
    
   var looplenlinksli = document.getElementsByClassName("view");
    for (var i = 0; i < looplenlinksli.length; i++) {
     looplenlinksli[i].id = "";
    }


    


   var x = document.getElementsByClassName("view")[viewid-1];
   
   x.id = "menu_active";
   
   

        
        
};