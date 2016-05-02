function setactive(id)
{
    cleanlinks();
          
    var x = document.getElementsByClassName("view")[id - 1];

        x.id = "menu_active"; 
}

function cleanlinks()
{
    var looplenlinksli = document.getElementsByClassName("view");
    for (var i = 0; i < looplenlinksli.length; i++) {
        looplenlinksli[i].id = "";
    }
}

function setup()
{
    var page = "";
    page = window.location.href;
    var substring = "view";
    

    
    if(page.search(substring) > 0)
    {
    

        var viewid = page.slice(page.indexOf(substring) + 4, page.indexOf(substring) + 5);
        
        
        
        var x = document.getElementsByClassName("view")[viewid - 1];

        x.id = "menu_active";
    }
    else
    {
        window.location = "#/view1";
        setup();
    }

}