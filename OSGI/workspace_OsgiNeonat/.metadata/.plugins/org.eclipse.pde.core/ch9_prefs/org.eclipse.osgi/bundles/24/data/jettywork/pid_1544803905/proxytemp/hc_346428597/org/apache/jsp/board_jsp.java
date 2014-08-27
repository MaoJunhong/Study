package org.apache.jsp;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import java.util.Map.Entry;
import java.util.*;
import org.osgi.framework.*;
import org.fenixsoft.neonat.service.*;
import org.fenixsoft.neonat.entity.*;
import org.fenixsoft.neonat.ui.*;

public final class board_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static final JspFactory _jspxFactory = JspFactory.getDefaultFactory();

  private static java.util.List<String> _jspx_dependants;

  private org.glassfish.jsp.api.ResourceInjector _jspx_resourceInjector;

  public java.util.List<String> getDependants() {
    return _jspx_dependants;
  }

  public void _jspService(HttpServletRequest request, HttpServletResponse response)
        throws java.io.IOException, ServletException {

    PageContext pageContext = null;
    HttpSession session = null;
    ServletContext application = null;
    ServletConfig config = null;
    JspWriter out = null;
    Object page = this;
    JspWriter _jspx_out = null;
    PageContext _jspx_page_context = null;

    try {
      response.setContentType("text/html; charset=utf-8");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;
      _jspx_resourceInjector = (org.glassfish.jsp.api.ResourceInjector) application.getAttribute("com.sun.appserv.jsp.resource.injector");

      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">\r\n");
      out.write("<html xmlns=\"http://www.w3.org/1999/xhtml\" >\r\n");
      out.write("<head>\r\n");
      out.write("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\r\n");
      out.write("<title>Neonat论坛</title>\r\n");
      out.write("<link href=\"forum.css\" media=\"screen\" rel=\"stylesheet\" type=\"text/css\">\r\n");
      out.write("</head>\r\n");
      out.write("<body>\r\n");
      out.write("<div id=\"page\">\r\n");
      out.write("  <div id=\"header\">\r\n");
      out.write("    <div class=\"clearfix\">\r\n");
      out.write("      <div style=\"padding:15px 0 10px 0;\">\r\n");
      out.write("          <div class=\"title_logo\"></div>\r\n");
      out.write("      </div>\r\n");
      out.write("      <div class=\"toolbar\"><a href=\"new.jsp\"><img src=\"images/post.gif\" /></a><div style=\"float:right\">论坛发帖总数：\r\n");
      out.write("\t\t");

			BundleContext context = Activator.getContext();
			NeonatModelService ms = context.getService(context.getServiceReference(NeonatModelService.class));
			Board board = ms.getBoard();
			out.println(board.getTopicCount());
		
      out.write("\r\n");
      out.write("\t  </div></div>\r\n");
      out.write("\t  \r\n");
      out.write("\t <table id=\"forum_main\" cellspacing=\"1\">\r\n");
      out.write("        <thead>\r\n");
      out.write("          <tr>\r\n");
      out.write("            <td bgcolor=\"#477AA5\" style=\"width: 20px;\">ID</td>\r\n");
      out.write("            <td colspan=\"2\" bgcolor=\"#477AA5\" style=\"border-left: 1px solid white;\">主题</td>\r\n");
      out.write("            <td bgcolor=\"#477AA5\" style=\"width: 50px;\">回复</td>\r\n");
      out.write("            <td bgcolor=\"#477AA5\" style=\"width: 100px;\">发帖时间</td>\r\n");
      out.write("            <td bgcolor=\"#477AA5\" style=\"width: 100px;\">作者</td>\r\n");
      out.write("          </tr>\r\n");
      out.write("        </thead>\r\n");
      out.write("        <tbody>\r\n");
      out.write("           ");

            for (Iterator<Entry<Integer, Topic>> i = board.getTopicsMap().entrySet().iterator(); i.hasNext();) {
                Topic topic = i.next().getValue();
	        
      out.write("\r\n");
      out.write("\t        <tr>\r\n");
      out.write("\t            <td class=\"topic_id\">");
      out.print(topic.getId());
      out.write("</td>\r\n");
      out.write("\t            <td class=\"topic_icon unread_topic\"></td>\r\n");
      out.write("\t            <td class=\"topic_title\"><a href=\"content.jsp?id=");
      out.print(topic.getId());
      out.write('"');
      out.write('>');
      out.print(topic.getTitle());
      out.write("</a></td>\r\n");
      out.write("\t            <td class=\"topic_replies\">");
      out.print(topic.getReplyTopics().size() );
      out.write("</td>\r\n");
      out.write("\t            <td class=\"topic_time\">");
      out.print(topic.getDate().toLocaleString());
      out.write("</td>\r\n");
      out.write("\t            <td class=\"topic_author\"><a href=\"#\" target=\"_blank\">IcyFenix</a></td>\r\n");
      out.write("\t        </tr>\r\n");
      out.write("\t        ");

	            }
	        
      out.write("\r\n");
      out.write("      </table>\r\n");
      out.write("      <br />\r\n");
      out.write("      <div style=\"text-align:center\">©2011-2012 Neonat BBS All rights reserved.</div>\r\n");
      out.write("    </div>\r\n");
      out.write("  </div>\r\n");
      out.write("</div>\r\n");
      out.write("</body>\r\n");
      out.write("</html>");
    } catch (Throwable t) {
      if (!(t instanceof SkipPageException)){
        out = _jspx_out;
        if (out != null && out.getBufferSize() != 0)
          out.clearBuffer();
        if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
        else throw new ServletException(t);
      }
    } finally {
      _jspxFactory.releasePageContext(_jspx_page_context);
    }
  }
}
