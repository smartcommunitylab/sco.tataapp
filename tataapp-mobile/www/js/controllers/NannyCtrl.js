angular.module('tataapp.controllers.nanny', [])

.controller('NannyCtrl', function ($scope, $stateParams) {
    $scope.nanny = $stateParams['nanny'];

    $scope.getAge = function(nanny) {
        return moment().diff(nanny.birthdate, 'years');
    }
});
