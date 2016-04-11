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
    </style>
    <script src="bower_components/angular/angular.js"></script>
    <script src="bower_components/angular-route/angular-route.js"></script>
    <script src="bower_components/angular-animate/angular-animate.js"></script>
    <script src="bower_components/angular-bootstrap/ui-bootstrap-tpls.js"></script>
    <script src="bower_components/angular-sanitize/angular-sanitize.js"></script>
    <script type="text/javascript">
        function onloadPage() {
            document.getElementById('textArea').style.display = "none";
            document.getElementById('temp').innerHTML= "hello";
        }
        function submitForm() {

            var editor = ace.edit("editor");
            var code = editor.getSession().getValue();
            document.getElementById('textArea').style.display = "block";
            document.getElementById('textArea').value = code;
        }
    </script>
</head>
<body onload="return onloadPage();" style="background-color: white">
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
                            <div class="form-group">
                                <!--  <ul>
                                      <li ng-repeat="day in (filteredArray = (items.allDateDetailsList | filter:{Selected:true, checkOut: '00:00'}))"><td> {{day.currentDate | date}}</td></li>
                                  </ul>
                                  <ul>
                                      <li ng-repeat="day in (filteredArray = (items.allDateDetailsList | filter:{Selected:true, attendanceStatusType: 'HALF_DAY'}))"><td> {{day.currentDate | date}}</td></li>
                                  </ul>
                                  -->
                                <div id="editor" class="form-control" style="height: 300px" contenteditable="true">

                                    Hi {{items.empName}},
                                    <br/>
                                    You have not entered the below leave days for the month of Jan on <span
                                        style="font-weight: bold;">Financial Force</span>, and nor have you
                                    registered your attendance in the biometric system for these days:

                                    <p name = "message" style="font-weight: bold; color: red;">Full Day Leaves </p>
                                    <ul style="font-weight: bold;">
                                        <li ng-repeat="day in (filteredArray = (items.allDateDetailsList | filter:{Selected:true, checkOut: '00:00'}))">
                                            <td> {{day.currentDate | date}}</td>
                                        </li>
                                    </ul>
                                    <p style="font-weight: bold; color: red;">Half Day Leaves </p>
                                    <ul style="font-weight: bold;">
                                        <li ng-repeat="day in (filteredArray = (items.allDateDetailsList | filter:{Selected:true, attendanceStatusType: 'HALF_DAY'}))">
                                            <td> {{day.currentDate | date}}</td>
                                        </li>
                                    </ul>

                                    <span style="font-weight: bold;" Request you to apply for these days in Financial
                                          Force.></span>
                                    You have not <span style="font-weight: bold">Checked In/Check Out </span> on the
                                    below mentioned days through the biometric system. Request you to send the <span
                                        style="font-weight: bold">Check-In/Check-out-time </span> for these days.
                                    <br/>
                                    <p style="font-weight: bold; color: red;">Days </p>
                                    <ul style="font-weight: bold;">
                                        <li ng-repeat="day in items.allDateDetailsList  | filter:{Selected:true, checkOut: '00:00'}">
                                            <td> {{day.currentDate | date}}</td>
                                        </li>
                                    </ul>
                                </div>
                                <!--    <ul>
                                        <li ng-repeat="day in items.allDateDetailsList  | filter:{Selected:true, checkOut: 00:00}">
                                            <td> {{day.currentDate | date}}</td>
                                        </li>
                                    </ul>

                                    <table name="message" style="border: 10px">
                                        <thead style="color: red;">
                                        <th>
                                            <input type="text" width="10px" value="Full Day Leave"/>
                                        </th>
                                        <th>
                                            <input type="text" width="10px" value="Half Day Leave"/>
                                        </th>
                                        </thead>
                                        <tbody>
                                        <tr>
                                            <td><input type="text" value="22"></td>
                                            <td><input type="text" value="22"></td>
                                        </tr>

                                        </tbody>
                                    </table>

                                    <ul>
                                        <li ng-repeat="day in (filteredArray = (items.allDateDetailsList | filter:{Selected:true, checkOut: 'NA'}))"><td> {{day.currentDate | date}}</td></li>
                                    </ul>
                                    -->
                            </div>
                        </div>
                    </div>
                    <!-- /.box-body -->
                    <textarea id="textArea">

                    </textarea>
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
        </div>
        <!-- /.col -->
        <!-- /.row -->
        </form>
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
