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

.controller('HowCtrl', function ($scope, $filter) {
    $scope.swiperOptions = {};

    $scope.howPages = [];

    var howCounter = 1;
    while ($filter('translate')('how_' + howCounter + '_t') != ('how_' + howCounter + '_t')) {
        var t1 = $filter('translate')('how_' + howCounter + '_t');
        var t2 = ('how_' + howCounter + '_t');
        var entry = {
            title: 'how_' + howCounter + '_t',
            content: 'how_' + howCounter + '_c'
        };
        $scope.howPages.push(entry);
        howCounter++;
    }
});
