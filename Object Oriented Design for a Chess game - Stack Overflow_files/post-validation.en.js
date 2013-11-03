'use strict';StackExchange.postValidation=function(){function r(b,a,d){var c=b.find('input[type="submit"]:visible'),f=c.length&&c.is(":enabled");f&&c.attr("disabled",!0);x(b);y(b,a,d);z(b);A(b);B(b);var e=function(){1!=a||b.find(l).length?(C(b),f&&c.attr("disabled",!1)):setTimeout(e,250)};e()}function s(){for(var b=0;b<g.length;b++)clearTimeout(g[b]);g=[]}function x(b){var a=b.find(n);a.length&&a.blur(function(){g.push(setTimeout(function(){var d=a.val(),c=$.trim(d);if(0==c.length)e(b,a);else{var f=
a.data("min-length");f&&c.length<f?i(a,["Title must be at least "+f+" characters."],h()):(f=a.data("max-length"))&&c.length>f?i(a,["Title cannot be longer than "+f+" characters."],h()):$.ajax({type:"POST",url:"/posts/validate-title",data:{title:d},success:function(c){c.success?e(b,a):i(a,c.errors.Title,h())},error:function(){e(b,a)}})}},k))})}function y(b,a,d){var c=b.find(o);c.length&&c.blur(function(){g.push(setTimeout(function(){var f=c.val(),g=$.trim(f);0==g.length?e(b,c):5==a?(f=c.data("min-length"))&&
g.length<f?i(c,[function(a){return"Wiki Body must be at least "+a.minLength+" characters. You entered "+a.actual+"."}({minLength:f,actual:g.length})],h()):e(b,c):(1==a||2==a)&&$.ajax({type:"POST",url:"/posts/validate-body",data:{body:f,oldBody:c.prop("defaultValue"),isQuestion:1==a,isSuggestedEdit:d},success:function(a){a.success?e(b,c):i(c,a.errors.Body,h())},error:function(){e(b,c)}})},k))})}function C(b){var a=b.find(l);if(a.length){var d=a.parent().find("input#tagnames");d.blur(function(){g.push(setTimeout(function(){var c=
d.val();0==$.trim(c).length?e(b,a):$.ajax({type:"POST",url:"/posts/validate-tags",data:{tags:c,oldTags:d.prop("defaultValue")},success:function(c){c.success?e(b,a):i(a,c.errors.Tags,h())},error:function(){e(b,a)}})},k))})}}function z(b){var a=b.find(m);a.length&&a.blur(function(){g.push(setTimeout(function(){var d=a.val(),d=$.trim(d);if(0==d.length)e(b,a);else{var c=a.data("min-length");c&&d.length<c?i(a,["Your edit summary must be at least "+c+" characters."],h()):(c=a.data("max-length"))&&d.length>
c?i(a,["Your edit summary cannot be longer than "+c+" characters."],h()):e(b,a)}},k))})}function A(b){var a=b.find(p);a.length&&a.blur(function(){g.push(setTimeout(function(){var d=a.val(),d=$.trim(d);if(0==d.length)e(b,a);else{var c=a.data("min-length");c&&d.length<c?i(a,[function(a){return"Wiki Excerpt must be at least "+a.minLength+" characters; you entered "+a.actual+"."}({minLength:c,actual:d.length})],h()):(c=a.data("max-length"))&&d.length>c?i(a,[function(a){return"Wiki Excerpt cannot be longer than "+
a.maxLength+" characters; you entered "+a.actual+"."}({maxLength:c,actual:d.length})],h()):e(b,a)}},k))})}function B(b){var a=b.find(q);a.length&&a.blur(function(){g.push(setTimeout(function(){var d=a.val();0==$.trim(d).length?e(b,a):/^[A-Z0-9._%+-]+@[A-Z0-9.-]+\.[A-Z]{2,20}$/i.test(d)?e(b,a):i(a,["This email does not appear to be valid."],t())},k))})}function h(){var b=$("#sidebar, .sidebar").first().width()||270;return{position:{my:"left top",at:"right center"},css:{"max-width":b,"min-width":b},
closeOthers:!1}}function t(){return{position:{my:"left top",at:"right center"},css:{"min-width":$("#sidebar, .sidebar").first().width()||270},closeOthers:!1}}function u(b,a,d,c){if(b){var f=function(){var d=0;i(a.find(n),b.Title,h())?d++:e(a,a.find(n));i(a.find(o),b.Body,h())?d++:e(a,a.find(o));i(a.find(l),b.Tags,h())?d++:e(a,a.find(l));i(a.find(m),b.EditComment,h())?d++:e(a,a.find(m));i(a.find(p),b.Excerpt,h())?d++:e(a,a.find(p));i(a.find(q),b.Email,t())?d++:e(a,a.find(q));var f=a.find(".general-error"),
g=b.General&&0<b.General.length;g||0<d?(f.length||(a.find('input[type="submit"]:visible').before('<div class="general-error-container"><div class="general-error"></div><br class="cbt" /></div>'),f=a.find(".general-error")),g?i(f,b.General,{position:"inline",css:{"float":"left","margin-bottom":"10px"},closeOthers:!1,dismissable:!1}):(e(a,f),f.text("Your "+c+" couldn't be submitted. Please see the errors above."))):a.find(".general-error-container").remove();var v;w()&&($("#sidebar").animate({opacity:0.4},
500),v=setInterval(function(){w()||($("#sidebar").animate({opacity:1},500),clearInterval(v))},500));var j;a.find(".validation-error").each(function(){var a=$(this).offset().top;if(!j||a<j)j=a});d=function(){for(var b=0;3>b;b++)a.find(".message").animate({left:"+=5px"},100).animate({left:"-=5px"},100)};j?(f=$(".review-bar").length,j=Math.max(0,j-(f?125:30)),$("html, body").animate({scrollTop:j},d)):d()},g=function(){1!=d||a.find(l).length?f():setTimeout(g,250)};g()}}function i(b,a,d){if(!a||0==a.length||
!b.length||!$("html").has(b).length)return!1;if((a=1==a.length?a[0]:"<ul><li>"+a.join("</li><li>")+"</li></ul>")&&0<a.length){var c=b.data("error-popup");if(c&&c.is(":visible")){if(b.data("error-message")==a)return!0;c.fadeOutAndRemove()}d=StackExchange.helpers.showMessage(b,a,d);d.find("a").attr("target","_blank");b.addClass("validation-error");b.data("error-popup",d);b.data("error-message",a);d.click(s);return!0}return!1}function e(b,a){if(!a||0==a.length)return!1;var d=a.data("error-popup");d&&
d.is(":visible")&&d.fadeOutAndRemove();a.removeClass("validation-error");a.removeData("error-popup");a.removeData("error-message");b.find(".validation-error").length||b.find(".general-error-container").remove();return!0}function w(){var b=!1,a=$("#sidebar, .sidebar").first();if(!a.length)return!1;var d=a.offset().left;$(".message").each(function(){var a=$(this);if(a.offset().left+a.outerWidth()>d)return b=!0,!1});return b}var n="input#title",o="textarea.wmd-input:first",l=".tag-editor",m="input[id^=edit-comment]",
p="textarea#excerpt",q="input#m-address",g=[],k=250;return{initOnBlur:r,initOnBlurAndSubmit:function(b,a,d,c,f){r(b,a,c);var e,g=function(c){if(c.success)if(f)f(c);else{var i=window.location.href.split("#")[0],h=c.redirectTo.split("#")[0];0==h.indexOf("/")&&(h=window.location.protocol+"//"+window.location.hostname+h);e=!0;window.location=c.redirectTo;i.toLowerCase()==h.toLowerCase()&&window.location.reload(!0)}else c.captchaHtml?StackExchange.captcha.init(c.captchaHtml,g):c.errors?u(c.errors,b,a,
d):b.find('input[type="submit"]:visible').parent().showErrorMessage(c.message)};b.submit(function(){if(b.find("#answer-from-ask").is(":checked"))return!0;var a=b.find(m);if("[Edit removed during grace period]"==$.trim(a.val()))return i(a,["Comment reserved for system use.  Please use an appropriate comment."],h()),!1;s();StackExchange.navPrevention&&StackExchange.navPrevention.stop();b.find('input[type="submit"]:visible').parent().addSpinner();StackExchange.helpers.disableSubmitButton(b);setTimeout(function(){$.ajax({type:"POST",
dataType:"json",data:b.serialize(),url:b.attr("action"),success:g,error:function(){b.find('input[type="submit"]:visible').parent().showErrorMessage("An error occurred submitting the "+d+".")},complete:function(){StackExchange.helpers.removeSpinner();e||StackExchange.helpers.enableSubmitButton(b)}})},0);return!1})},showErrorsAfterSubmission:u,getSidebarPopupOptions:h}}();