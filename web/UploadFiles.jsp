<%--
  Created by IntelliJ IDEA.
  User: AroraA
  Date: 08-04-2016
  Time: 09:47
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html ng-app="sampleApp" ng-controller="UploadFilesController" lang="en" class="no-js">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href='https://fonts.googleapis.com/css?family=Open+Sans:300,400,700' rel='stylesheet' type='text/css'>
    <link rel="stylesheet" href="bower_components/bootstrap/dist/css/bootstrap.min.css">
    <script src="./js/app.js"></script>
    <script>

    </script>
    <title>Upload Files</title>
</head>
<body>
<main class="cd-main-content">
    <div class="content-wrapper">
        <table>
            <thead>
            <th>Biometric Files</th>
            <th>Financial Force</th>
            <th>Email List</th>
            <th>Holiday List</th>
            </thead>
            <tbody>
            <tr>
                <td>
                    <form action="/upBiometric" method="post" enctype="multipart/form-data">
                        <input type="file" name="biometricFile" ng-model="bFile" required><br/>
                        <input ng-click="A()" class="btn btn-primary" type="submit" value="Upload Biometric Excel"/>
                    </form>
                    <br/>
                    <br/>
                    <br/>
                    <br/>
                    <h4>Details of Last Biometric File Uploaded </h4>
                    <b>Time:</b>
                    <br/>
                    {{button_A | date}}
                    <br/>

                    <%
                        if (session.getAttribute("biometricName") != null) {
                            out.println("<b> Biometric File Name: </b>");
                            out.print(session.getAttribute("biometricName"));
                        }
                    %>
                </td>

                <td>
                    <form action="upSalesforce" method="post" enctype="multipart/form-data">
                        <input type="file" name="salesforceFile" required><br/>
                        <input ng-click="B()" class="btn btn-primary" type="submit" value="Upload Salesforce Excel"/>
                    </form>
                    <br/>
                    <br/>
                    <br/>
                    <br/>
                    <h4>Details of Last Salesforce File Uploaded </h4>
                    <b>Time:</b>
                    <br/>
                    {{button_B | date}}
                    <br/>
                    <%
                        if (session.getAttribute("salesforceName") != null) {
                            out.println("<b> Salesforce File Name: </b>");
                            out.print(session.getAttribute("salesforceName"));
                        }
                    %>
                </td>
                <td>
                    <form action="upEmailList" method="post" enctype="multipart/form-data">
                        <input type="file" name="emailListFile" required><br/>
                        <input ng-click="C()" class="btn btn-primary" type="submit" value="Upload Email Excel"/>
                    </form>
                    <br/>
                    <br/>
                    <br/>
                    <br/>
                    <h4>Details of Last Email File Uploaded </h4>
                    <b>Time:</b>
                    <br/>
                    {{button_C | date:'yyyy-MM-dd'}}
                    <br/>
                    <%
                        if (session.getAttribute("emailName") != null) {
                            out.println("<b> Email List File Name: </b>");
                            out.print(session.getAttribute("emailName"));
                        }
                    %>
                </td>
                <td>
                    <form action="upHoliday" method="post" enctype="multipart/form-data">
                        <input type="file" name="holidayListFile"><br/>
                        <input ng-click="D()" disabled="true" class="btn btn-primary" type="submit"
                               value="Upload Holiday Excel"/>
                        {{button_D}}
                    </form>

                </td>

            </tr>
            </tbody>
        </table>

        <li style="float: right;" class="has-children">
            <form action="core" method="post">
                <input class="btn btn-success" type="submit" ng-disabled="checkJson() == 0" value="Generate Jsons">
            </form>
        </li>
    </div> <!-- .content-wrapper -->
</main> <!-- .cd-main-content -->
</body>
</html>
