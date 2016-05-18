'use strict';

// Declare app level module which depends on views, and components
angular.module('app', ['i18nmessages','app.ctrls','app.home','app.tatapoint','app.tata','app.rate',
  'ngRoute','ui.bootstrap'
]).
config(['$routeProvider', function($routeProvider) {
  $routeProvider.otherwise({redirectTo: '/home'});
}]);
