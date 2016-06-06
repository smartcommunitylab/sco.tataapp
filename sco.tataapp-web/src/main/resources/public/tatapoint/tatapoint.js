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
		[ '$rootScope', '$scope', '$sce','Settings', 'SharedData',
				function($rootScope, $scope, $sce, Settings, SharedData) {
					var agencyId = SharedData.getAppId();
					Settings.read({aid : agencyId},function(data) {
						var tatapointCalId = data.tatapointCalId;
						$scope.tatapointCalendarURL = $sce.trustAsResourceUrl("https://calendar.google.com/calendar/embed?showTitle=0&amp;showPrint=0&amp;showCalendars=0&amp;showTz=0&amp;height=600&amp;wkst=2&amp;hl=it&amp;bgcolor=%23FFFFFF&amp;src="+ tatapointCalId +"&amp;color=%232F6309&amp;ctz=Europe%2FRome");
					});

				} ])

.factory('Settings', [ '$resource', function($resource) {
	return $resource('console/api/agency/:aid/settings', {
		aid : '@aid'
	}, {
		read: {
			method : 'get'
		}
	}
	);
} ]);