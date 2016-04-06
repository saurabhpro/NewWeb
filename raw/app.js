var app = angular.module('App', []);

app.controller('listCtrl', function($scope) {

    $scope.users = [
        {id:1, name:'Wayan', email:'wayan@gmail.com'},
        {id:2, name:'Made', email:'made@gmail.com'},
        {id:3, name:'Komang', email:'komang@gmail.com'},
        {id:4, name:'Ketut', email:'ketut@gmail.com'},
    ];

    $scope.userChecked = [];
    $scope.checkedAll = false;

    $scope.checkItem = function(id, checked) {
        if (checked) {
            $scope.userChecked.push(id);
        } else {
            $scope.userChecked.pop();
            if ($scope.userChecked.length < 1) {
                $scope.checkedAll = true;
            }
        }

        console.log('$scope.userChecked', $scope.userChecked);
    };

    $scope.checkAll = function(checked) {
        $scope.userChecked = [];

        angular.forEach($scope.users, function(value, key) {
            value.selected = checked;
            $scope.userChecked.push(value.id);
        });

        if (!checked) {
            $scope.userChecked = [];
        }

        console.log('$scope.userChecked', $scope.userChecked);
    };

}); 