/**
 * Created by AroraA on 09-03-2016.
 */
app.controller('jsonCtrl', function ($scope, $http) {
    $http.get("Emails.json")
        .then(function (response) {
            $scope.rowCollection = response.data;
        });
});