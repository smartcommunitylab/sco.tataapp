angular.module('tataapp.controllers.points', [])

.controller('PointsCtrl', function ($scope, $filter, BackendSrv, Config, $state, $ionicPlatform) {
    $scope.now = new Date();
    $scope.tataPoints = [];
    $scope.dateFormat = Config.dateFormatNum;
    $scope.dateFormatMonth = Config.dateFormatMonth;
    var getAllTatapoint = function () {
        BackendSrv.getAllTatapoint().then(
            function (data) {
                for (i = 0; i < data.content.length; i++) {
                    if (data.content[i].startDate < $scope.now && data.content[i].endDate > $scope.now) {
                        $scope.tataPoints.push(angular.copy(data.content[i]));
                    }
                }
            },
            function (reason) {
                console.log(error);
            }
        );
    };

    var init = function () {
        getAllTatapoint();
    };

    init();

    $scope.changeMonth = function (num) {
        $scope.tataPoints = [];
        $scope.now.setMonth($scope.now.getMonth() + num);
        getAllTatapoint();
    };

    $scope.seePoint = function (index) {
        $state.go('app.point', {
            'selectedPoint': $scope.tataPoints[index]
        });
    };
})

.controller('PointCtrl', function ($scope, $filter, BackendSrv, Config, $stateParams) {
    $scope.timeFormat = Config.timeFormat;
    $scope.selectedPoint = $stateParams['selectedPoint'];
    console.log($scope.selectedPoint);
});
