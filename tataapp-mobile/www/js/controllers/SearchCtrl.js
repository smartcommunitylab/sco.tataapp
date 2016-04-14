angular.module('tataapp.controllers.search', [])

.controller('SearchCtrl', function ($scope, $filter, $ionicPopup, $state) {
    $scope.popupReset = function () {
        var popup = $ionicPopup.confirm({
            title: $filter('translate')('popup_search_when_reset_title'), // String. The title of the popup.
            cssClass: '',
            template: $filter('translate')('popup_search_when_reset_content'),
            cancelText: $filter('translate')('cancel'),
            cancelType: '', // (default: 'button-default')
            okText: $filter('translate')('yes'),
            okType: 'button-stable' // (default: 'button-positive')
        });

        popup.then(function (ok) {
            if (ok) {
                console.log('Reset done');
            } else {
                console.log('Reset canceled');
            }
        });
    };

    $scope.search = function () {
        $state.go('app.searchsummary', {}, {
            reload: true
        });
    };
})

.controller('SearchSummaryCtrl', function ($scope, $state) {
    $scope.seeSearchResultsList = function () {
        $state.go('app.searchresults', {}, {
            reload: true
        });
    };

    $scope.dummy = function (oh) {
        console.log(oh);
    };
})

.controller('SearchResultsCtrl', function ($scope) {
    $scope.dummy = function (oh) {
        console.log(oh);
    };
});
