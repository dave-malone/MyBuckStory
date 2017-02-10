//++++++++++++++++++++++++++++++++++++

// YUI textarea

// 4-12-2008 - Edwart Visser

// maximum amount of character in textarea

//

// HOW TO USE:

// write textarea as follows <textarea class="max" rel="20"></textarea>

// class "max" is needed

// rel defines the maximum amount of characters

//

// REQUIRES: yahoo-dom-event.js

//++++++++++++++++++++++++++++++++++++

var Dom = YAHOO.util.Dom;

var Event = YAHOO.util.Event;



YAHOO.namespace("lutsr");



YAHOO.lutsr.maxTextarea = {

	init : function() {

		// get all textarea's and extend them

		var textareaEls = Dom.getElementsByClassName("max");

		if(textareaEls.length > 0) {

			for(var i=0; i < textareaEls.length; i++) {

				this.extend(textareaEls[i]);

			}

		}

	},

	extend : function(el) {

		el.maxChars = el.getAttribute("rel");

		// add display of amount of characters in textarea

		var statusDisplay = document.createElement("div");

		statusDisplay.className = "display";

		el.statusDisplayEl = statusDisplay;

		

		// set status text

		var statusText = "You have " + el.maxChars + " characters left <span class='gray'>(150 character total)</span>.";

		statusDisplay.innerHTML = statusText;

		el.parentNode.insertBefore(statusDisplay,el);



		// add onchange event

		Event.addListener(el,"keyup",this.changeStatusMessage,el);

	},

	changeStatusMessage : function(e,el) {

		var curLength = el.value.length;

		var leftLength = el.maxChars - curLength;

		if(leftLength > 0) {

			// no problem

			if(Dom.hasClass(el.statusDisplayEl,"error")){

				Dom.removeClass(el.statusDisplayEl,"error")

			}

			var statusText = "You have " + leftLength + " characters left <span class='gray'>(150 character total)</span>.";

			el.statusDisplayEl.innerHTML = statusText;

		} else {

			// string length too long

			if(!Dom.hasClass(el.statusDisplayEl,"error")){

				Dom.addClass(el.statusDisplayEl,"error")

			}

			var statusText = "You're message has too many characters. Please limit your message to 150 characters";

			el.statusDisplayEl.innerHTML = statusText;

		}

	}

}



initPage = function() {

	YAHOO.lutsr.maxTextarea.init();

}



Event.on(window,"load",initPage);