'use strict';

// Declare app level module which depends on views, and components
angular.module('app', ['app.ctrls','app.home','app.tatapoint',
  'ngRoute','ui.bootstrap'
]).
config(['$routeProvider', function($routeProvider) {
  $routeProvider.otherwise({redirectTo: '/home'});
}]);
