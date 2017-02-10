<%@ include file="/WEB-INF/jsp/common/taglibs.inc" %>

<div class="ad">
    <a href="/ad/click/35" title="Join us on Facebook" target="_blank" rel="nofollow">
        <img alt="" src="/static/images/AD/75925/75925_190_190.jpg" />
    </a>
</div>
<div class="ad">
    <a href="/ad/click/14" title="Follow Us on Twitter" target="_blank" rel="nofollow">
        <img alt="" src="/static/images/AD/75924/75924_190_190.png" />
    </a>
</div>

<%--
<div class="ad">
<script type="text/javascript"><!--
google_ad_client = "ca-pub-7415535279069157";
/* Banner 180x150 */
google_ad_slot = "7669986643";
google_ad_width = 180;
google_ad_height = 150;
//-->
</script>
<script type="text/javascript"
src="http://pagead2.googlesyndication.com/pagead/show_ads.js">
</script>
</div>
--%>

<mbs:adIterator id="ad" enabledOnly="${true}">
    <div class="ad">
    	<c:if test="${not empty ad.rawCode}">
    		${ad.rawCode}
    	</c:if>
    	<c:if test="${empty ad.rawCode}">        
	        <a href="<c:url value="/ad/click/${ad.id}" />" title="${ad.name}" target="_blank" rel="nofollow">
	        	<mbs:imageUri id="adImageUri" imageId="${ad.image.id}" maxWidthAndHeight="215"/>
	            <img alt="" src="${adImageUri}" />
	        </a>
        </c:if>
        <div class="adTag">ADVERTISEMENT</div>
    </div>
</mbs:adIterator>

<%--
<div class="ad">
	<script type="text/javascript"><!--
	google_ad_client = "ca-pub-7415535279069157";
	/* Skyscraper */
	google_ad_slot = "1276504922";
	google_ad_width = 160;
	google_ad_height = 600;
	//-->
	</script>
	<script type="text/javascript"
	src="http://pagead2.googlesyndication.com/pagead/show_ads.js">
	</script>
</div>
 --%>

<div class="placeAd">
    <span class="haveQuestion fauxLink" id="advertiseButton">
        <a href="<c:url value="/howto.html?KeepThis=true&TB_iframe=true&height=450&width=750" />" class="thickbox" title="Advertise on MyBuckStory.com">
            Advertise on MyBuckStory.com</a>
    </span>
</div>