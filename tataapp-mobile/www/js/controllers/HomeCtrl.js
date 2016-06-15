angular.module('tataapp.controllers.home', [])

.controller('AppCtrl', function ($scope, $state, $ionicHistory) {
    $scope.openLink = function (link) {
        window.open(link, '_system');
    };

    // alternative to <a href>
    $scope.goTo = function (state, params, reload, root) {
        if (!!root) {
            $ionicHistory.nextViewOptions({
                historyRoot: true
            });
        }

        if (!!reload) {
            $ionicHistory.clearCache().then(function () {
                $state.go(state, params);
            });
        } else {
            $state.go(state, params);
        }
    };
})

.controller('HomeCtrl', function ($scope, $state, $filter, Config, BackendSrv) {
    $scope.swiperOptions = {
        autoplay: 2000,
        speed: 1000,
        loop: true
    };

    $scope.mailreceiver = Config.MAIL_RECEIVER;

    /*$scope.entries = [];
    var counter = 1;
    while ($filter('translate')('info_' + counter + '_t') != ('info_' + counter + '_t')) {
        $scope.entries.push('info_' + counter + '_t');
        counter++;
    }*/

    $scope.homePages = [];
    var homeCounter = 1;
    while ($filter('translate')('home_' + homeCounter + '_t') != ('home_' + homeCounter + '_t')) {
        var entry = {
            title: 'home_' + homeCounter + '_t',
            content: 'home_' + homeCounter + '_c',
            state: $filter('translate')('home_' + homeCounter + '_p'),
            image: 'img/homepage_0' + homeCounter + '.png'
        };
        $scope.homePages.push(entry);
        homeCounter++;
    }
})

.controller('PointsCtrl', function ($scope) {
    // TODO
})

.controller('InfoCtrl', function ($scope, $stateParams, $filter, $ionicSlideBoxDelegate, BackendSrv) {
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

    if (!!$stateParams && !!$stateParams['page']) {
        $ionicSlideBoxDelegate.slide($stateParams['page']);
    }
});
