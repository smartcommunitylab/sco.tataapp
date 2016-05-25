'use strict';

angular.module('app.ctrls', ['ngResource','i18nmessages'])
.controller('MainCtrl', ['$scope', '$window','$location','SecurityCheck', 'AppId', 'SharedData',
                 function($scope, $window, $location, SecurityCheck, AppId, SharedData) {
	$scope.message = "Angular ROCKSSS";
	
	var activeHome = "active";
	var activeTata = "";
	var activeFamily = "";
	var activeTataPoint = "";
	var activeTataRate = "";
	
	$scope.checkCalendarPermissions = function(){
		var agencyId = "";
		AppId.getId(function(data){
			if(data && data.id){
				agencyId = data.id;
				SharedData.setAppId(agencyId);
			} else {
				agencyId = SharedData.setAppId();
			}
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
		})
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

.factory('AppId', [ '$resource', function($resource) {
	return $resource('console/api/agencyid', null, {
		getId : {
			isArray : false,
			method : 'get',
			transformResponse : function(data) {
				return { id: data };
			}
		}
	});
}])

.service('SharedData', function(){
	this.appId = 'progetto92';
		
	this.getAppId = function(){
		return this.appId;
	};
	
	this.setAppId = function(appId){
		this.appId = appId;
	};
		
})

.filter('i18n', function (i18nmessages) {
    return function (input) {
        if (!angular.isString(input)) {
            return input;
        }
        return i18nmessages[input] || '?'+input+'?';
    };
});
