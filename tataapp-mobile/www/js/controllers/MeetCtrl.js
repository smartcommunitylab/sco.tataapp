angular.module('tataapp.controllers.meet', [])

.controller('MeetCtrl', function ($scope, $filter, $ionicPopup) {
    $scope.popupPersonalData = function () {
        var popup = $ionicPopup.confirm({
            title: $filter('translate')('popup_personaldata_title'), // String. The title of the popup.
            cssClass: '',
            template: $filter('translate')('popup_personaldata_content'),
            cancelText: $filter('translate')('deny'),
            cancelType: 'button-stable', // (default: 'button-default')
            okText: $filter('translate')('allow'),
            okType: 'button-positive' // (default: 'button-positive')
        });

        popup.then(function (ok) {
            if (ok) {
                console.log('Auth allowed');
            } else {
                console.log('Auth denied');
            }
        });
    };

    $scope.send = function () {
        $scope.popupPersonalData();
    };
});
