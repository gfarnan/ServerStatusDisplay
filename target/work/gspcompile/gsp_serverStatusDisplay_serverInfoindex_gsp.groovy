import org.codehaus.groovy.grails.plugins.metadata.GrailsPlugin
import org.codehaus.groovy.grails.web.pages.GroovyPage
import org.codehaus.groovy.grails.web.taglib.*
import org.codehaus.groovy.grails.web.taglib.exceptions.GrailsTagException
import org.springframework.web.util.*
import grails.util.GrailsUtil

class gsp_serverStatusDisplay_serverInfoindex_gsp extends GroovyPage {
public String getGroovyPageFileName() { "/WEB-INF/grails-app/views/serverInfo/index.gsp" }
public Object run() {
Writer out = getOut()
Writer expressionOut = getExpressionOut()
registerSitemeshPreprocessMode()
printHtmlPart(0)
createTagBody(1, {->
printHtmlPart(1)
invokeTag('captureMeta','sitemesh',4,['gsp_sm_xmlClosingForEmptyTag':("/"),'http-equiv':("Content-Type"),'content':("text/html; charset=ISO-8859-1")],-1)
printHtmlPart(1)
invokeTag('captureMeta','sitemesh',5,['gsp_sm_xmlClosingForEmptyTag':("/"),'name':("layout"),'content':("test")],-1)
printHtmlPart(1)
createTagBody(2, {->
createClosureForHtmlPart(2, 3)
invokeTag('captureTitle','sitemesh',6,[:],3)
})
invokeTag('wrapTitleTag','sitemesh',6,[:],2)
printHtmlPart(1)
})
invokeTag('captureHead','sitemesh',7,[:],1)
printHtmlPart(1)
createTagBody(1, {->
printHtmlPart(3)
loop:{
int i = 0
for( server in (servers) ) {
printHtmlPart(4)
expressionOut.print(server.host)
printHtmlPart(5)
invokeTag('fieldValue','g',24,['bean':(server),'field':("status")],-1)
printHtmlPart(5)
invokeTag('fieldValue','g',25,['bean':(server),'field':("os")],-1)
printHtmlPart(5)
invokeTag('fieldValue','g',26,['bean':(server),'field':("swVersion")],-1)
printHtmlPart(5)
invokeTag('fieldValue','g',27,['bean':(server),'field':("owner")],-1)
printHtmlPart(5)
invokeTag('fieldValue','g',28,['bean':(server),'field':("oracle")],-1)
printHtmlPart(5)
invokeTag('fieldValue','g',29,['bean':(server),'field':("jboss")],-1)
printHtmlPart(5)
invokeTag('fieldValue','g',30,['bean':(server),'field':("java")],-1)
printHtmlPart(6)
i++
}
}
printHtmlPart(7)
})
invokeTag('captureBody','sitemesh',35,[:],1)
printHtmlPart(8)
}
public static final Map JSP_TAGS = new HashMap()
protected void init() {
	this.jspTags = JSP_TAGS
}
public static final String CONTENT_TYPE = 'text/html;charset=UTF-8'
public static final long LAST_MODIFIED = 1439565132873L
public static final String EXPRESSION_CODEC = 'html'
public static final String STATIC_CODEC = 'none'
public static final String OUT_CODEC = 'html'
public static final String TAGLIB_CODEC = 'none'
}

@org.codehaus.groovy.grails.web.transform.LineNumber(
	lines = [1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 4, 4, 4, 5, 5, 6, 6, 6, 6, 6, 6, 7, 7, 7, 8, 21, 21, 21, 21, 21, 21, 23, 23, 24, 24, 25, 25, 26, 26, 27, 27, 28, 28, 29, 29, 30, 30, 32, 32, 32, 32, 35, 35, 35, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1],
	sourceName = "index.gsp"
)
class ___LineNumberPlaceholder { }
