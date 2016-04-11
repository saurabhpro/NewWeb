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
                    Mailbox
                </h1>
            </section>

            <!-- Main content -->
            <form action="email" method="post">
                <!-- /.col -->
                <div class="col-md-9">
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
                                       value="Mail Regarding Discrepancies">
                            </div>
                            <div class="form-group" class="form-control">
                                <ul>
                                      <li ng-repeat="day in (leaveNotApplied = (items.allDateDetailsList | filter:{Selected:true, checkOut: '00:00'}))"></li>
                                  </ul>
                                  <ul>
                                      <li ng-repeat="day in (halfDay = (items.allDateDetailsList | filter:{Selected:true, attendanceStatusType: 'HALF_DAY'}))"></li>
                                  </ul>
                                <ul>
                                    <li ng-repeat="day in (checkOutTime = (items.allDateDetailsList | filter:{Selected:true, attendanceStatusType: 'NA'}))"></li>
                                </ul>
                                <div ng-show=leaveNotAppliedFunc()>

                                    {{leaveNotApplied}}
                                    hello
                                </div>
                                <textarea type="text" name="message" id="editor" class="form-control"
                                          style="outline: none; height: 85px; border: none; float: left;"
                                          contenteditable="true">Hi {{items.empName}},You have not entered the below leave days for the month of Jan on Financial Force, and nor have you registered your attendance in the biometric system for these days:</textarea>
                                <input type="text" style="font-weight: bold; color: red; border: none;" name="message"
                                       value="Full Day Leaves"/>
                                <ul style="font-weight: bold; border: none;">
                                        <li ng-repeat="day in (filteredArray = (items.allDateDetailsList | filter:{Selected:true, checkOut: 'NA'}))">
                                            <input type="text" style="outline: none; border: none;" name="message"
                                                   value={{day.currentDate}}>
                                        </li>
                                    </ul>
                                <ul style="font-weight: bold; border: none; ">
                                    <input type="text" style="font-weight: bold; color: red; border: none;"
                                           name="message" value="Half Day Leaves"/>
                                    <li ng-repeat="day in (filteredArray = (items.allDateDetailsList | filter:{Selected:true, attendanceStatusType: 'HALF_DAY'}))">
                                        <input type="text" name="message" style="outline: none; border: none;"
                                               value={{day.currentDate}}>
                                    </li>
                                </ul>

                                <input name="message" class="form-control"
                                       style="height: 40px; border: none; float: left; font-weight: bold; outline: none;"
                                       value="Request you to apply for these days in Financial Force."/>
                                <textarea name="message" class="form-control"
                                          style="height: 60px; border: none; float: left;"> You have not Checked In/Check Out on the below mentioned days through the biometric system. Request you to send the Check-In/Check-out-time for these days.</textarea>
                                <input type="text" style="font-weight: bold; color: red; border: none;" name="message"
                                       value="Days"/>
                                <ul style="font-weight: bold; border: none; ">
                                    <li ng-repeat="day in (filteredArray = (items.allDateDetailsList | filter:{Selected:true, attendanceStatusType: '00:00'}))">
                                        <input type="text" name="message" style="outline: none; border: none;"
                                               value={{day.currentDate}}>
                                        </li>
                                    </ul>

                            </div>
                        </div>
                    </div>
                    <div class="box-footer">
                        <div class="pull-right">
                            <!--     <button type="button" class="btn btn-default"><i class="fa fa-pencil"></i> Draft</button> -->
                            <button type="submit" class="btn btn-primary"><i
                                    class="fa fa-envelope-o"></i> Send
                            </button>
                        </div>

                    </div>
                    <!-- /.box-footer -->
                </div>
                <!-- /. box -->
            </form>
        </div>
        <!-- /.col -->
        <!-- /.row -->

        <div class="modal-footer">
            <button type="reset" ng-click="cancel()" class="btn btn-default"><i class="fa fa-times"></i>
                Discard
            </button>
        </div>
        <!-- /.content -->
    </div>

    </div>
</script>
<!-- ./wrapper -->

<!-- jQuery 2.2.0 -->
<script src="bower_components/jquery/dist/jquery.min.js"></script>
<!-- Bootstrap 3.3.6 -->
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>
<!-- Slimscroll -->
<script src="js/slimScroll/jquery.slimscroll.min.js"></script>
<!-- AdminLTE App -->
<script src="js/js/app.min.js"></script>
<!-- AdminLTE for demo purposes -->
<script src="js/js/demo.js"></script>
<!-- Bootstrap WYSIHTML5 -->
<script src="js/bootstrap-wysihtml5.js"></script>
<!-- Page Script -->
<script>
    $(function () {
        //Add text editor
        $("#compose-textarea").wysihtml5();
    });
</script>
</body>
</html>
