<%@ include file="/WEB-INF/jsp/common/taglibs.inc" %>
<c:set var="resource" value="${requestScope.requestedResource}" />

<script type="text/javascript" src="<c:url value="/scripts/jquery-1.4.2.min.js" />"></script>

<security:authorize access="isAuthenticated()">
<!-- DWR scripts -->
<script type="text/javascript" src="<c:url value="/dwr/interface/FileUploadProgressClient.js" />"></script>
<script type="text/javascript" src="<c:url value="/dwr/interface/FriendRequestClient.js" />"></script>
<script type="text/javascript" src="<c:url value="/dwr/interface/ImageClient.js" />"></script>
<script type="text/javascript" src="<c:url value="/dwr/interface/MessageClient.js" />"></script>
<script type="text/javascript" src="<c:url value="/dwr/interface/StoryRatingClient.js" />"></script>
<script type="text/javascript" src="<c:url value="/dwr/interface/WallClient.js" />"></script>
<script type="text/javascript" src="<c:url value="/dwr/engine.js" />"></script>
</security:authorize>
<!-- Combo-handled YUI JS files: -->
<script type="text/javascript" src="http://yui.yahooapis.com/combo?2.7.0/build/yahoo-dom-event/yahoo-dom-event.js&2.7.0/build/connection/connection-min.js&2.7.0/build/element/element-min.js&2.7.0/build/animation/animation-min.js&2.7.0/build/utilities/utilities.js&2.7.0/build/connection/connection-min.js&2.7.0/build/datasource/datasource-min.js&2.7.0/build/container/container-min.js&2.7.0/build/menu/menu-min.js&2.7.0/build/button/button-min.js&2.7.0/build/calendar/calendar-min.js&2.7.0/build/carousel/carousel-min.js&2.7.0/build/editor/editor-min.js&2.7.0/build/resize/resize-min.js&2.7.0/build/imagecropper/imagecropper-min.js&2.7.0/build/tabview/tabview-min.js&2.7.0/build/selector/selector-min.js"></script>
<script type="text/javascript" src="<c:url value="/scripts/yui.js" />"></script>
<script type="text/javascript" src="<c:url value="/scripts/yui-imgUploader.js" />"></script> 
<script type="text/javascript" src="<c:url value="/scripts/yui-textarea.js" />"></script>
<script type="text/javascript" src="<c:url value="/scripts/dispatcher-min.js" />"></script>  
<script type="text/javascript" src="<c:url value="/scripts/accordian.js" />"></script> 

<!-- jQuery Specific scripts -->
<script type="text/javascript" src="<c:url value="/scripts/thickbox.js" />"></script>
<script type="text/javascript" src="<c:url value="/scripts/jquery-ifixpng.js" />"></script>
<script type="text/javascript" src="<c:url value="/scripts/niftycube.js" />"></script>
<!-- required jquery.datePicker plugins -->
<script type="text/javascript" src="<c:url value="/scripts/date.js" />"></script>
<!--[if IE]>
<script type="text/javascript" src="<c:url value="/scripts/jquery.bgiframe.js" />"></script>
<![endif]-->
<script type="text/javascript" src="<c:url value="/scripts/jquery.datePicker.js" />"></script>
<script type="text/javascript" src="<c:url value="/scripts/jquery.rating.js" />"></script>
<script type="text/javascript" src="<c:url value="/scripts/tiny_mce/tiny_mce.js" />"></script>
<script type="text/javascript" src="http://jquery-ui.googlecode.com/svn/tags/1.8rc1/ui/jquery-ui.js"></script>
<script type="text/javascript" src="<c:url value="/scripts/jquery.easing.1.3.js" />"></script>
<script type="text/javascript" src="<c:url value="/scripts/jquery.bxSlider.min.js" />"></script>

<script type="text/javascript" src="<c:url value="/scripts/application.js" />"></script>
<script type="text/javascript" src="<c:url value="/scripts/ecommerce.js" />"></script>
<script type="text/javascript" src="<c:url value="/scripts/doc.utils.js" />"></script>
<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.2.6/jquery.js"></script>
<script type="text/javascript" src="<c:url value="/scripts/drop.down.menu.js" />"></script>
