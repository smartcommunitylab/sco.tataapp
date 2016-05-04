angular.module('tataapp', [
    'ionic',
    'ionic.wizard',
    'ionic-datepicker',
    'ngCordova',
    'ngSanitize',
    'pascalprecht.translate',
    'tataapp.services.config',
    'tataapp.services.utils',
    'tataapp.services.backend',
    'tataapp.controllers.home',
    'tataapp.controllers.points',
    'tataapp.controllers.search',
    'tataapp.controllers.meet',
    'tataapp.controllers.fee',
    'tataapp.controllers.faq'
])

.run(function ($ionicPlatform) {
    $ionicPlatform.ready(function () {
        // Hide the accessory bar by default (remove this to show the accessory bar above the keyboard
        // for form inputs)
        if (window.cordova && window.cordova.plugins.Keyboard) {
            cordova.plugins.Keyboard.hideKeyboardAccessoryBar(true);
            cordova.plugins.Keyboard.disableScroll(true);

        }
        if (window.StatusBar) {
            // org.apache.cordova.statusbar required
            StatusBar.styleDefault();
        }
    });
})

.config(function ($translateProvider) {
    /*$translateProvider.translations('it', {});*/
    /*$translateProvider.preferredLanguage('it');*/
    $translateProvider.preferredLanguage('it');
    $translateProvider.useStaticFilesLoader({
        prefix: 'languages/',
        suffix: '.json'
    });
    $translateProvider.useSanitizeValueStrategy('sanitize');
})

.config(function ($stateProvider, $urlRouterProvider) {
    $stateProvider.state('app', {
        url: '/app',
        abstract: true,
        templateUrl: 'templates/menu.html',
        controller: 'AppCtrl'
    })

    .state('app.home', {
        url: '/home',
        views: {
            'menuContent': {
                templateUrl: 'templates/home.html',
                controller: 'HomeCtrl'
            }
        }
    })

    .state('app.search', {
        url: '/search',
        cache: false,
        views: {
            'menuContent': {
                templateUrl: 'templates/search.html',
                controller: 'SearchCtrl'
            }
        }
    })

    .state('app.searchsummary', {
        url: '/search/summary',
        views: {
            'menuContent': {
                templateUrl: 'templates/searchsummary.html',
                controller: 'SearchSummaryCtrl'
            }
        }
    })

    .state('app.searchresults', {
        url: '/search/results',
        views: {
            'menuContent': {
                templateUrl: 'templates/searchresults.html',
                controller: 'SearchResultsCtrl'
            }
        }
    })

    .state('app.meet', {
        url: '/meet',
        views: {
            'menuContent': {
                templateUrl: 'templates/meet.html',
                controller: 'MeetCtrl'
            }
        }
    })

    .state('app.points', {
        url: '/points',
        views: {
            'menuContent': {
                templateUrl: 'templates/points.html',
                controller: 'PointsCtrl'
            }
        }
    })

    .state('app.point', {
        url: '/points/point',
        params: {
            'selectedPoint': {}
        },
        views: {
            'menuContent': {
                templateUrl: 'templates/point.html',
                controller: 'PointCtrl'
            }
        }
    })

    .state('app.fee', {
        url: '/fee',
        views: {
            'menuContent': {
                templateUrl: 'templates/fee.html'
            }
        }
    })

    .state('app.fee_estimate', {
        url: '/fee/estimate',
        views: {
            'menuContent': {
                templateUrl: 'templates/fee_estimate.html',
                controller: 'FeeCtrl'
            }
        }
    })

    .state('app.info', {
        url: '/info',
        views: {
            'menuContent': {
                templateUrl: 'templates/info.html',
                controller: 'InfoCtrl'
            }
        }
    })

    .state('app.vouchers', {
        url: '/vouchers',
        views: {
            'menuContent': {
                templateUrl: 'templates/vouchers.html'
            }
        }
    })

    .state('app.faq', {
        url: '/faq',
        views: {
            'menuContent': {
                templateUrl: 'templates/faq.html',
                controller: 'FaqCtrl'
            }
        }
    });

    // if none of the above states are matched, use this as the fallback
    $urlRouterProvider.otherwise('/app/home');
});
