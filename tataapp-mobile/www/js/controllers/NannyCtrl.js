angular.module('tataapp.controllers.nanny', [])

.controller('NannyCtrl', function ($scope, $stateParams) {
    $scope.nanny = $stateParams['nanny'];
});
