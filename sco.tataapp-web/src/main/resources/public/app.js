'use strict';

// Declare app level module which depends on views, and components
angular.module('app', ['app.ctrls','app.home','app.tatapoint','app.tata',
  'ngRoute','ui.bootstrap'
]).
config(['$routeProvider', function($routeProvider) {
  $routeProvider.otherwise({redirectTo: '/home'});
}]);
