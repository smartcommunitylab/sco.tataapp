angular.module('tataapp.controllers.home', [])

.controller('AppCtrl', function ($scope, $state, $ionicHistory) {
    // alternative to <a href>
    $scope.goTo = function (state, refresh, root) {
        if (root) {
            $ionicHistory.nextViewOptions({
                historyRoot: true
            });
        }

        $state.go(state, {}, {
            refresh: !!refresh
        });
    };
})

.controller('HomeCtrl', function ($scope) {
    $scope.swiperOptions = {
        autoplay: 2000,
        speed: 1000,
        loop: true
    };
})

.controller('PointsCtrl', function ($scope) {
    // TODO
})

.controller('InfoCtrl', function ($scope, $filter) {
    $scope.swiperOptions = {};

    $scope.infoPages = [];

    var infoCounter = 1;
    while ($filter('translate')('info_' + infoCounter + '_t') != ('info_' + infoCounter + '_t')) {
        var entry = {
            title: 'info_' + infoCounter + '_t',
            content: 'info_' + infoCounter + '_c'
        };
        $scope.infoPages.push(entry);
        infoCounter++;
    }
});
