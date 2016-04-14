angular.module('tataapp.controllers.faq', [])

.controller('FaqCtrl', function ($scope, $filter) {
    var FAQ_COUNT = 8;

    $scope.faq = [];
    for (var i = 1; i <= FAQ_COUNT; i++) {
        var entry = {
            question: 'faq_' + i + '_q',
            answer: 'faq_' + i + '_a'
        };

        $scope.faq.push(entry);
    }

    $scope.activeItem = $scope.faq[0];

    $scope.isActiveItem = function (item) {
        return $scope.activeItem === item;
    };

    $scope.toggleActiveItem = function (item) {
        if ($scope.isActiveItem(item)) {
            $scope.activeItem = null;
        } else {
            $scope.activeItem = item;
        }
    };
});
