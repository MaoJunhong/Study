<%@page import="java.util.Map.Entry"%>
<%@page import="java.util.*"%>
<%@page import="org.osgi.framework.*"%>
<%@page import="org.fenixsoft.neonat.service.*"%>
<%@page import="org.fenixsoft.neonat.entity.*"%>
<%@page import="org.fenixsoft.neonat.ui.*"%>
<%@page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" >
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Neonat论坛</title>
<link href="forum.css" media="screen" rel="stylesheet" type="text/css">
</head>
<%
	BundleContext context = Activator.getContext();
	NeonatModelService ms = context.getService(context.getServiceReference(NeonatModelService.class));
	Board board = ms.getBoard();
	int id = Integer.parseInt(request.getParameter("id"));
	String replyTitle = request.getParameter("replyTitle");
	if (replyTitle != null) {
		String replyData = request.getParameter("replyData");
		board.addTopic(id, replyTitle, replyData);
		out.println("<script>alert('回复成功！');location.href='board.jsp'</script>");
	}
	Topic topic = board.getTopicsMap().get(id);
%>
<body>
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
            <th colspan="2" class="topic_title"><div class="wrapper" style="color:#1E4E7B">&nbsp;&nbsp;主题：<%=topic.getTitle() %></div></th>
          </tr>
          <tr>
            <td class="first_col" bgcolor="#477AA5">作者</td>
            <td class="last_col" bgcolor="#477AA5">正文</td>
          </tr>
        </thead>
        <form action="content.jsp" method="post">
        <tbody id="posts">
          <tr>
            <td class="postauthor"><div class="name">IcyFenix</div></td>
            <td class="postcontent"><div class="postactions">
              <div class="description"> &nbsp;&nbsp;
                发表时间：<%=topic.getDate().toLocaleString() %></div>
            </div>
              <div class="postbody clearfix"><pre><%=topic.getContent().replaceAll("<", "&lt;").replaceAll(">", "&gt;")%></pre></div>
              <div id="topic_copyright">声明：文章版权属于作者，受法律保护。没有作者书面许可不得转载。 </div></td>
          </tr>
          <td colspan="2" class="topic_title"><div class="wrapper" style="color:#1E4E7B">&nbsp;&nbsp;回复帖子</div></td>
          <tr>
            <td class="row1">主题</td>
            <td class="row2"><input class="text max-tag-5" maxlength="80" name="replyTitle" size="45" style="width:450px;" type="text" title="" value="<%="Re:" + topic.getTitle()%>" /></td>
          </tr>
          <tr>
            <td class="row1">内容</td>
            <td class="row2"><textarea name="replyData" class="text max-tag-5" style="width: 650px; height: 350px;"></textarea>
              <div id="current_tags_panel"></div></td>
          </tr>
          <tr>
            <td colspan="2" align="center" class="row4">
            <input type="hidden" name="id" value="<%=id %>" /> 
            <input class="submit" type="submit" value="回复帖子" /> 
            <input class="submit" type="button" onclick="javascript:location.href='board.jsp'"  value="返回版面" />
            </td>
          </tr>
        </tbody>
        </form>
      </table>
      <br />
      <div style="text-align:center">©2011-2012 Neonat BBS All rights reserved.</div>
    </div>
  </div>
</div>
</body>
</html>