<%@page import="java.util.Map.Entry"%>
<%@page import="java.util.*"%>
<%@page import="org.osgi.framework.*"%>
<%@page import="org.fenixsoft.neonat.service.*"%>
<%@page import="org.fenixsoft.neonat.entity.*"%>
<%@page import="org.fenixsoft.neonat.ui.*"%>
<%@page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" >
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Neonat论坛</title>
<link href="forum.css" media="screen" rel="stylesheet" type="text/css">
</head>
<body>
<%
	BundleContext context = Activator.getContext();
	NeonatModelService ms = context.getService(context.getServiceReference(NeonatModelService.class));
	Board board = ms.getBoard();
	String title = request.getParameter("title");
	if (title != null) {
		String data = request.getParameter("data");
		board.addTopic(0, title, data);
		out.println("<script>alert('发帖成功！');location.href='board.jsp'</script>");
	}
%>
<form action="new.jsp" method="post">
<div id="page">
  <div id="header">
    <div class="clearfix">
      <div style="padding:15px 0 10px 0;">
        <div class="title_logo"></div>
      </div>
      <br />
      <table cellspacing="1" id="forum_main">
        <thead>
          <tr>
            <td bgcolor="#477AA5" colspan="2">发表主题</td>
          </tr>
        </thead>
        <tbody>
          <tr>
            <td class="row1">主题</td>
            <td class="row2"><input class="text max-tag-5"  maxlength="80" name="title" size="45" style="width:450px;" type="text" title="" /></td>
          </tr>
          <tr>
            <td class="row1">内容</td>
            <td class="row2"><textarea name="data" class="text max-tag-5" style="width: 650px; height: 350px;"></textarea>
              <div id="current_tags_panel"></div></td>
          </tr>
          <tr>
            <td colspan="2" align="center" class="row4">
            <input class="submit" type="submit" value="发表帖子" /> 
            <input class="submit" type="button" onclick="javascript:location.href='board.jsp'"  value="返回版面" />
            </td>
          </tr>
        </tbody>
      </table>
      <br />
      <div style="text-align:center">©2011-2012 Neonat BBS All rights reserved.</div>
    </div>
  </div>
</div>
</form>
</body>
</html>