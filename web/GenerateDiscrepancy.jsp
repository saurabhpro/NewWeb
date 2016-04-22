<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html ng-app="sampleApp" ng-controller="GenerateDiscrepancyController" lang="en" class="no-js">
<head>
    <meta charset="UTF-8">

    <meta name="viewport" content="width=device-width, initial-scale=1">

    <link rel="stylesheet" href="bower_components/bootstrap/dist/css/bootstrap.min.css">
    <!-- since there could be dependencies problem, maintain the inculsion order -->
    <script src="bower_components/angular/angular.js"></script>
    <script src="bower_components/angular-route/angular-route.js"></script>
    <script src="bower_components/angular-animate/angular-animate.js"></script>
    <script src="bower_components/angular-bootstrap/ui-bootstrap-tpls.js"></script>

    <script src="js/app.js"></script>
    <style type="text/css" media="screen">
        .click:hover {
            text-decoration: underline;
        }
    </style>

    <title>Discrepancy Employee List</title>
</head>
<body>

<main class="cd-main-content">
    <div class="content-wrapper">
        <br>
        <h2 style="text-align: center">
            Monthly Discrepancy List
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