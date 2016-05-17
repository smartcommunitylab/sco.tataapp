'use strict';

angular.module('app.home', ['ngRoute'])

.config(['$routeProvider', function($routeProvider) {
  $routeProvider.when('/home', {
    templateUrl: 'home/view.html',
    controller: 'HomeCtrl'
  });
}])

.controller('HomeCtrl', ['$scope',function($scope) {
	$scope.message = "HOME VIEW";
	
}]);