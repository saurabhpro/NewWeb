<%--
  Created by IntelliJ IDEA.
  User: AmritaArora
  Date: 4/10/2016
  Time: 10:19 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html ng-app="sampleApp">
<div ng-include="'ComposeEmail.jsp'"></div>
<head>
    <meta charset="utf-8">
    <title>Compose Message</title>
    <!-- Tell the browser to be responsive to screen width -->
    <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
    <!-- Bootstrap 3.3.6 -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">
    <!-- Font Awesome -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.5.0/css/font-awesome.min.css">
    <!-- Ionicons -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/ionicons/2.0.1/css/ionicons.min.css">

    <!-- bootstrap wysihtml5 - text editor -->
    <link rel="stylesheet" href="css/bootstrap-wysihtml5.css">

    <style>
        table {
            border-collapse: collapse;
        }

        table, td, th {
            border: 1px solid black;
        }

        textarea {
            border: none;
            overflow: auto;
            outline: none;
        }
    </style>
    <script src="bower_components/angular/angular.js"></script>
    <script src="bower_components/angular-route/angular-route.js"></script>
    <script src="bower_components/angular-animate/angular-animate.js"></script>
    <script src="bower_components/angular-bootstrap/ui-bootstrap-tpls.js"></script>
    <script src="bower_components/angular-sanitize/angular-sanitize.js"></script>

  <!-- jsp scriptlet -->
  <%
      Cookie[] cookies = request.getCookies();
      String monthInfo = "";
      if (cookies != null)
          for (Cookie cookie : cookies)
              if (cookie.getName().equals("monthInfo"))
                  monthInfo = "" + cookie.getValue();
  %>
</head>
  
<body style="background-color: white">
<div id="temp"></div>
<script type="text/ng-template" id="ComposeEmail.jsp">

    <div class="wrapper" ng-controller="GenerateDiscrepancyController">

        <!-- Content Wrapper. Contains page content -->
        <div class="content-wrapper">
            <!-- Content Header (Page header) -->
            <section class="content-header">
                <h1>
                    &nbsp Mailbox
                </h1>
            </section>

            <!-- Main content -->
            <div class="col-md-9">
                <form action="email" method="post">
                    <!-- /.col -->
                    <div class="box box-primary">
                        <div class="box-header with-border">
                            <h3 class="box-title">Compose New Message</h3>
                        </div>
                        <!-- /.box-header -->
                        <div class="box-body">
                            <div class="form-group">
                                <input class="form-control" placeholder="To:" value={{items.empEmailId}} name="to">
                            </div>
                            <div class="form-group">
                                <input class="form-control" placeholder="Subject:" name="subject"
                                       value="Leave Discrepancies - <%=monthInfo%>">
                            </div>
                            <input type="hidden" name="message" value={{items.empName}}>
                            <div class="form-group" class="form-control">
                                Hi {{items.empName}},
                                <ul>
                                    <li ng-repeat="day in (leaveNotApplied = (items.allDateDetailsList | filter:{Selected:true, checkOut: '!00:00', attendanceStatusType: '!HALF_DAY' }))"></li>
                                </ul>
                                <ul>
                                    <li ng-repeat="day in (halfDay = (items.allDateDetailsList | filter:{Selected:true, attendanceStatusType: 'HALF_DAY'}))"></li>
                                </ul>
                                <ul>
                                    <li ng-repeat="day in (checkOutTime = (items.allDateDetailsList | filter:{Selected:true, checkOut: '00:00'}))"></li>
                                </ul>
                                <!--
                                <input type="text" name="message" style="width: 20px; border: none; outline: none; "
                                       value="Hi">
                                <input type="text" name="message" style="border: none; outline: none; "
                                       value={{items.empName}}>
                                -->


                            <textarea ng-show=discrepancyFound() type="text" name="message2"
                                      class="form-control"
                                      style="outline: none; height: 85px; border: none; float: left;"
                                      contenteditable="true">You have not entered the below leave days on Financial Force, and nor have you registered your attendance in the biometric system for these days:
                            </textarea>

                                <input type="text" ng-show=leaveNotAppliedFunc()
                                       style="font-weight: bold; color: red; border: none; position: relative;"
                                       name="message3"
                                       value="Full Day Leaves"/>
                                <ul ng-show=leaveNotAppliedFunc()
                                    style="font-weight: bold; border: none; position: relative;">
                                    <li ng-repeat="day in leaveNotApplied">
                                        <input name="message4" type="text" style="outline: none; border: none;"
                                               value={{day.currentDate}}>
                                    </li>
                                </ul>

                                <input ng-show=halfDayFunc() type="text"
                                       style="font-weight: bold; color: red; border: none;"
                                       name="message5" value="Half Day Leaves"/>
                                <ul ng-show=halfDayFunc() style="font-weight: bold; border: none; ">
                                    <li ng-repeat="day in halfDay">
                                        <input type="text" name="message6" style="outline: none; border: none;"
                                               value={{day.currentDate}}>
                                    </li>
                                </ul>

                                <input ng-show=discrepancyFound() name="message7" class="form-control"
                                       style="height: 40px; border: none; float: left; font-weight: bold; outline: none;"
                                       value="Request you to apply for these days in Financial Force."/>

                                <textarea ng-show=checkOutTimeNotApplicable() name="message8" class="form-control"
                                          style="height: 60px; border: none; float: left;"> You have not Checked In/Check Out on the below mentioned days through the biometric system. Request you to send the Check-In/Check-out-time for these days: </textarea>
                                <input ng-show=checkOutTimeNotApplicable() type="text"
                                       style="font-weight: bold; color: red; border: none;" name="message9"
                                       value="Days"/>
                                <ul ng-show=checkOutTimeNotApplicable() style="font-weight: bold; border: none; ">
                                    <li ng-repeat="day in checkOutTime">
                                        <input type="text" name="message10" style="outline: none; border: none;"
                                               value={{day.currentDate}}>
                                    </li>
                                </ul>
                            </div>
                        </div>
                    </div>

                    <div class="modal-footer">

                        <!--     <button type="button" class="btn btn-default"><i class="fa fa-pencil"></i> Draft</button> -->
                        <button type="submit" class="btn btn-primary btn-small-block">
                            <i class="fa fa-envelope-o"></i>&nbsp Send
                        </button>

                        <button style="float:right;" type="reset" ng-click="cancel()"
                                class="btn btn-warning btn-small-block">
                            <i class="fa fa-times"></i>&nbsp Discard
                        </button>
                    </div>
                    <!-- /.box-footer -->

                    <!-- /. box -->
                </form>

            </div>
            <!-- TODO Seems like this is required here and the upper footer is embeded here-->
            <div class="modal-footer">
            </div>
            
        </div>
        <!-- /.col -->
        <!-- /.row -->
        <!-- /.content -->
    </div>

</script>
<!-- ./wrapper -->

<!-- jQuery 2.2.0 -->
<script src="bower_components/jquery/dist/jquery.min.js"></script>
<!-- Bootstrap 3.3.6 -->
<script src="bower_components/bootstrap/dist/js/bootstrap.min.js"></script>
<!-- Page Script -->
<script>
    $(function () {
        //Add text editor
        $("#compose-textarea").wysihtml5();
    });
</script>
</body>
</html>
