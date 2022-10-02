package hospital.sys;

import javax.servlet.*;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class Hospital extends HttpServlet {

	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		String action = req.getParameter("action");

		PrintWriter wr = res.getWriter();
		if (action != null && action.equalsIgnoreCase("register"))
			wr.print(this.header()+this.registerBody("")+this.footer());
		else if (action != null && action.equalsIgnoreCase("login"))
			wr.print(this.home());
		else
			wr.print(this.home());
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		PrintWriter wr = res.getWriter();
		String action = req.getParameter("action");
		String patientName = req.getParameter("patientname");
		String email = req.getParameter("email");
		String password = req.getParameter("password");
		String confirmPassword = req.getParameter("confirmPassword");
		String address = req.getParameter("add");
		String phoneNumber = req.getParameter("phone");
		String reasonOfVisit = req.getParameter("rov");
		String gender = req.getParameter("gender");
		String age = req.getParameter("age");
		String bloodGroup = req.getParameter("bgroup");
		String userRole = req.getParameter("userrole");

		boolean register = action!= null && action.equalsIgnoreCase("register");
		boolean login = action!= null && action.equalsIgnoreCase("login");

		String actionError = "";
		if (register) {
			System.out.println("Patient Name: "+patientName);
			System.out.println("Email: " + email);
			System.out.println("Password: " + password);
			System.out.println("Confirm Password: " + confirmPassword);
			System.out.println("Address: "+address);
			System.out.println("Phone Number: "+phoneNumber);
			System.out.println("Reason of Visit: "+reasonOfVisit);
			System.out.println("Sex: "+gender);
			System.out.println("Age: "+age);
			System.out.println("Blood Group: "+bloodGroup);
			System.out.println("User Role: "+userRole);

			if (email == null || email.equalsIgnoreCase(""))
				actionError = "Email is required<br/>";

			if (password == null || password.equalsIgnoreCase(""))
				actionError += "Password is required<br/>";

			if (confirmPassword == null || confirmPassword.equalsIgnoreCase(""))
				actionError += "Confirm password is required<br/>";

			if (password != null && confirmPassword != null && !password.equals(confirmPassword))
				actionError += "Password & confirm password do not match<br/>";

			if (actionError.equals("")) {
				wr.print("<script type=\"text/javascript\">window.alert(\"Patient Registration Done Successfully\");</script>");
				wr.print(this.home());
			}else
				wr.print(this.header()+this.registerBody(actionError)+this.footer());

		} else if (login) {
			System.out.println("executed");
			if (userRole.equalsIgnoreCase("Select User"))
				actionError = "You must select a user type<br/>";
			else{
				if (password == null || password.equalsIgnoreCase(""))
					actionError += "Password is required<br/>";

				if (password != null && !password.equals("bonny1234"))
					actionError += "Invalid password<br/>";
			}
			if (actionError.equals(""))
				wr.print(adminDashboard(email));
			else
				wr.print(this.header()+this.loginBody(actionError)+this.footer());
		}
	}

	public String home(){
		return this.header()+this.loginBody("")+this.footer();
	}

    public String loginBody(String actionError){
        return "<div class=\"row \">"
                    +"<div class=\"col-md-12\">"
                        +"<br /><br /><br /><br /><br /><br /><br /><br />"
                        +"<div class=\"panel panel-default login\">"
                            +"<div class=\"panel-heading logintitle\">Login</div>"
                            +"<div class=\"panel-body\">"
                                +"<form class=\"form-horizontal center-block\" role=\"form\" action=\"./home\" method=\"post\">"
									+"<input type=\"hidden\" name=\"action\" value=\"login\">"
                                    +"<div class=\"input-group input-group-lg\">"
                                        +"<span class=\"input-group-addon\" id=\"sizing-addon1\"><span class=\"glyphicon glyphicon-user\" aria-hidden=\"true\"></span></span>"
                                        +"<select class=\"form-control\" name=\"userrole\">"
                                            +"<option selected=\"selected\">Select User</option>"
                                            +"<option value=\"admin\">Admin</option>"
                                            +"<option value=\"patient\">Patient</option>"
                                        +"</select>"
                                    +"</div><br/>"
                                    +"<div>"
                                    +"</div>"
                                    +"<div class=\"input-group input-group-lg\">"
                                        +"<span class=\"input-group-addon\" id=\"sizing-addon1\"><span class=\"glyphicon glyphicon-envelope\" aria-hidden=\"true\"></span></span>"
                                        +"<input type=\"email\" class=\"form-control\" name=\"email\" placeholder=\"example@gmail.com\" required aria-describedby=\"sizing-addon1\">"
                                    +"</div>"
                                    +"<br />"
                                    +"<div class=\"input-group input-group-lg\">"
                                        +"<span class=\"input-group-addon\" id=\"sizing-addon1\"><span class=\"glyphicon glyphicon-lock\" aria-hidden=\"true\"></span></span>"
                                        +"<input type=\"password\" name=\"password\" class=\"form-control\" placeholder=\"Password\" required aria-describedby=\"sizing-addon1\">"
                                    +"</div>"
                                    +"<br />"
                                    +"<div class=\"col-sm-7 col-sm-offset-2\">"
                                        +"<button type=\"submit\" class=\"btn btn-primary btn-block btn-lg\">Login</button>"
                                    +"</div>"
                                +"</form>"
                            +"</div>"
							+"<div style=\"text-align:center;font-weight:bold;color:red\">" + (actionError != null? actionError : "") + "</div>"
                            +"<a href=\"/hospital-management-system1/home?action=register\" style=\"text-align:Center;font-weight:bold;font-size:120%;padding: 0 2%\">Register As Patient</a>"
                        +"</div>"
                    +"</div>"
                +"</div>";
    }

	public String registerBody(String actionError){
		return "<div class=\"row \">"
				+"<div class=\"col-md-12\">"
				+"<div class=\"panel panel-default login\">"
				+"<div class=\"panel-heading logintitle\">Register As Patient</div>"
				+"<div class=\"panel-body\">"
				+"<form class=\"form-horizontal center-block\" role=\"form\" action=\"./home\" method=\"POST\">"
				+"<input type=\"hidden\" name=\"action\" value=\"register\">"
				+"<div class=\"form-group\">"
				+"<label class=\"col-sm-2 control-label\">Patient Id:</label>"
				+"<div class=\"col-sm-10\">"
				+"<input type=\"number\" class=\"form-control\" name=\"patientid\" placeholder=\"unique_id auto generated\" readonly>"
				+"</div>"
				+"</div>"
				+"<div class=\"form-group\">"
				+"<label class=\"col-sm-2 control-label\">Name</label>"
				+"<div class=\"col-sm-10\">"
				+"<input type=\"text\" class=\"form-control\" name=\"patientname\" placeholder=\"Name\" required>"
				+"</div>"
				+"</div>"
				+"<div class=\"form-group\">"
				+"<label class=\"col-sm-2 control-label\">Email</label>"
				+"<div class=\"col-sm-10\">"
				+"<input type=\"email\" class=\"form-control\" name=\"email\" placeholder=\"Email\" required>"
				+"</div>"
				+"</div>"
				+"<div class=\"form-group\">"
				+"<label class=\"col-sm-2 control-label\">Password</label>"
				+"<div class=\"col-sm-10\">"
				+"<input type=\"password\" class=\"form-control\" name=\"password\" placeholder=\"Password\" required>"
				+"</div>"
				+"</div>"
				+"<div class=\"form-group\">"
				+"<label class=\"col-sm-2 control-label\">Confirm Password</label>"
				+"<div class=\"col-sm-10\">"
				+"<input type=\"password\" class=\"form-control\" name=\"confirmPassword\" placeholder=\"Password\" required>"
				+"</div>"
				+"</div>"
				+"<div class=\"form-group\">"
				+"<label class=\"col-sm-2 control-label\">Address</label>"
				+"<div class=\"col-sm-10\">"
				+"<input type=\"text\" class=\"form-control\" name=\"add\" placeholder=\"Address\" required>"
				+"</div>"
				+"</div>"
				+"<div class=\"form-group\">"
				+"<label class=\"col-sm-2 control-label\">Phone</label>"
				+"<div class=\"col-sm-10\">"
				+"<input type=\"text\" class=\"form-control\" name=\"phone\" placeholder=\"Phone No.\" required>"
				+"</div>"
				+"</div>"
				+"<div class=\"form-group\">"
				+"<label class=\"col-sm-2 control-label\">Reason Of Visit</label>"
				+"<div class=\"col-sm-10\">"
				+"<input type=\"text\" class=\"form-control\" name=\"rov\" placeholder=\"Reason Of Visit\" required>"
				+"</div>"
				+"</div>"
				+"<div class=\"form-group\">"
				+"<label class=\"col-sm-2 control-label\">Room No</label>"
				+"<div class=\"col-sm-10\">"
				+"<input type=\"text\" class=\"form-control\" value=\"\" name=\"roomNo\" placeholder=\"Left for Admin\" readonly>"
				+"</div>"
				+"</div>"
				+"<div class=\"form-group\">"
				+"<label class=\"col-sm-2 control-label\">Bed No</label>"
				+"<div class=\"col-sm-10\">"
				+"<input type=\"text\" class=\"form-control\" name=\"bedNo\" placeholder=\"Left for Admin\" readonly>"
				+"</div>"
				+"</div>"
				+"<div class=\"form-group\">"
				+"<label class=\"col-sm-2 control-label\">To Be reffered To</label>"
				+"<div class=\"col-sm-10\">"
				+"<input type=\"text\" class=\"form-control\" name=\"doct\" placeholder=\"Left for Admin\" readonly>"
				+"</div>"
				+"</div>"
				+"<div class=\"form-group\">"
				+"<label class=\"col-sm-2 control-label\">Sex</label>"
				+"<div class=\"col-sm-2\">"
				+"<select class=\"form-control\" name=\"gender\">"
				+"<option>Male</option>"
				+"<option>Female</option>"
				+"</select>"
				+"</div>"
				+"</div>"
				+"<div class=\"form-group\">"
				+"<label class=\"col-sm-2 control-label\">Admission Date</label>"
				+"<div class=\"col-sm-10\">"
				+"<input type=\"text\" class=\"form-control\" name=\"joindate\" placeholder=\"Left For Admin\" readonly>"
				+"</div>"
				+"</div>"
				+"<div class=\"form-group\">"
				+"<label class=\"col-sm-2 control-label\">Age</label>"
				+"<div class=\"col-sm-10\">"
				+"<input type=\"text\" class=\"form-control\" name=\"age\" placeholder=\"Age\" required>"
				+"</div>"
				+"</div>"
				+"<div class=\"form-group\">"
				+"<label class=\"col-sm-2 control-label\">Blood Group</label>"
				+"<div class=\"col-sm-2\">"
				+"<select class=\"form-control\" name=\"bgroup\">"
				+"<option>A<sup>+</sup></option>"
				+"<option>A<sup>-</sup></option>"
				+"<option>B<sup>+</sup></option>"
				+"<option>B<sup>-</sup></option>"
				+"<option>AB<sup>+</sup></option>"
				+"<option>AB<sup>-</sup></option>"
				+"<option>O<sup>+</sup></option>"
				+"<option>O<sup>-</sup></option>"
				+"</select>"
				+"</div>"
				+"</div>"
				+"<div style=\"text-align:center;font-weight:bold;color:red\">" + (actionError != null? actionError : "") + "</div>"
				+"<div class=\"form-group\">"
				+"<div class=\"col-sm-7 col-sm-offset-2\" style=\"margin:0 0 0 40%\">"
				+"<button type=\"submit\" class=\"btn btn-primary\">Register As Patient Now</button>"
				+"</div>"
				+"</div>"
				+"<br><Br><Br>"
				+"</form>"
				+"</div>"
				+"</div>"
				+"</div>"
				+"</div>";
	}

    public String header(){
        return "<!DOCTYPE html>"
                +"<html lang=\"en\">"
                +"<head>"
                    +"<meta charset=\"utf-8\">"
                    +"<meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">"
                    +"<meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">"
                    +"<title>Online Hospital Management System</title>"
                    +"<link href=\"./css/bootstrap.min.css\" rel=\"stylesheet\">"
                    +"<link href=\"./css/style.css\" rel=\"stylesheet\">"
                    +"<script src=\"./js/jquery.js\"></script>"
                +"</head>"
                +"<body>"
                    +"<div class=\"container-fluid\">"
                        +"<div class=\"row navbar-fixed-top\">"
                            +"<nav class=\"navbar navbar-default header\">"
                                +"<div class=\"container-fluid\">"
                                    +"<div class=\"navbar-header\">"
                                        +"<a class=\"navbar-brand logo\" href=\"#\">"
                                            +"<img alt=\"Brand\" src=\"images/logo.png\">"
                                        +"</a>"
                                        +"<div class=\"navbar-text title\">"
                                            +"<p>Hospital Management System"
                                            +"<p>"
                                        +"</div>"
                                    +"</div>"
                                +"</div>"
                            +"</nav>"
                        +"</div>";
    }

    public String footer(){
        return "<div class=\"row footer navbar-fixed-bottom\">"
                    +"<div class=\"col-md-12\">"
                        +"<div>Botien Technologies</div>"
                        +"<p>Copyrights © 2022. All rights reserved. </p>"
                    +"</div>"
                +"</div>"
            +"</div>"
            +"<script src=\"js/bootstrap.min.js\"></script>"
        +"</body>"
        +"</html>";
    }
	public String adminDashboard(String email){
		return "<!DOCTYPE html>\n" +
				"<html lang=\"en\">\n" +
				"<head>\n" +
				"    <meta charset=\"utf-8\">\n" +
				"    <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\n" +
				"    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\n" +
				"    <link href=\"images/logo.png\" rel=\"icon\" />\n" +
				"    <title>Online Hospital Management System</title>\n" +
				"    <!-- Bootstrap -->\n" +
				"    <link href=\"css/bootstrap.min.css\" rel=\"stylesheet\">\n" +
				"    <link href=\"css/style.css\" rel=\"stylesheet\">\n" +
				"    <script src=\"js/jquery.js\"></script>\n" +
				"    <script type=\"text/javascript\">\n" +
				"        $(document).ready(function () {\n" +
				"            $('#doctorlist').show();\n" +
				"            $('.doctor li:first-child a').addClass('active');\n" +
				"            $('.doctor li a').click(function (e) {\n" +
				"                var tabDiv = this.hash;\n" +
				"                $('.doctor li a').removeClass('active');\n" +
				"                $(this).addClass('.active');\n" +
				"                $('.switchgroup').hide();\n" +
				"                $(tabDiv).fadeIn();\n" +
				"                e.preventDefault();\n" +
				"            });\n" +
				"        });\n" +
				"    </script>\n" +
				"</head>\n" +
				"\n" +
				"<body>\n" +
				"    <div class=\"container-fluid\">\n" +
				"        <!--- Header Start -------->\n" +
				"        <div class=\"row header\">\n" +
				"            <div class=\"col-md-10\">\n" +
				"                <div class=\"navbar-header\">\n" +
				"                    <a class=\"navbar-brand logo\" href=\"#\">\n" +
				"                        <img alt=\"Brand\" src=\"images/logo.png\">\n" +
				"                    </a>\n" +
				"                    <div class=\"navbar-text title\">\n" +
				"                        <p>Hospital Management System\n" +
				"                        <p>\n" +
				"                    </div>\n" +
				"                </div>\n" +
				"            </div>\n" +
				"\n" +
				"            <div class=\"col-md-2 \">\n" +
				"                <ul class=\"nav nav-pills \">\n" +
				"                    <li class=\"dropdown dmenu\">\n" +
				"                        <a href=\"#\" class=\"dropdown-toggle\" data-toggle=\"dropdown\" role=\"button\" aria-haspopup=\"true\"\n" +
				"                            aria-expanded=\"false\">\n" +
				                            email +"<span class=\"caret\"></span>\n" +
				"                        </a>\n" +
				"                        <ul class=\"dropdown-menu \">\n" +
				"                            <li><a href=\"profile.jsp\">Change Profile</a></li>\n" +
				"                            <li role=\"separator\" class=\"divider\"></li>\n" +
				"                            <li><a href=\"logout.jsp\">Logout</a></li>\n" +
				"                        </ul>\n" +
				"                    </li>\n" +
				"                </ul>\n" +
				"            </div>\n" +
				"        </div>\n" +
				"        <!--- Header Ends --------->\n" +
				"        <div class=\"row\">\n" +
				"            <!----- Menu Area Start ------>\n" +
				"            <div class=\"col-md-2 menucontent\">\n" +
				"                <a href=\"#\">\n" +
				"                    <h1>Dashboard</h1>\n" +
				"                </a>\n" +
				"                <ul class=\"nav nav-pills nav-stacked\">\n" +
				"                    <li role=\"presentation\"><a href=\"#\">Department</a></li>\n" +
				"                    <li role=\"presentation\"><a href=\"#\">Doctors</a></li>\n" +
				"                    <li role=\"presentation\"><a href=\"#\">Patients</a></li>\n" +
				"                    <li role=\"presentation\"><a href=\"#\">Nurse</a></li>\n" +
				"                    <li role=\"presentation\"><a href=\"#\">Room</a></li>\n" +
				"                    <li role=\"presentation\"><a href=\"#\">Pathology</a></li>\n" +
				"                    <li role=\"presentation\"><a href=\"#\">Blood Donor</a></li>\n" +
				"                    <li role=\"presentation\"><a href=\"#\">Billing</a></li>\n" +
				"                    <li role=\"presentation\"><a href=\"#\">Search</a></li>\n" +
				"                </ul>\n" +
				"            </div>\n" +
				"            <!---- Menu Ares Ends  -------->\n" +
				"            <!-------   Content Area start  --------->\n" +
				"            <div class=\"col-md-10 maincontent\">\n" +
				"                <!-----------  Content Menu Tab Start   ------------>\n" +
				"                <div class=\"panel panel-default contentinside\">\n" +
				"                    <div class=\"panel-heading\">Manage Department</div>\n" +
				"                    <!----------------   Panel Body Start   --------------->\n" +
				"                    <div class=\"panel-body\">\n" +
				"                        <ul class=\"nav nav-tabs doctor\">\n" +
				"                            <li role=\"presentation\"><a href=\"#doctorlist\">Department List</a></li>\n" +
				"                            <li role=\"presentation\"><a href=\"#adddoctor\">Add Department</a></li>\n" +
				"                        </ul>\n" +
				"                        <!----------------   Display Department Data List start   --------------->\n" +
				"\n" +
				"                        <div id=\"doctorlist\" class=\"switchgroup\">\n" +
				"                            <table class=\"table table-bordered table-hover\">\n" +
				"                                <tr class=\"active\">\n" +
				"                                    <td>Department ID</td>\n" +
				"                                    <td>Department Name</td>\n" +
				"                                    <td>Department Description</td>\n" +
				"                                    <td>Options</td>\n" +
				"                                </tr>\n" +
				"                                <tr>\n" +
				"                                    <td>\n" +
				"                                        23\n" +
				"                                    </td>\n" +
				"                                    <td>\n" +
				"                                        Therapy Department\n" +
				"                                    </td>\n" +
				"                                    <td>\n" +
				"                                        Example Description\n" +
				"                                    </td>\n" +
				"                                    <td>\n" +
				"                                        <button type=\"button\" class=\"btn btn-primary\" data-toggle=\"modal\"\n" +
				"                                            data-target=\"#myModal<%=deptId%>\"><span class=\"glyphicon glyphicon-wrench\"\n" +
				"                                                aria-hidden=\"true\"></span></button>\n" +
				"                                        <a href=\"add delete validation here\" class=\"btn btn-danger\"\n" +
				"                                            onclick=\"return confirmDelete()\"><span class=\"glyphicon glyphicon-trash\"\n" +
				"                                                aria-hidden=\"true\"></span></a>\n" +
				"\n" +
				"                                    </td>\n" +
				"                                </tr>\n" +
				"                            </table>\n" +
				"                        </div>\n" +
				"                        <!----------------   Display Department Data List ends   --------------->\n" +
				"\n" +
				"                        <!------ Edit Department Modal Start ---------->\n" +
				"                        <div class=\"modal fade\" id=\"myModal<%=deptId%>\" tabindex=\"-1\" role=\"dialog\"\n" +
				"                            aria-labelledby=\"myModalLabel\">\n" +
				"                            <div class=\"modal-dialog\" role=\"document\">\n" +
				"                                <div class=\"modal-content\">\n" +
				"                                    <div class=\"modal-header\">\n" +
				"                                        <button type=\"button\" class=\"close\" data-dismiss=\"modal\"\n" +
				"                                            aria-label=\"Close\"><span aria-hidden=\"true\">&times;</span></button>\n" +
				"                                        <h4 class=\"modal-title\" id=\"myModalLabel\">Edit Department Information\n" +
				"                                        </h4>\n" +
				"                                    </div>\n" +
				"\n" +
				"                                    <div class=\"modal-body\">\n" +
				"                                        <div class=\"panel panel-default\">\n" +
				"                                            <div class=\"panel-body\">\n" +
				"                                                <form class=\"form-horizontal\" action=\"edit_dept_validation.jsp\">\n" +
				"\n" +
				"                                                    <div class=\"form-group\">\n" +
				"                                                        <label class=\"col-sm-4 control-label\">Department\n" +
				"                                                            ID</label>\n" +
				"                                                        <div class=\"col-sm-4\">\n" +
				"                                                            <input type=\"number\" class=\"form-control\" name=\"deptId\"\n" +
				"                                                                value=\"<%=deptId%>\" readonly=\"readonly\">\n" +
				"                                                        </div>\n" +
				"                                                    </div>\n" +
				"\n" +
				"                                                    <div class=\"form-group\">\n" +
				"                                                        <label class=\"col-sm-4 control-label\">Department\n" +
				"                                                            Name</label>\n" +
				"                                                        <div class=\"col-sm-4\">\n" +
				"                                                            <input type=\"text\" class=\"form-control\" name=\"deptName\"\n" +
				"                                                                value=\"<%=deptName%>\">\n" +
				"                                                        </div>\n" +
				"                                                    </div>\n" +
				"\n" +
				"                                                    <div class=\"form-group\">\n" +
				"                                                        <label class=\"col-sm-4 control-label\">Department\n" +
				"                                                            Description</label>\n" +
				"                                                        <div class=\"col-sm-4\">\n" +
				"                                                            <input type=\"text\" class=\"form-control\" name=\"deptDesc\"\n" +
				"                                                                value=\"<%=deptDesc%>\">\n" +
				"                                                        </div>\n" +
				"                                                    </div>\n" +
				"\n" +
				"                                                    <div class=\"modal-footer\">\n" +
				"                                                        <button type=\"button\" class=\"btn btn-default\"\n" +
				"                                                            data-dismiss=\"modal\">Close</button>\n" +
				"                                                        <input type=\"submit\" class=\"btn btn-primary\"\n" +
				"                                                            value=\"Update\"></button>\n" +
				"                                                    </div>\n" +
				"                                                </form>\n" +
				"                                            </div>\n" +
				"                                        </div>\n" +
				"                                    </div>\n" +
				"                                </div>\n" +
				"                            </div>\n" +
				"                        </div>\n" +
				"                        <!----------------   Modal ends here  --------------->\n" +
				"\n" +
				"                        <!----------------   Add Department Start   --------------->\n" +
				"                        <div id=\"adddoctor\" class=\"switchgroup\">\n" +
				"                            <div class=\"panel panel-default\">\n" +
				"                                <div class=\"panel-body\">\n" +
				"                                    <form class=\"form-horizontal\" action=\"add_dept_validation.jsp\">\n" +
				"                                        <div class=\"form-group\">\n" +
				"                                            <label class=\"col-sm-4 control-label\">Department ID</label>\n" +
				"                                            <div class=\"col-sm-4\">\n" +
				"                                                <input type=\"number\" class=\"form-control\" name=\"deptId\"\n" +
				"                                                    placeholder=\"ID Auto Generated\" readonly>\n" +
				"                                            </div>\n" +
				"                                        </div>\n" +
				"\n" +
				"                                        <div class=\"form-group\">\n" +
				"                                            <label class=\"col-sm-4 control-label\">Department Name</label>\n" +
				"                                            <div class=\"col-sm-4\">\n" +
				"                                                <input type=\"text\" class=\"form-control\" name=\"deptName\"\n" +
				"                                                    placeholder=\"Enter Department Name\">\n" +
				"                                            </div>\n" +
				"                                        </div>\n" +
				"\n" +
				"                                        <div class=\"form-group\">\n" +
				"                                            <label class=\"col-sm-4 control-label\">Department\n" +
				"                                                Description</label>\n" +
				"                                            <div class=\"col-sm-4\">\n" +
				"                                                <input type=\"text\" class=\"form-control\" name=\"deptDesc\"\n" +
				"                                                    placeholder=\"Enter Department Description here...\">\n" +
				"                                            </div>\n" +
				"                                        </div>\n" +
				"\n" +
				"\n" +
				"                                        <div class=\"form-group\">\n" +
				"                                            <div class=\"col-sm-offset-4 col-sm-10\">\n" +
				"                                                <button type=\"submit\" class=\"btn btn-primary\">Add\n" +
				"                                                    Department</button>\n" +
				"                                            </div>\n" +
				"                                        </div>\n" +
				"                                    </form>\n" +
				"                                </div>\n" +
				"                            </div>\n" +
				"                        </div>\n" +
				"                        <!----------------   Add Department Ends   --------------->\n" +
				"                    </div>\n" +
				"                    <!----------------   Panel Body Ends   --------------->\n" +
				"                </div>\n" +
				"                <!-----------  Content Menu Tab Ends   ------------>\n" +
				"            </div>\n" +
				"            <!-------   Content Area Ends  --------->\n" +
				"        </div>\n" +
				"        <script src=\"js/bootstrap.min.js\"></script>\n" +
				"    </div>\n" +
				"</body>\n" +
				"\n" +
				"</html>";
	}
}