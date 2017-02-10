YAHOO.namespace("example.calendar");
YAHOO.namespace("example.container");

/* Calendar within Create Event Dialog */
YAHOO.example.calendar.init = function() {
	
	// Enable navigator with a custom configuration
	var navConfig = {
		strings : {
			month: "Choose Month",
			year: "Enter Year",
			submit: "OK",
			cancel: "Cancel",
			invalidYear: "Please enter a valid year"
		},
		monthFormat: YAHOO.widget.Calendar.SHORT,
		initialFocus: "year"
	};

	function mySelectHandler(type,args,obj) {
		document.getElementById('hiddenDate').value = args[0];        
	};

	
	YAHOO.example.calendar.cal2 = new YAHOO.widget.Calendar("cal2Container", {navigator: navConfig});
	YAHOO.example.calendar.cal2.selectEvent.subscribe(mySelectHandler, YAHOO.example.calendar.cal2, true);
	
	YAHOO.example.calendar.cal2.render();
}	

<!-- add friend -->
function addFriend() {		
	// Define various event handlers for Dialog
	var handleYes = function() {
		//TODO - check to see if user is logged in.  If they aren't, tell them to log in first
		//TODO - submit Ajax friend request, if the user is logged in
		_this = this;
		FriendRequestClient.sendRequest(_this.requesteeId, {callback:function(responseMsg){
			if(responseMsg){
				alert(responseMsg);					
				_this.hide();
			}else{
				alert("There was a problem servicing your request.  Please try again later.  If the problem persists, please contact the us");
			}				
		}, async:false});			
	};
	var handleNo = function() {
		this.hide();
	};

	// Instantiate the Dialog
	addFriendDialog = 
		new YAHOO.widget.SimpleDialog("addFriendDialog", 
			{ width: "300px",
			  fixedcenter: true,
			  visible: false,
			  draggable: false,
			  close: true,
			  text: "Would you like to add this friend?",
			  constraintoviewport: true,
			  buttons: [ { text:"Yes", handler:handleYes, isDefault:true },
					  { text:"No",  handler:handleNo } ]
			} );
	
	// Render the Dialog
	//addFriendDialog.render("dialogContainer");	
	//var addButton = YAHOO.util.Selector.query('.addConfirmation');
	//YAHOO.util.Event.addListener(addButton, "click", addFriendDialog.show, addFriendDialog, true);
	$('.addConfirmation').click(function(){
		addFriendDialog.render("dialogContainer");
		addFriendDialog.requesteeId = this.id;
		addFriendDialog.show();
	});

}

function removeFriend() {
	
	// Define various event handlers for Dialog
	var handlYes = function() {
		_this = this;			
		FriendRequestClient.removeFriend(_this.friendId, {callback:function(success){			
			if(success){
				var liElm = document.getElementById('friend' + _this.friendId);
				liElm.parentNode.removeChild(liElm);					
			}else{
				alert("There was a problem servicing your request.  Please try again later.  If the problem persists, please contact us");
			}
			_this.hide();									
		}, async:false});			
	};
	var handlNo = function() {
		this.hide();
	};

	// Instantiate the Dialog
	removeFriendDialog = 
		new YAHOO.widget.SimpleDialog("removeFriendDialog", 
			 { width: "300px",
			   fixedcenter: true,
			   visible: false,
			   draggable: false,
			   close: true,
			   text: "Are you sure you would like to remove this friend?",
			   constraintoviewport: true,
			   buttons: [ { text:"Yes", handler:handlYes, isDefault:true },
						  { text:"No",  handler:handlNo } ]
			 } );

	//var remFriend = YAHOO.util.Selector.query('.removeFriendButton');
	//new YAHOO.widget.Button(remFriend);
    
	$('.removeFriendButton').click(function(){
		removeFriendDialog.render("dialogContainer");
		addFriendDialog.friendId = this.id;
		removeFriendDialog.show();
	});
}

new YAHOO.widget.Button("searchButton");


/* Homepage: Recently Added photos carousel */			
YAHOO.util.Event.onContentReady("recentPhotos",function(ev){
	var carousel = new YAHOO.widget.Carousel("recentPhotos", {
				animation: {speed: 0.5},
				isCircular: true,
				numVisible: 1}
	);			
	carousel.render(); // get ready for rendering the widget
	carousel.show();   // display the widget
});



function initRTE(textAreaId, submitButtonId, width, height){

	var editor = new YAHOO.widget.SimpleEditor(textAreaId, {
		width: width ? width : '702px',
		height: height ? height : '160px',
		filterWord: true,
		collapse: true,
		toolbar: {
			buttons: [
				{ group: 'textstyle', label: 'Font Style',
					buttons: [
						{ type: 'push', label: 'Bold CTRL + SHIFT + B', value: 'bold' },
						{ type: 'push', label: 'Italic CTRL + SHIFT + I', value: 'italic' },
						{ type: 'push', label: 'Underline CTRL + SHIFT + U', value: 'underline' }
					]
				},
				{ type: 'separator' },
				{ group: 'indentlist', label: 'Lists',
					buttons: [
						{ type: 'push', label: 'Create an Unordered List', value: 'insertunorderedlist' },
						{ type: 'push', label: 'Create an Ordered List', value: 'insertorderedlist' }
					]
				},
				{ type: 'separator' },
				{ group: 'insertitem', label: 'Insert Item',
					buttons: [
						{ type: 'push', label: 'HTML Link CTRL + SHIFT + L', value: 'createlink', disabled: true }
					]
				}
			]
		}	
	});
	
	editor.render(); 
	
	$(submitButtonId).click(function(){
		editor.saveHTML();	
	});
}

YAHOO.util.Event.onDOMReady(YAHOO.example.calendar.init);	

/*Widget event listeners */
//YAHOO.util.Event.addListener(window, "load", addFriend);
//YAHOO.util.Event.addListener(window, "load", removeFriend);
	 
//var featuredTabView = new YAHOO.widget.TabView('featured-stories',{'orientation':'bottom'});
//var featuredNewsTabView = new YAHOO.widget.TabView('featured-news',{'orientation':'bottom'});
//var faq = new YAHOO.widget.TabView('frequent');
	
//initRTE('description', '#storySubmit', '600px', '350px');
//initRTE('newsDescription', '#newsSubmit');
//initRTE('eventDetails', '#submitEvent');
//initRTE('message', '#messageSubmit', 500);