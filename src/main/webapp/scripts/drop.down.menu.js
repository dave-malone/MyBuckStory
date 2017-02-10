$(function() {
//toggle the dropdown
    $("img#drop-down-menu-icon").click(function() { 
    	//.show()
        $(this).parent().find("ul.subnav").toggle('slow'); //Drop down the subnav on click 
        
        return false;
    });
    /*
    //handle the subnav
    $("ul.subnav li").hover(function(){
        //do nothing    	
    }, function(){
        $(this).parent().toggle('slow');        
    });  
    */
});  