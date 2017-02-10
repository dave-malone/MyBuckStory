$(document).ready(function(){
	
	$('.sent-messages-pagination-links').click(getSentMessages);	
	$('.received-messages-pagination-links').click(getReceivedMessages);
	
	$('#videoUploadForm').submit(function(){
		$('input[type=submit]', this).attr('disabled', 'disabled')
		startUploadMonitoring();
	});
	
	$('a.delete').click(function(){
		if(confirm("Are you sure you want to delete this?  This action is irreversible!")){
			$(this).parent().submit()	
		}else{
			return false
		}
	})
	
	$("#winningStoryName").autocomplete({
		source: "/story/autoComplete",
		minLength: 3,
		select: function( event, ui ) {
			$('#winningStory').val(ui.item ? ui.item.id : '')						
		}
	});
	
	clearOnClick('.emailSi');
	clearOnClick('.passwordSi');
	clearOnClick('#memberName');
	clearOnClick('#location');	
	clearOnClick('#status');
	clearOnClick('#write-wall');			
		
	$("#faq-tabs").tabs();
	$("#edit-profile-tabs").tabs();

	$('.addConfirmation').click(function(){
		FriendRequestClient.sendRequest($(this).attr('id'), {callback:function(responseMsg){
			if(responseMsg){
				alert(responseMsg);
			}else{
				alert("There was a problem servicing your request.  Please try again later.  If the problem persists, please contact the us");
			}				
		}, async:true});
	});

	$('.vote').click(function(){
		StoryRatingClient.voteStory($(this).attr('id'), {callback:function(message){
			if(message){
				alert(message);
			}else{
				alert("There was a problem servicing your request.  Please try again later.  If the problem persists, please contact us");
			}
		}, async:true});
	});

	$('#user-status').submit(function() {
		var tpi = $('#tpi').val();
		var status = $.trim($('#status').val());
		if(status != null && status != ''){
			WallClient.post(tpi, status, function(){	
				$('#curr-status').html(status);				
				$('#curr-status-comment').html('');
				$('#status').val('');
			});
		}else{
			alert('Please enter a value for your status update')			
		}
		return false;
	});

	$('#friend-status').submit(function() {
		var tpi = $('#tpi').val();
		var status = $.trim($('#write-wall').val());
		if(status != null && status != ''){
			WallClient.post(tpi, status, function(post){
				/*if(post && post.id){
					refresh()
				}else{
					alert('There was an error submitting your wall post.  If the problem persists, please contact us at admin@mybuckstory.com')
				}*/
				
				var postHtml = '<div class="log" id="log' + post.id + '">'
				postHtml += '<p class="wall-thumb">'
				postHtml += '<a href="/image.html?imageId=' + post.createdByImageId + '" title="View Picture" id="profile-thumb" >'
				postHtml += '<img alt="' + post.createdByFullName + '" src="/streamimage.do?imageId=' + post.createdByImageId + '&width=90&height=90" />'
				postHtml += '</a>'
				postHtml += '</p>'
				postHtml += '<p class="wall-message">'
				postHtml += '<b><a href="/profile/show/' + post.createdById + '" name="View Profile" class="wall-user">' + post.createdByFullName + '</a></b>'
				postHtml += '&nbsp;' + post.dateCreated
				postHtml += '&nbsp;' + post.message
				postHtml += '</p>'
				postHtml += '<p>'
				postHtml += '<a href="" class="comment-wall-post" id="wpc' + post.id + '">Comment</a>'
				postHtml += '&nbsp;&nbsp;&nbsp;<a href="" class="like-wall-post" id="wpl' + post.id + '">Like</a>'
				postHtml += '</p>'
				postHtml += '</div>'				
				postHtml += '<div class="post-comments" id="post-comments' + post.id + '">'
				postHtml += '<span id="' + post.id + 'likes"></span>'
				postHtml += '<span id="' + post.id + 'comments"></span>'				
				postHtml += '<div class="post-single-comment-form" id="wpcfdiv' + post.id + '" style="visibility: hidden">'
				postHtml += '<form class="wall-post-comment-form" id="wpcf' + post.id + '">'
				postHtml += '<input type="text" name="comment-text" id="comment-text' + post.id + '" style="width: 350px;" />'						                        
			    postHtml += '<button>Comment</button>'
		        postHtml += '</form>'                   
				postHtml += '</div>'
				postHtml += '</div>'

				$('#profile-wall').prepend(postHtml)
				$('.like-wall-post').click(likeWallPost)	
				$('.comment-wall-post').click(showWallPostCommentForm)
				$('.wall-post-comment-form').submit(submitWallPostComment)
				$('#write-wall').val('')
				
			});
		}else{
			alert('Please enter a value for your status update')			
		}			
		return false;
	});


	$('.make-profile-pic').click(function(){
		var imageId = this.id
		if(imageId){
			$.ajax({
			  url: '/profile/makeProfilePic/' + imageId,
			  success: function(data) {
				 if('success' == data){
					 alert('This image is now your profile picture');
				 }else{
					 alert(data)
				 }
			  }
			});
			
			
		}
		return false; 
	});

	$('.delete-pic').click(function(){
		var imageId = this.id
		if(imageId){
			var conf = confirm('Are you sure you want to delete this picture?  This is irreversible')
			if(conf){
				ImageClient.deleteImage(imageId, function(){
					alert('Your image has been deleted - you will now be redirected back to your profile');
					window.location.replace('/profile/photos/mine');						
				});					
			}				
		}
		return false;
	});
	
	$('.like-this-image').click(function(){
		var imageId = this.id

		if(imageId){			
			ImageClient.likeImage(imageId, function(){
				$('#like-image').remove('#' + imageId);
				$('#like-image').append('&nbsp;&nbsp;&nbsp;You like this image');
			});							
		}
		return false;
	});


	 tinyMCE.init({			
		mode : "textareas",
		theme: "advanced",
		editor_selector : "rte",
		plugins : "spellchecker",
		theme_advanced_buttons1_add : "spellchecker",
		spellchecker_languages : "+English=en-us",
	    spellchecker_rpc_url    : "/jspellchecker/google-spellchecker", //spellcheck url
		
		theme_advanced_buttons1 : "bold,italic,underline,|,bullist,numlist,|,undo,redo,|,link,unlink",
		theme_advanced_buttons2 : "",
		theme_advanced_buttons3 : "",
		theme_advanced_buttons4 : "",				 
		theme_advanced_toolbar_location : "top",				 
		theme_advanced_toolbar_align : "left",	
		theme_advanced_path : false,	
		theme_advanced_resizing : true,
		theme_advanced_resize_horizontal : false,						 
		theme_advanced_statusbar_location : "bottom",
		relative_urls : false,
		nonbreaking_force_tab : true,
		apply_source_formatting : true
	 });

		 
	 var version = $.browser.version; 
	 var ltEqIE6 = (version <= 6.0);
	 var isIE = $.browser.msie;

	 //alert('is msie? ' + isIE + '\nversion: ' + version + '\nltEqIE6: ' + ltEqIE6);
	 //alert(isIE && ltEqIE6);		
	 if(isIE && ltEqIE6){
		$('#ie6orless').css('visibility','visible')
	 }else{
		$('#ie6orless').css('visibility','hidden')
		$('#ie6orless').css('height','0px')
		$('#ie6orless').css('padding','0px')
		$('#ie6orless').css('margin','0px')
	 }
	 
	$('.like-wall-post').click(likeWallPost);	
	$('.comment-wall-post').click(showWallPostCommentForm);	
	$('.wall-post-comment-form').submit(submitWallPostComment);
});

function likeWallPost(){
	//wpl#
	var wplId = this.id;		
	var postId = wplId.replace('wpl', '');
	
	if(postId){
		WallClient.like(postId, function(like){
			if(like){
				var likeHtml = '<p class="post-like" id="wpls' + postId + '">'
				likeHtml += '<a href="/profile/show/' + like.createdById + '">' + like.createdByFullName + '</a> Likes This'
				likeHtml += like.dateCreated
				likeHtml += '</p>'
				$('#' + postId + 'likes').prepend(likeHtml);
			}else{
				alert("You already like this")
			}
		});
	}
	
	return false;
}

function showWallPostCommentForm(){
	//wpc# - the comment-wall-post id
	var wpcId = this.id;		
	var postId = wpcId.replace('wpc', '');
			
	//wpcf# - comment form id
	$('#wpcfdiv' + postId).css('visibility','visible');
	return false;
}

function submitWallPostComment(){
	var wpcfId = this.id;		
	var postId = wpcfId.replace('wpcf', '');		
	var commentText = $.trim($('#comment-text' + postId).val());
	
	if(commentText != null && commentText != ''){
		WallClient.comment(postId, commentText, function(comment){
			var commentHtml = '<div class="post-single-comment">'
			commentHtml += '<p><a href="profile/show/' + comment.createdById + '">' + comment.createdByFullName + '</a>'
			commentHtml += ' - Posted ' + comment.dateCreated + '</p>'
			commentHtml += '<p>' + comment.text + '</p>'
			commentHtml += '</div>'
				
			$('#' + postId + 'comments').append(commentHtml);
			$('#wpcfdiv' + postId).css('visibility','hidden');
			$('#comment-text' + postId).val('')				
		});
	}else{
		alert('Your comment was blank - Please enter a comment')			
	}
	return false;
}

function refresh(target){
	if(target){
		window.location.href = target;	
	}else{
		window.location.reload();
	}
}

function respond(friendReqId, accept){		
	FriendRequestClient.respondToRequest(friendReqId, accept, {callback:function(success){			
		if(success){
			var liElm = document.getElementById(friendReqId);
			liElm.parentNode.removeChild(liElm);
		}else{
			alert("There was a problem servicing your request.  Please try again later.  If the problem persists, please contact us");
		}									
	}, async:true});
	return false;
}

function deleteMessage(messageId){		
	MessageClient.deleteMessage(messageId, {callback:function(success){			
		if(success){
			var liElm = document.getElementById(messageId);
			liElm.parentNode.removeChild(liElm);
		}else{
			alert("There was a problem servicing your request.  Please try again later.  If the problem persists, please contact us");
		}									
	}, async:true});
	
	return false;
}

function deleteSentMessage(messageId){		
	MessageClient.deleteSentMessage(messageId, {callback:function(success){			
		if(success){
			var liElm = document.getElementById(messageId);
			liElm.parentNode.removeChild(liElm);
		}else{
			alert("There was a problem servicing your request.  Please try again later.  If the problem persists, please contact us");
		}									
	}, async:true});
	
	return false;
}

function clearOnClick(id){
	var defaultVal = $(id).val();
	$(id).focus(function(){
		if(this.value == defaultVal){
			$(this).val('');
		}
	});
}


function checkStatus() {
	FileUploadProgressClient.getProgress({callback:function(progress){
		if(!progress || progress.finished){
			updateProgressBar(100)
			
			window.location.href = frames[0].location
			
            return;
		}else{
			updateProgressBar(progress.percentDone);
	        window.setTimeout("checkStatus()", 500);
		}
	}, async:false});   

}

function updateProgressBar(percentage) {
    // make sure you set the width style property for uploadProgressBar, otherwise progress.style.width won�t work

    var progress = document.getElementById("uploadProgressBar");
    var indicator = document.getElementById("uploadIndicator");
    var maxWidth = parseIntWithPx(progress.style.width) - 4;
    var width = percentage * maxWidth / 100;
    indicator.style.width = width + "px";
    var perc = document.getElementById("uploadPercentage");
    perc.innerHTML = percentage + "%";
}

function parseIntWithPx(str) {
    var strArray = str.split("p");
    return parseInt(strArray[0]);
}



function startUploadMonitoring() {
	$('#uploadStatus').toggle();
    window.setTimeout("checkStatus()", 500);
    return true;
}

function getReceivedMessages(){
	var hrefVal = $(this).attr("href");

    $.ajax({
  	  url: '/profile/messages/received' + hrefVal,
  	  dataType: 'html',
  	  success: function(data) {
  		$('#messages-received').html(data)  		
  		$('.received-messages-pagination-links').click(getReceivedMessages);
  	  }
  	});          
    
    return false;
}

function getSentMessages(){
	var hrefVal = $(this).attr("href");
    
	$.ajax({
	  url: '/profile/messages/sent' + hrefVal,
	  dataType: 'html',
	  success: function(data) {
		  $('#messages-sent').html(data);
		  $('.sent-messages-pagination-links').click(getSentMessages);
	  }
	});
    
    return false;
}