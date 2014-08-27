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

public final class new_jsp extends org.apache.jasper.runtime.HttpJspBase
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

	BundleContext context = Activator.getContext();
	NeonatModelService ms = context.getService(context.getServiceReference(NeonatModelService.class));
	Board board = ms.getBoard();
	String title = request.getParameter("title");
	if (title != null) {
		String data = request.getParameter("data");
		board.addTopic(0, title, data);
		out.println("<script>alert('发帖成功！');location.href='board.jsp'</script>");
	}

      out.write("\r\n");
      out.write("<form action=\"new.jsp\" method=\"post\">\r\n");
      out.write("<div id=\"page\">\r\n");
      out.write("  <div id=\"header\">\r\n");
      out.write("    <div class=\"clearfix\">\r\n");
      out.write("      <div style=\"padding:15px 0 10px 0;\">\r\n");
      out.write("        <div class=\"title_logo\"></div>\r\n");
      out.write("      </div>\r\n");
      out.write("      <br />\r\n");
      out.write("      <table cellspacing=\"1\" id=\"forum_main\">\r\n");
      out.write("        <thead>\r\n");
      out.write("          <tr>\r\n");
      out.write("            <td bgcolor=\"#477AA5\" colspan=\"2\">发表主题</td>\r\n");
      out.write("          </tr>\r\n");
      out.write("        </thead>\r\n");
      out.write("        <tbody>\r\n");
      out.write("          <tr>\r\n");
      out.write("            <td class=\"row1\">主题</td>\r\n");
      out.write("            <td class=\"row2\"><input class=\"text max-tag-5\"  maxlength=\"80\" name=\"title\" size=\"45\" style=\"width:450px;\" type=\"text\" title=\"\" /></td>\r\n");
      out.write("          </tr>\r\n");
      out.write("          <tr>\r\n");
      out.write("            <td class=\"row1\">内容</td>\r\n");
      out.write("            <td class=\"row2\"><textarea name=\"data\" class=\"text max-tag-5\" style=\"width: 650px; height: 350px;\"></textarea>\r\n");
      out.write("              <div id=\"current_tags_panel\"></div></td>\r\n");
      out.write("          </tr>\r\n");
      out.write("          <tr>\r\n");
      out.write("            <td colspan=\"2\" align=\"center\" class=\"row4\">\r\n");
      out.write("            <input class=\"submit\" type=\"submit\" value=\"发表帖子\" /> \r\n");
      out.write("            <input class=\"submit\" type=\"button\" onclick=\"javascript:location.href='board.jsp'\"  value=\"返回版面\" />\r\n");
      out.write("            </td>\r\n");
      out.write("          </tr>\r\n");
      out.write("        </tbody>\r\n");
      out.write("      </table>\r\n");
      out.write("      <br />\r\n");
      out.write("      <div style=\"text-align:center\">©2011-2012 Neonat BBS All rights reserved.</div>\r\n");
      out.write("    </div>\r\n");
      out.write("  </div>\r\n");
      out.write("</div>\r\n");
      out.write("</form>\r\n");
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
