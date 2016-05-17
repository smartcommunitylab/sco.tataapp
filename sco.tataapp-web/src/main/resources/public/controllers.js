'use strict';

angular.module('app.ctrls', [])
.controller('MainCtrl', ['$scope',function($scope) {
	$scope.message = "Angular ROCKSSS";
	
	var activeHome = "active";
	var activeTata = "";
	var activeFamily = "";
	var activeTataPoint = "";
	var activeTataRate = "";
	
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
	
}]);