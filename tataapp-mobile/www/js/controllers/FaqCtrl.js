angular.module('tataapp.controllers.faq', [])

.controller('FaqCtrl', function ($scope, $filter) {
    $scope.faq = [];

    var faqCounter = 1;
    while ($filter('translate')('faq_' + faqCounter + '_q') != ('faq_' + faqCounter + '_q')) {
        var t1 = $filter('translate')('faq_' + faqCounter + '_q');
        var t2 = ('faq_' + faqCounter + '_q');
        var entry = {
            question: 'faq_' + faqCounter + '_q',
            answer: 'faq_' + faqCounter + '_a'
        };
        $scope.faq.push(entry);
        faqCounter++;
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
