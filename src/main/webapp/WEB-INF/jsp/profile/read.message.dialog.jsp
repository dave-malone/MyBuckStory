<%@ include file="/WEB-INF/jsp/common/taglibs.inc" %>
<div id="writeNews">
    <div class="bd">
    	<c:forEach items="${messageChain}" var="historicalMessage">
    		<c:if test="${historicalMessage.mostRecent}">
    			<a name="mostRecent"></a>
    		</c:if>
	    	<div>
	    		<p style="float:left;padding:5px;margin:5px;">
	    			<mbs:imageUri id="messageCreatedByProfileImageUri" imageId="${historicalMessage.createdBy.profile.image.id}" maxWidthAndHeight="100"/>	 
	    			<img alt="${historicalMessage.createdBy.profile.firstName} Profile Picture" src="${messageCreatedByProfileImageUri}" />
	    			<br />
	    			${historicalMessage.createdBy.profile.fullName}
	    		</p>
	    		<p style="clear:right;text-align:left;">
		    		<fmt:formatDate value="${historicalMessage.dateCreated}" pattern="MMMM d, yyyy 'at' hh:mma"/>
		    		<br />
		    	    Subject: ${historicalMessage.subject}
			    	<br />
			    	<div style="width:450px">
			    		${historicalMessage.content}
			    	</div>
		    	</p>
	    	</div>
	    	<hr style="clear:both"/>
    	</c:forEach>
        <form style="padding:5px;margin:5px;" id="messageForm" name="messageForm" method="post" action="/profile/message/reply">
            <fieldset>	
                <p>
                    To: ${message.createdBy.profile.fullName}                                        
                </p>
                <p>
                    Subject: ${message.subject}
                </p>
                <p>
                    <label for="content">Message</label>
                    <textarea class="rte" name="content" id="message" style="width:100%;height:250px;"></textarea>
                </p>
            </fieldset>            
            <p>
            	<input type="hidden" name="messageToId" value="${message.createdBy.id}"/>
            	<input type="hidden" name="inResponseToId" value="${message.id}"/>
            	<input type="hidden" name="subject" value="${message.subject}" />
            	<input id="messageSubmit" type="submit" value="Send"/>
            	<input type="button" value="Cancel" onclick="self.parent.tb_remove();"/>            
            </p>            
        </form>
    </div>
</div>