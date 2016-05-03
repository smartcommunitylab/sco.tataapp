angular.module('tataapp.controllers.points', [])

.controller('PointsCtrl', function ($scope, $filter, BackendSrv, Config) {
    var now = new Date();
    $scope.dateFormat = Config.dateFormatNum;
    $scope.dateFormatMonth = Config.dateFormatMonth
    $scope.tataPoints = [];
    $scope.month = now;
    var getAllTatapoint = function () {
        BackendSrv.getAllTatapoint().then(
            function (data) {
                $scope.tataPoints = data.content;
            },
            function (error) {
                console.log(error);
            }
        );
    };
    getAllTatapoint();
});
