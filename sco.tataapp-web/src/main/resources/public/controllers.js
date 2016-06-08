'use strict';

angular.module('app.ctrls', ['ngResource','i18nmessages'])
.controller('MainCtrl', ['$scope', '$window','$location','SecurityCheck', 'SharedData',
                 function($scope, $window, $location, SecurityCheck, SharedData) {
	$scope.message = "Angular ROCKSSS";
	
	var activeHome = "active";
	var activeTata = "";
	var activeFamily = "";
	var activeTataPoint = "";
	var activeTataRate = "";
	
	$scope.checkCalendarPermissions = function(){
		var agencyId = SharedData.getAppId();
		//var skip = ($location.search()).jump_google;
		var skip = ($window.location.search.indexOf("jump_google=true") > -1) ? true : false;
		if(!skip){
			SecurityCheck.get({ agencyId:agencyId }, function(data) {
				if(!data.calendarPermissionOk){
					console.log("redirect url " + JSON.stringify(data.authorizationURL));
					$window.location.href = data.authorizationURL;
				}
			});
		}
	};
	
	$scope.isActiveHome = function(){
		return activeHome;
	};
	
	$scope.isActiveTata = function(){
		return activeTata;
	};
	
	$scope.isActiveFamily = function(){
		return activeFamily;
	};
	
	$scope.isActiveTataPoint = function(){
		return activeTataPoint;
	};
	
	$scope.isActiveTataRate = function(){
		return activeTataRate;
	};
	
	$scope.showHome = function(){
		activeHome = "active";
		activeTata = "";
		activeFamily = "";
		activeTataPoint = "";
		activeTataRate = "";
	};

	$scope.showTata = function(){
		activeHome = "";
		activeTata = "active";
		activeFamily = "";
		activeTataPoint = "";
		activeTataRate = "";
	};
	
	$scope.showFamily = function(){
		activeHome = "";
		activeTata = "";
		activeFamily = "active";
		activeTataPoint = "";
		activeTataRate = "";
	};
	
	$scope.showTataPoint = function(){
		activeHome = "";
		activeTata = "";
		activeFamily = "";
		activeTataPoint = "active";
		activeTataRate = "";
	};
	
	$scope.showTataRate = function(){
		activeHome = "";
		activeTata = "";
		activeFamily = "";
		activeTataPoint = "";
		activeTataRate = "active";
	};
	
}])

.factory('SecurityCheck', [ '$resource', function($resource) {
	return $resource('console/api/agency/:agencyId/settings/permissions', {
		agencyId: '@id'
	});
}])

/*.factory('AppId', [ '$resource', function($resource) {
	return $resource('console/api/agencyid', null, {
		getId : {
			isArray : false,
			method : 'get',
			transformResponse : function(data) {
				return { id: data };
			}
		}
	});
}])*/

.service('SharedData', function(){
	// shared variables between controllers
	this.appId = 'progetto92';	// appId value; Change only here!
	
	// google calendar supports only this colors
	var supportedCalendarColor = ["%23B1365F", "%235C1158", "%23711616", "%23691426", "%23BE6D00", "%23B1440E", "%23853104", "%238C500B", "%23754916", "%2388880E", "%23AB8B00", "%23856508", "%2328754E", "%231B887A", "%2328754E", "%230D7813", "%23528800", "%23125A12", "%232F6309", "%232F6213", "%230F4B38", "%235F6B02", "%234A716C", "%236E6E41", "%2329527A", "%232952A3", "%234E5D6C", "%235A6986", "%23182C57", "%23060D5E", "%23113F47", "%237A367A", "%235229A3", "%23865A5A", "%23705770", "%2323164E", "%235B123B", "%2342104A", "%23875509", "%238D6F47", "%236B3304", "%23333333"];
	
	this.getAppId = function(){
		return this.appId;
	};
	
	this.setAppId = function(appId){
		this.appId = appId;
	};
	
	this.getPrimaryCalendarColor = function() {
		return supportedCalendarColor[0];
	}
	
	this.getRandomCalendarColor = function() {
		return supportedCalendarColor[Math.floor(Math.random() * (42 - 1 + 1) + 1)];
	}
		
})

.filter('i18n', function (i18nmessages) {
    return function (input) {
        if (!angular.isString(input)) {
            return input;
        }
        return i18nmessages[input] || '?'+input+'?';
    };
});
