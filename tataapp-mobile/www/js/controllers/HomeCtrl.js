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

.controller('HomeCtrl', function ($scope) {});
