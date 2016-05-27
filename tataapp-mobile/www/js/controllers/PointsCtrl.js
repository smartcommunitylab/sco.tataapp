angular.module('tataapp.controllers.points', [])

.controller('PointsCtrl', function ($scope, $filter, BackendSrv, Config, $state, $ionicPlatform) {
    $scope.now = moment().startOf('month');
    $scope.tataPoints = [];
    $scope.dateFormat = Config.dateFormat; // Config.dateFormatNum
    $scope.dateFormatMonth = Config.dateFormatMonth;

    var getAllTatapoint = function () {
        BackendSrv.getAllTatapoint().then(
            function (data) {
                for (var i = 0; i < data.content.length; i++) {
                    if (moment(data.content[i].startDate).startOf('month').isSameOrBefore($scope.now) &&
                        moment(data.content[i].endDate).startOf('month').isSameOrAfter($scope.now)) {
                        $scope.tataPoints.push(angular.copy(data.content[i]));
                    }
                }
            },
            function (reason) {
                console.log(error);
            }
        );
    };

    getAllTatapoint();

    $scope.changeMonth = function (num) {
        $scope.tataPoints = [];
        $scope.now.add(num, 'M');
        getAllTatapoint();
    };

    $scope.seePoint = function (index) {
        $state.go('app.point', {
            'selectedPoint': $scope.tataPoints[index]
        });
    };
})

.controller('PointCtrl', function ($scope, $filter, BackendSrv, Config, $stateParams) {
    $scope.dateFormat = Config.dateFormat;
    $scope.timeFormat = Config.timeFormat;
    $scope.selectedPoint = $stateParams['selectedPoint'];
    //console.log($scope.selectedPoint);
});
