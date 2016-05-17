angular.module('tataapp.controllers.home', [])

.controller('AppCtrl', function ($scope, $state, $ionicHistory) {
    $scope.openLink = function (link) {
        window.open(link, '_system');
    };

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

.controller('HomeCtrl', function ($scope, $filter, Config, BackendSrv) {
    $scope.swiperOptions = {
        autoplay: 2000,
        speed: 1000,
        loop: true
    };

    $scope.mailreceiver = Config.MAIL_RECEIVER;

    $scope.entries = [];
    var counter = 1;
    while ($filter('translate')('info_' + counter + '_t') != ('info_' + counter + '_t')) {
        $scope.entries.push('info_' + counter + '_t');
        counter++;
    }
})

.controller('PointsCtrl', function ($scope) {
    // TODO
})

.controller('InfoCtrl', function ($scope, $filter, BackendSrv) {
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

    $scope.zones = [];
    BackendSrv.getZones().then(function (response) {
        $scope.zones = response.content;
    });
});
