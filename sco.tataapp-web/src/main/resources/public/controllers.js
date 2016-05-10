'use strict';

angular.module('app.ctrls', [])
.controller('MainCtrl', ['$scope',function($scope) {
	$scope.message = "Angular ROCKSSS";
	
	var activeHome = "active";
	var activeTata = "";
	var activeFamily = "";
	var activeTataPoint = "";
	
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
	
	$scope.showHome = function(){
		activeHome = "active";
		activeTata = "";
		activeFamily = "";
		activeTataPoint = "";
	};

	$scope.showTata = function(){
		activeHome = "";
		activeTata = "active";
		activeFamily = "";
		activeTataPoint = "";
	};
	
	$scope.showFamily = function(){
		activeHome = "";
		activeTata = "";
		activeFamily = "active";
		activeTataPoint = "";
	};
	
	$scope.showTataPoint = function(){
		activeHome = "";
		activeTata = "";
		activeFamily = "";
		activeTataPoint = "active";
	};
	
}]);