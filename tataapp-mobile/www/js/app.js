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
    'tataapp.controllers.nanny',
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

.config(function ($translateProvider, $ionicConfigProvider) {
    /*$translateProvider.translations('it', {});*/
    /*$translateProvider.preferredLanguage('it');*/
    $translateProvider.preferredLanguage('it');
    $translateProvider.useStaticFilesLoader({
        prefix: 'languages/',
        suffix: '.json'
    });
    //$translateProvider.useSanitizeValueStrategy('sanitize');
    //$translateProvider.useSanitizeValueStrategy('sanitizeParameters');
    $translateProvider.useSanitizeValueStrategy('escapeParameters');
    $ionicConfigProvider.backButton.previousTitleText(false).text('');
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
        params: {
            'searchResults': {},
        },
        views: {
            'menuContent': {
                templateUrl: 'templates/searchsummary.html',
                controller: 'SearchSummaryCtrl'
            }
        }
    })

    .state('app.searchresults', {
        url: '/search/results',
        params: {
            'searchResults': {},
        },
        views: {
            'menuContent': {
                templateUrl: 'templates/searchresults.html',
                controller: 'SearchResultsCtrl'
            }
        }
    })

    .state('app.nanny', {
        url: '/search/results/{nannyId}',
        params: {
            'nanny': {},
        },
        views: {
            'menuContent': {
                templateUrl: 'templates/nanny.html',
                controller: 'NannyCtrl'
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

    .state('app.meetnanny', {
        url: '/meet/{nannyId}',
        params: {
            'nanny': {}
        },
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
                templateUrl: 'templates/fee.html',
                controller: 'FeeCtrl'
            }
        }
    })

    .state('app.fee_estimate', {
        url: '/fee/estimate',
        views: {
            'menuContent': {
                templateUrl: 'templates/fee_estimate.html',
                controller: 'FeeEstimateCtrl'
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

    .state('app.who', {
        url: '/who',
        views: {
            'menuContent': {
                templateUrl: 'templates/who.html'
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
