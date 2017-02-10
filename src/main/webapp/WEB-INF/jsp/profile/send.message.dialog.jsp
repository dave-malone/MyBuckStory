<%@ include file="/WEB-INF/jsp/common/taglibs.inc" %>


<div id="writeNews">
    <div class="bd">
        <form:form id="messageForm" name="messageForm" method="post" action="/profile/message/submit">
            <fieldset>	
            	<c:set var="errorText">
				<form:errors path="*"/>
				</c:set>
				<c:if test="${not empty errorText}">
					<div style="border: #d9acac solid 1px; color: #ff000; padding: 1em;">
						${errorText}		
					</div>			
				</c:if>	
                <p>
                    <label for="newsTitle">To:</label>
                    ${command.to.profile.fullName}                                        
                </p>
                <p>
                    <label for="subject">Subject</label>					                  
                    <form:input path="subject"/>
                </p>
                <p>
                    <label for="content">Message</label>
                    <form:textarea cssClass="rte" path="content" id="message" cssStyle="width:100%;height:250px;"/>
                </p>
            </fieldset>            
            <p>
            	<input type="hidden" name="to.id" value="${param.messageToId}"/>
            	<input id="messageSubmit" type="submit" value="Send"/>
            	<input type="button" value="Cancel" onclick="self.parent.tb_remove();"/>            
            </p>            
        </form:form>
    </div>
</div>