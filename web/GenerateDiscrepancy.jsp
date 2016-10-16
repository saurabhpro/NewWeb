<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html ng-app="sampleApp" ng-controller="GenerateDiscrepancyController" lang="en" class="no-js">
<div ng-include="GenerateDiscrepancy.jsp"></div>
<head>
    <!-- Meta Tags -->
    <meta charset="UTF-8">
    <meta http-equiv="Pragma" content="no-cache">
    <meta http-equiv="Cache-Control" content="no-cache">
    <meta http-equiv="Expires" content="Sat, 01 Dec 2001 00:00:00 GMT">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <!-- css -->
    <link rel="stylesheet" href="bower_components/bootstrap/dist/css/bootstrap.min.css">

    <!-- js scripts, since there could be dependencies problem, maintain the inclusion order -->
    <script src="bower_components/angular/angular.js"></script>
    <script src="bower_components/angular-route/angular-route.js"></script>
    <script src="bower_components/angular-animate/angular-animate.js"></script>
    <script src="bower_components/angular-bootstrap/ui-bootstrap-tpls.js"></script>

    <script src="js/app.js"></script>
    <script language="javascript">
        function LoadOnce() {
            window.location.reload();
        }
    </script>

    <!-- jsp scriptlet -->
    <%
        Cookie[] cookies = request.getCookies();
        String headline = "Monthly Discrepancy List";
        if (cookies != null)
            for (Cookie cookie : cookies)
                if (cookie.getName().equals("month"))
                    headline = " Discrepancy Report for " + cookie.getValue();
    %>
</head>

<body onload="LoadOnce()">
<main class="cd-main-content">
    <div class="content-wrapper">
        <br>
        <h2 style="text-align: center">
            <%=headline%>
        </h2>
        <%-- <%
        String msg=(String)session.getAttribute("successfulMessage");
            if(msg!=null)
                out.print(msg);
        %>
       <%= request.getParameter("successfulMessage") %>
        <%out.print(session.getAttribute("successfulMessage"));%>
        --%>
        <form action="multigen" method="post">
            <input type="hidden" name="fileToUse" value="DiscrepancyInWorkers">
            <button class="btn btn-danger" style="float: right;" type="submit" ng-click="showSelected()"
                    name="DownloadButtonType"
                    value="DOWNLOAD_PDF">
                DOWNLOAD PDF
            </button>
            <button class="btn btn-success" style="float: right;" type="submit" ng-click="showSelected()"
                    name="DownloadButtonType"
                    value="DOWNLOAD_EXCEL">
                DOWNLOAD EXCEL
            </button>
            <input type="hidden" name="listOfIds" value={{itemsSelected}}>
        </form>
        <form>
            <br/>
            <br/>
            <br/>

            <table name="empListTable" class="table table-striped table-condensed">
                <thead>
                <tr style="font-weight:bolder;">
                    <th style="width: 40px">
                        <input type="checkbox" ng-model="selectedAll" ng-click="checkAll()"/>
                    </th>
                    <th style="width: 200px">EMPLOYEE ID</th>
                    <th style="width: 200px">SALESFORCE ID</th>
                    <th style="width: 200px">EMPLOYEE NAMES</th>
                    <th style="width: 400px">EMPLOYEE EMAILS</th>
                    <th style="width: 200px">AVERAGE CHECK-IN</th>
                    <th style="width: 200px">AVERAGE CHECK-OUT</th>
                    <th style="width: 200px">AVERAGE WORK HOURS</th>
                </tr>
                </thead>
                <tbody>
                <tr ng-repeat="item in rowCollection">

                    <td style="width: 40px"><input type="checkbox" ng-bind="modelName" ng-model="item.Selected"
                                                   ng-click="checkStatus()"></td>
                    <td ng-model="item.empRevalId">{{item.empRevalId}}</td>
                    <td>{{item.empSalesforceId}}</td>
                    <td>{{item.empName}}</td>
                    <td>{{item.empEmailId}}</td>
                    <td>{{item.empAvgCheckInTimeForMonth}}</td>
                    <td>{{item.empAvgCheckOutTimeForMonth}}</td>
                    <td>{{item.empAvgWorkHoursForMonth}}</td>
                    <td>
                        <button type="button" class="btn btn-info-outline" value="View Details"
                                ng-click="open(item,'lg')">View Details
                        </button>
                    </td>

                </tr>
                </tbody>
            </table>


        </form>

    </div> <!-- .content-wrapper -->
</main> <!-- .cd-main-content -->
<script src="js/main.js"></script> <!-- Resource jQuery -->
</body>
</html>