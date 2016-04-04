//Define an angular module for our app
var sampleApp = angular.module('sampleApp', ['ui.bootstrap']);

//Define Routing for app
//Uri /AddNewOrder -> template AddOrder.html and Controller AddOrderController
//Uri /ShowOrders -> template ShowOrders.html and Controller AddOrderController
sampleApp.config(['$routeProvider',
    function ($routeProvider) {
        $routeProvider.when('/GenerateReport', {
            templateUrl: '../GenerateReport.html',
            controller: 'GenerateReportController'
        }).when('/GenerateDiscrepancy', {
            templateUrl: '../GenerateDiscrepancy.html',
            controller: 'GenerateDiscrepancyController'
        }).when('/uploadBiometric', {
            templateUrl: './UploadFiles/uploadBiometric.jsp',
            controller: 'uploadBiometricController'
        }).when('/uploadSalesforce', {
            templateUrl: './UploadFiles/uploadSalesforce.jsp',
            controller: 'uploadHrnetController'
        }).when('/uploadEmailList', {
            templateUrl: './UploadFiles/uploadEmailList.jsp',
            controller: 'uploadEmailListController'
        }).when('/uploadHoliday', {
            templateUrl: './UploadFiles/uploadHoliday.jsp',
            controller: 'uploadHolidayController'
        }).when('/Logout',{
            //templateUrl: './UploadFiles/uploadHoliday.jsp',
            controller: 'logoutController'
        }).otherwise({
            redirectTo: '/mainPage.html'
        });

    }]);


sampleApp.controller("ReportController", function ($scope, $http) {
    $http.get("./json/WebDetails.json").then(function (response) {
        $scope.rowCollection = response.data;
    });

    $scope.checkAll = function () {
        if ($scope.selectedAll) {
            $scope.selectedAll = true;
        } else {
            $scope.selectedAll = false;
        }
        angular.forEach($scope.rowCollection, function (item) {
            item.Selected = $scope.selectedAll;
        });

    };

    $scope.showMe = false;
    $scope.clickFunc = function () {
        $scope.showMe = !$scope.showMe;
    }
});

sampleApp.controller("GenerateDiscrepancyController", function ($scope, $http, $uibModal, $log) {
    $http.get("./json/Discrepancy.json").then(function (response) {
        $scope.rowCollection = response.data;
    });

    $scope.checkAll = function () {
        if ($scope.selectedAll) {
            $scope.selectedAll = true;
        } else {
            $scope.selectedAll = false;
        }
        angular.forEach($scope.rowCollection, function (item) {
            item.Selected = $scope.selectedAll;
        });
    };

    $scope.showMe = false;
    $scope.clickFunc = function () {
        $scope.showMe = !$scope.showMe;
    };

    $scope.items = ['item1', 'item2', 'item3'];

    $scope.open = function () {

        var modalInstance = $uibModal.open({
            templateUrl: 'RowDetail.html',
            controller: 'ModalInstanceCtrl',
            resolve: {
                items: function () {
                    return $scope.items;
                }
            }
        });

        modalInstance.result.then(function (selectedItem) {
            $scope.selected = selectedItem;
        }, function () {
            $log.info('Modal dismissed at: ' + new Date());
        });
    };
});

angular.module('sampleApp').controller('ModalInstanceCtrl',function ($scope, $uibModalInstance, items) {

    $scope.items = items;
    $scope.selected = {
        item: $scope.items[0]
    };

    $scope.ok = function () {
        $uibModalInstance.close($scope.selected.item);
    };

    $log.info('Items ' + new Date()+ $scope.items[0]);

    $scope.cancel = function () {
        $uibModalInstance.dismiss('cancel');
    };
});

sampleApp.controller("GenerateReportController", function ($scope, $http, $uibModal, $log) {
    $http.get("./json/WebDetails.json").then(function (response) {
        $scope.rowCollection = response.data;
    });

    $scope.checkAll = function () {
        if ($scope.selectedAll) {
            $scope.selectedAll = true;
        } else {
            $scope.selectedAll = false;
        }
        angular.forEach($scope.rowCollection, function (item) {
            item.Selected = $scope.selectedAll;
        });

    };

    $scope.showMe = false;
    $scope.clickFunc = function () {
        $scope.showMe = !$scope.showMe;
    }
    $scope.items = ['item1', 'item2', 'item3'];

    $scope.open = function () {

        var modalInstance = $uibModal.open({
            templateUrl: 'RowDetail.html',
            controller: 'ModalInstanceCtrl',
            resolve: {
                items: function () {
                    return $scope.items;
                }
            }
        });

        modalInstance.result.then(function (selectedItem) {
            $scope.selected = selectedItem;
        }, function () {
            $log.info('Modal dismissed at: ' + new Date());
        });
    };

});

sampleApp.controller("uploadBiometricController", function ($scope, $http) {
    /*

     $scope.getDataFromServer = function() {

     $http({
     method : 'POST',
     url : '../../BiometricServlet'
     }).success(function(data, status, headers, config) {
     $scope.message = "successful";
     }).error(function(data, status, headers, config) {
     $scope.message = "error";
     alert(data+headers+config);
     document.write(data);
     });

     };
     */
});
sampleApp.controller("uploadHrnetController", function ($scope, $http) {
});
sampleApp.controller("uploadEmailListController", function ($scope, $http) {
});
sampleApp.controller("uploadHolidayController", function ($scope, $http) {
});

sampleApp.controller("logoutController", function($scope, $http){});

