angular.module('tataapp.controllers.points', [])

.controller('PointsCtrl', function ($scope, $filter, BackendSrv, Config, $state, $ionicPlatform) {
    $scope.now = moment().startOf('month');
    $scope.tataPoints = [];
    $scope.dateFormat = Config.dateFormat; // Config.dateFormatNum
    $scope.dateFormatMonth = Config.dateFormatMonth;

    var days2text = function (tatapoint) {
        if (!!tatapoint.recurrence && tatapoint.recurrence.frequency == 'WEEKLY' && tatapoint.recurrence.days.length > 0) {
            tatapoint.days = '';
            for (var i = 0; i < tatapoint.recurrence.days.length; i++) {
                tatapoint.days += $filter('translate')('date_weekslist_' + tatapoint.recurrence.days[i]);
                if ((i + 1) < tatapoint.recurrence.days.length) {
                    tatapoint.days += ', ';
                }
            };
        }
    };

    var getAllTatapoint = function () {
        BackendSrv.getAllTatapoint().then(
            function (data) {
                for (var i = 0; i < data.content.length; i++) {
                    var tatapoint = data.content[i];
                    if (moment(tatapoint.startDate).startOf('month').isSameOrBefore($scope.now) &&
                        moment(tatapoint.endDate).startOf('month').isSameOrAfter($scope.now)) {
                        days2text(tatapoint);
                        $scope.tataPoints.push(angular.copy(tatapoint));
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
