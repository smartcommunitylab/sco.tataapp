angular.module('tataapp.controllers.nanny', [])

.controller('NannyCtrl', function ($scope, $state, $stateParams) {
    $scope.nanny = $stateParams['nanny'];

    $scope.getAge = function (nanny) {
        return moment().diff(nanny.birthdate, 'years');
    }

    $scope.meetNanny = function (nanny) {
        $state.go('app.meetnanny', {
            'nannyId': nanny.id,
            'nanny': nanny
        });
        /*, {
            reload: true
        }*/
    };
});
