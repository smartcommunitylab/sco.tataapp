'use strict';

angular.module('app.tatapoint', [ 'ngRoute', 'ngResource' ])

.config([ '$routeProvider', function($routeProvider) {
	$routeProvider.when('/tatapoint', {
		templateUrl : 'tatapoint/tatapoint.html',
		controller : 'TatapointCtrl'
	});
} ])

.controller(
		'TatapointCtrl',
		[ '$rootScope', '$scope', '$sce',
				function($rootScope, $scope, $sce) {
					var tatapointCalId = '';
					$scope.tatapointCalendarURL = $sce.trustAsResourceUrl("https://calendar.google.com/calendar/embed?showTitle=0&amp;showPrint=0&amp;showCalendars=0&amp;showTz=0&amp;height=600&amp;wkst=2&amp;hl=it&amp;bgcolor=%23FFFFFF&src=" + tatapointCalId + "&amp;color=%230F4B38&amp;ctz=Europe%2FRome");

				} ])

.factory('Tatapoint', [ '$resource', function($resource) {
	return $resource('api/agency/:id/tatapoint/:tid', {
		id : "tataApp"
	}, {
		list : {
			isArray : true,
			method : 'get',
			transformResponse : function(data, headers) {
				return JSON.parse(data).content;
			}
		}
	}

	);
} ]);