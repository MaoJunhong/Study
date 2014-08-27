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
<body>
<div id="page">
  <div id="header">
    <div class="clearfix">
      <div style="padding:15px 0 10px 0;">
          <div class="title_logo"></div>
      </div>
      <div class="toolbar"><a href="new.jsp"><img src="images/post.gif" /></a><div style="float:right">论坛发帖总数：
		<%
			BundleContext context = Activator.getContext();
			NeonatModelService ms = context.getService(context.getServiceReference(NeonatModelService.class));
			Board board = ms.getBoard();
			out.println(board.getTopicCount());
		%>
	  </div></div>
	  
	 <table id="forum_main" cellspacing="1">
        <thead>
          <tr>
            <td bgcolor="#477AA5" style="width: 20px;">ID</td>
            <td colspan="2" bgcolor="#477AA5" style="border-left: 1px solid white;">主题</td>
            <td bgcolor="#477AA5" style="width: 50px;">回复</td>
            <td bgcolor="#477AA5" style="width: 100px;">发帖时间</td>
            <td bgcolor="#477AA5" style="width: 100px;">作者</td>
          </tr>
        </thead>
        <tbody>
           <%
            for (Iterator<Entry<Integer, Topic>> i = board.getTopicsMap().entrySet().iterator(); i.hasNext();) {
                Topic topic = i.next().getValue();
	        %>
	        <tr>
	            <td class="topic_id"><%=topic.getId()%></td>
	            <td class="topic_icon unread_topic"></td>
	            <td class="topic_title"><a href="content.jsp?id=<%=topic.getId()%>"><%=topic.getTitle()%></a></td>
	            <td class="topic_replies"><%=topic.getReplyTopics().size() %></td>
	            <td class="topic_time"><%=topic.getDate().toLocaleString()%></td>
	            <td class="topic_author"><a href="#" target="_blank">IcyFenix</a></td>
	        </tr>
	        <%
	            }
	        %>
      </table>
      <br />
      <div style="text-align:center">©2011-2012 Neonat BBS All rights reserved.</div>
    </div>
  </div>
</div>
</body>
</html>