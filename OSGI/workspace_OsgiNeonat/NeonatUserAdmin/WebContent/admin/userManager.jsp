<%@page import="java.util.Comparator"%>
<%@page import="java.util.Arrays"%>
<%@page import="java.util.Collections"%>
<%@page import="org.fenixsoft.neonat.useradmin.*"%>
<%@page import="org.osgi.service.useradmin.*"%>
<%@page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" >
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Neonat论坛用户管理</title>
<link href="../forum.css" media="screen" rel="stylesheet" type="text/css">
</head>
<body>
<div id="page">
  <div id="header">
    <div class="clearfix">
      <div style="padding:15px 0 10px 0;">
          <div class="title_logo"></div>
      </div>
      <div class="toolbar"><div style="float:right"><a href="#"><img src="../images/addUser.gif" /></a>&nbsp;&nbsp;&nbsp;&nbsp;<a href="#"><img src="../images/addGroup.gif" /></a>&nbsp;&nbsp;&nbsp;&nbsp;
		<%
		  UserAdmin userAdmin = UserAdminTracker.getUserAdmin();
          Role[] roles = userAdmin.getRoles(null);
          Arrays.sort(roles, new Comparator<Role>(){
        	  public int compare(Role r1,Role r2){
        		  return r2.getType() - r1.getType();
        	  }
          });
          //Collections.sort(list, c)
		%>
	  </div></div>
	  
	 <table id="forum_main" cellspacing="1">
        <thead>
          <tr>
            <td bgcolor="#477AA5" style="width: 20px;">ID</td>
            <td colspan="2" bgcolor="#477AA5" style="border-left: 1px solid white;">用户</td>
            <td bgcolor="#477AA5" style="width: 50px;">类型</td>
            <td bgcolor="#477AA5" style="width: 200px;">属性</td>
            <td bgcolor="#477AA5" style="width: 150px;">操作</td>
          </tr>
        </thead>
        <tbody>
           <%
            for (Role role : roles) {
	        %>
	        <tr>
	            <td class="topic_id"><%=role.hashCode()%></td>
	            <td class="topic_icon unread_topic"></td>
	            <td class="topic_title"><a href="content.jsp"><%=role.getName()%></a></td>
	            <td class="topic_replies"><%
	            if(role.getType() == Role.GROUP){
	            	out.println("用户组");
	            }else if(role.getType() == Role.USER){
	            	out.println("用户");
	            }else{
	            	out.println("角色");
	            }
	            %></td>
	            <td class="topic_time"><%
	            if(role instanceof Group){
	            	Role[] members = ((Group) role).getMembers();
	            	for(Role member: members){
	            		out.println(member.getName()+" ");
	            	}
	            }else{
	                 out.println(role.getProperties());
	            }
	            %></td>
	            <td class="topic_author"><a href=#>删除</a> <a href=#>修改</a> <a href=#>停用</a></td>
	        </tr>
	        <% } %>
      </table>
      <br />
      <div style="text-align:center">©2011-2012 Neonat BBS All rights reserved.</div>
    </div>
  </div>
</div>
</body>
</html>