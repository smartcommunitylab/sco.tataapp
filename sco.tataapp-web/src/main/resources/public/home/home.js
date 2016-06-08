'use strict';

angular.module('app.home', ['ngRoute', 'ngSanitize'])

.config(['$routeProvider', function($routeProvider) {
  $routeProvider.when('/home', {
    templateUrl: 'home/home.html',
    controller: 'HomeCtrl'
  });
  $routeProvider.when('/', {
	    templateUrl: 'home/home.html',
	    controller: 'HomeCtrl'
	  });
}])

.controller('HomeCtrl', ['$sce','$scope','Tata', 'SharedData', 'Settings',function($sce, $scope, Tata, SharedData, Settings) {
	// get primary calendar
	// get all babysitter calendars
	
	Tata.list({aid : SharedData.getAppId()},function(result) {
		var cals = '';
		result.forEach(function(data) {
			if(data.calendarURL) {
				if(cals.length !== 0) {
					cals +="&amp;";
				}
				cals += 'src=' + data.calendarURL;
			}
		});
		
		Settings.read({aid : SharedData.getAppId()},function(data) {
			var primaryCalendarId = data.googleAccount;
			var params = cals.length === 0 ? 'src='+primaryCalendarId : cals+'&amp;src='+primaryCalendarId;
			var htmlCode = '<iframe src="https://calendar.google.com/calendar/embed?showTitle=0&amp;showPrint=0&amp;showTz=0&amp;height=600&amp;wkst=2&amp;hl=it&amp;bgcolor=%23FFFFFF&amp;'+params+'&amp;ctz=Europe%2FRome" style="border-width: 0" width="800" height="600" frameborder="0" scrolling="no"></iframe>';
			$scope.calEmbed = $sce.trustAsHtml(htmlCode);
		});
	});
	
}]);