//Define an angular module for our app
var sampleApp = angular.module('sampleApp', ['ui.bootstrap', 'ngRoute']);

//Define Routing for app
//Uri /AddNewOrder -> template AddOrder.html and Controller AddOrderController
//Uri /ShowOrders -> template ShowOrders.html and Controller AddOrderController
sampleApp.config(['$routeProvider',
    function ($routeProvider) {
        $routeProvider.when('/GenerateReport', {
            templateUrl: '../GenerateReport.html',
            controller: 'GenerateReportController'
        }).when('/GenerateDiscrepancy', {
            templateUrl: '../GenerateDiscrepancy.jsp',
            controller: 'GenerateDiscrepancyController'
        }).when('/GenerateWeekendHoliday', {
            templateUrl: '../GenerateWeekendHoliday.html',
            controller: 'GenerateWeekendHolidayController'
        }).when('/GeneratePublicHoliday', {
            templateUrl: '../GeneratePublicHoliday.html',
            controller: 'GeneratePublicHolidayController'
        }).when('/uploadBiometric', {
            templateUrl: './UploadFiles/uploadBiometric.jsp',
            controller: 'uploadBiometricController'
        }).when('/UploadFiles', {
            templateUrl: './UploadFiles.jsp',
            controller: 'UploadFilesController'
        }).when('/Overview', {
            templateUrl: './Overview.html',
            controller: 'OverviewController'
        }).otherwise({
            redirectTo: './MainPage.jsp'
        });

    }]);

sampleApp.controller('mainCtrl', ['$scope', function( $scope ) {

    // Set the default value of inputType
    $scope.inputType = 'password';

    // Hide & show password function
    $scope.hideShowPassword = function () {
        if ($scope.inputType == 'password')
            $scope.inputType = 'text';
        else
            $scope.inputType = 'password';
    };
}]);
sampleApp.controller('uploadFiles', ['$scope', function ($scope) {
    $("#check").click(function () {
        var fileName = $("#file1").val();
        if (fileName.lastIndexOf("png") === fileName.length - 3)
            alert("OK");
        else
            alert("Not PNG");
    })
}]);
sampleApp.controller("ReportController", function ($scope, $http) {
    $http.get("./JsonFiles/AllWorkers.json").then(function (response) {
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
    $http.get("./JsonFiles/DiscrepancyInWorkers.json").then(function (response) {
        $scope.rowCollection = response.data;
    });

    $scope.modelname = '<table style="border: 1px"> <thead><th>Full Day Leave</th> <th> Half Day Leave </th></thead> <tbody></tbody></table>';

    $scope.countChecked = function () {
        var count = 0;
        angular.forEach($scope.items.allDateDetailsList, function (day) {
            if (day.Selected) count++;
        });

        return count;
    };
    $scope.showSelected = function (item) {

        $scope.itemsSelected = [];
        angular.forEach($scope.rowCollection, function (item) {
            if (item.Selected) {
                $scope.itemsSelected.push(item.empRevalId);
            }
        });
    };


    $scope.countChecked = function () {
        var count = 0;
        angular.forEach($scope.items.allDateDetailsList, function (day) {
            if (day.Selected) count++;
        });

        return count;
    };
    $scope.discrepancyFound = function () {

        var temp = $scope.leaveNotApplied;
        var temp2 = $scope.halfDay;
        $log.info('New Flag ' + new Date() + temp + " Hi");
        if (temp == "" && temp2 == "")
            return false;
        else
            return true;
    };
    $scope.leaveNotAppliedFunc = function () {

        var temp = $scope.leaveNotApplied;
        $log.info('New Flag ' + new Date() + temp + " Hi");
        if (temp == "")
            return false;
        else
            return true;
    };
    $scope.halfDayFunc = function () {

        var temp = $scope.halfDay;
        $log.info('New Flag ' + new Date() + temp + " Hi");
        if (temp == "")
            return false;
        else
            return true;
    };

    $scope.checkOutTimeNotApplicable = function () {

        var temp = $scope.checkOutTime;
        $log.info('New Flag ' + new Date() + temp + " Hi");
        if (temp == "")
            return false;
        else
            return true;
    };
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

    $scope.checkStatus = function () {
        var checkCount = 0;
        var length = 0;
        angular.forEach($scope.rowCollection, function (item) {
            length++;
        });
        angular.forEach($scope.rowCollection, function (item) {
            if (item.Selected) checkCount++;

        });
        $log.info('New Flag ' + new Date() + checkCount + length);
        $scope.selectedAll = ( checkCount === length);
    };

    $scope.discrepancyCheckAll = function () {
        if ($scope.discrepancySelectedAll) {
            $scope.discrepancySelectedAll = true;
        } else {
            $scope.discrepancySelectedAll = false;
        }
        angular.forEach($scope.items.allDateDetailsList, function (day) {
            day.Selected = $scope.discrepancySelectedAll;
        });

    };

    $scope.discrepancyCheckStatus = function () {
        var checkCount = 0;
        var length = 0;
        angular.forEach($scope.items.allDateDetailsList, function (day) {
            length++;
        });
        angular.forEach($scope.items.allDateDetailsList, function (day) {
            if (day.Selected) checkCount++;

        });
        $log.info('New Flag ' + new Date() + checkCount + length);
        $scope.discrepancySelectedAll = ( checkCount === length);
    };
    $scope.updateValue = function (newTest) {

        angular.forEach($scope.items.allDateDetailsList, function (day) {

        });
        angular.forEach($scope.items.allDateDetailsList, function (day) {
            if (day.Selected) checkCount++;

        });
        $log.info('New Flag ' + new Date() + checkCount + length);
        $scope.discrepancySelectedAll = ( checkCount === length);
    };

    $scope.open = function (items, size) {
        $log.info('Items24 ' + new Date() + items.empRevalId);
        var modalInstance = $uibModal.open({
            templateUrl: 'RowDetail.html',
            controller: 'ModalInstanceCtrl',
            size: size,
            resolve: {
                items: function () {
                    return items;
                }
            }
        });
        $log.info('Items25 ' + new Date() + items.empName);

        modalInstance.result.then(function (items) {
            $scope.items = items;
        }, function () {
            $log.info('Modal dismissed at: ' + new Date());
        });
    };


    $scope.openEmailDialog = function (items, day, size) {

        $log.info('Items24 ' + new Date());
        var modalInstance = $uibModal.open({
            templateUrl: 'ComposeEmail.jsp',
            controller: 'DraftEmailController',
            size: size,
            resolve: {
                items: function () {
                    return items;
                },
                day: function () {
                    return day;
                }
            }
        });
        $log.info('Items25 ' + new Date());

        modalInstance.result.then(function (items, day) {
            $scope.items = items;
            $scope.day = day;
        }, function () {
            $log.info('Modal dismissed at: ' + new Date());
        });
    };
    $scope.showMe = false;
    $scope.clickFunc = function () {
        $scope.showMe = !$scope.showMe;
    };
});
angular.module('sampleApp').controller('DraftEmailController', function ($scope, $uibModalInstance, $log, items, day) {

    $scope.items = items;
    $scope.day = day;
    /*  $scope.selected = {
     item: $scope.items[0]
     };*/
    $scope.send = function () {

        $uibModalInstance.close($scope.day);
    };

    $log.info('Items ' + new Date() + $scope.items);

    $scope.cancel = function () {
        $uibModalInstance.dismiss('cancel');
    };

});
angular.module('sampleApp').controller('ModalInstanceCtrl', function ($scope, $uibModalInstance, $log, items) {

    $scope.items = items;
    /*  $scope.selected = {
     item: $scope.items[0]
     };*/

    $scope.update = function () {

        $uibModalInstance.close($scope.items);
    };

    $log.info('Items ' + new Date() + $scope.items);

    $scope.cancel = function () {
        $uibModalInstance.dismiss('cancel');
    };
});

sampleApp.controller("GenerateWeekendHolidayController", function ($scope, $http, $uibModal, $log) {
    $http.get("./JsonFiles/WeekendWorkers.json").then(function (response) {
        $scope.rowCollection = response.data;
    });

    $scope.showSelected = function (item) {

        $scope.itemsSelected = [];
        angular.forEach($scope.rowCollection, function (item) {
            if (item.Selected) {
                $scope.itemsSelected.push(item.empRevalId);
            }
        });
    };

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

    $scope.checkStatus = function () {
        var checkCount = 0;
        var length = 0;
        angular.forEach($scope.rowCollection, function (item) {
            length++;
        });
        angular.forEach($scope.rowCollection, function (item) {
            if (item.Selected) checkCount++;

        });
        $log.info('New Flag ' + new Date() + checkCount + length);
        $scope.selectedAll = ( checkCount === length);
    };

    $scope.open = function (items, size) {
        $log.info('Items24 ' + new Date() + items.empRevalId);
        var modalInstance = $uibModal.open({
            templateUrl: 'RowDetail2.html',
            controller: 'ModalInstanceCtrl',
            size: size,
            resolve: {
                items: function () {
                    return items;
                }
            }

        });
        $log.info('Items25 ' + new Date() + items.empName);

        modalInstance.result.then(function (items) {
            $scope.items = items;
        }, function () {
            $log.info('Modal dismissed at: ' + new Date());
        });
    };

    $scope.showMe = false;
    $scope.clickFunc = function () {
        $scope.showMe = !$scope.showMe;
    };

});

sampleApp.controller("GenerateReportController", function ($scope, $http, $uibModal, $log) {
    $http.get("./JsonFiles/AllWorkers.json").then(function (response) {
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

    $scope.showSelected = function (item) {

        $scope.itemsSelected = [];
        angular.forEach($scope.rowCollection, function (item) {
            if (item.Selected) {
                $scope.itemsSelected.push(item.empRevalId);
            }
        });
    };

    $scope.checkStatus = function () {
        var checkCount = 0;
        var length = 0;
        angular.forEach($scope.rowCollection, function (item) {
            length++;
        });
        angular.forEach($scope.rowCollection, function (item) {
            if (item.Selected) checkCount++;

        });
        $log.info('New Flag ' + new Date() + checkCount + length);
        $scope.selectedAll = ( checkCount === length);
    };

    $scope.open = function (items, size) {
        $log.info('Items24 ' + new Date() + items.empRevalId);
        var modalInstance = $uibModal.open({
            templateUrl: 'RowDetail2.html',
            controller: 'ModalInstanceCtrl',
            size: size,
            resolve: {
                items: function () {
                    return items;
                }
            }

        });
        $log.info('Items25 ' + new Date() + items.empName);

        modalInstance.result.then(function (items) {
            $scope.items = items;
        }, function () {
            $log.info('Modal dismissed at: ' + new Date());
        });
    };

    $scope.showMe = false;
    $scope.clickFunc = function () {
        $scope.showMe = !$scope.showMe;
    };

});

sampleApp.controller("GeneratePublicHolidayController", function ($scope, $http, $uibModal, $log) {
    $http.get("./JsonFiles/PublicHolidayWorkers.json").then(function (response) {
        $scope.rowCollection = response.data;
    });

    $scope.showSelected = function (item) {

        $scope.itemsSelected = [];
        angular.forEach($scope.rowCollection, function (item) {
            if (item.Selected) {
                $scope.itemsSelected.push(item.empRevalId);
            }
        });
    };

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

    $scope.checkStatus = function () {
        var checkCount = 0;
        var length = 0;
        angular.forEach($scope.rowCollection, function (item) {
            length++;
        });
        angular.forEach($scope.rowCollection, function (item) {
            if (item.Selected) checkCount++;

        });
        $log.info('New Flag ' + new Date() + checkCount + length);
        $scope.selectedAll = ( checkCount === length);
    };

    $scope.open = function (items, size) {
        $log.info('Items24 ' + new Date() + items.empRevalId);
        var modalInstance = $uibModal.open({
            templateUrl: 'RowDetail2.html',
            controller: 'ModalInstanceCtrl',
            size: size,
            resolve: {
                items: function () {
                    return items;
                }
            }

        });
        $log.info('Items25 ' + new Date() + items.empName);

        modalInstance.result.then(function (items) {
            $scope.items = items;
        }, function () {
            $log.info('Modal dismissed at: ' + new Date());
        });
    };

    $scope.showMe = false;
    $scope.clickFunc = function () {
        $scope.showMe = !$scope.showMe;
    };

});
sampleApp.controller("UploadFilesController", function ($scope, $http, $log) {
    var buttonA = null;
    $scope.button_A = localStorage.getItem("buttonA");

    $scope.A = function () {
        console.log('you click A on ', Date());
        $log.info(buttonA);
        //var button_A = Date();
        window.localStorage.setItem("buttonA", Date());
    };
    var buttonB = null;
    $scope.button_B = localStorage.getItem("buttonB");
    $scope.B = function () {
        console.log('you click B on ', Date());
        $log.info(buttonB + Date());
        //var button_A = Date();
        window.localStorage.setItem("buttonB", Date());
    };
    var buttonC = null;
    $scope.button_C = localStorage.getItem("buttonC");
    $scope.C = function () {
        console.log('you click C on ', Date());
        $log.info(buttonC + Date());
        //var button_A = Date();
        window.localStorage.setItem("buttonC", Date());
    };


    $scope.checkJson = function () {
        if (buttonA === null || buttonB === null || buttonC === null) {
            $log.info(biometFile + "hello");
            return 0;
        }
        else {
            $log.info(biometFile + "hi");
            return 1;
        }
    };

});
sampleApp.controller("OverviewController", function ($scope, $http) {

});