'use strict';

angular.module('app.rate',[ 'ngRoute', 'ngResource'])

.config(['$routeProvider', function($routeProvider) {
  $routeProvider.when('/tatarate', {
    templateUrl: 'tatarate/rate.html',
    controller: 'TataRateCtrl'
  });
}])

.controller('TataRateCtrl', [ '$rootScope', '$scope','$uibModal', 'TataRate',
          				function($rootScope, $scope, $uibModal, TataRate) {
	
	$scope.decimalNumbers = /^([0-9]+)[\.]{0,1}[0-9]{0,2}$/;
	//$scope.tataratelist = TataRate.list();
	$scope.tataratelist = {
		hours: [],
		daily: {
			base: [],
			handicapped: []
		},
		holiday: {
			base: [],
			handicapped: []
		},
		night: {
			base: [],
			handicapped: []
		}
	};
	
	$scope.tataratelist.hours = {
		min: "<15 ore",
		medium: "16-24",
		max: "25-40",
		max_no_food: "25-40 no pasto"
	};
	// working daily rate
	$scope.tataratelist.daily.base = {
		min: 14.64,
		medium: 13.95,
		max: 13.42,
		max_no_food: 13.43
	};
	$scope.tataratelist.daily.handicapped = {
		min: 18.00,
		medium: 18.00,
		max: 18.00,
		max_no_food: 18.00
	};
	// holiday daily rate
	$scope.tataratelist.holiday.base = {
		min: 20.59,
		medium: 19.62,
		max: 19.10,
		max_no_food: 19.10
	};
	$scope.tataratelist.holiday.handicapped = {
		min: 25.31,
		medium: 25.31,
		max: 25.31,
		max_no_food: 25.31
	};
	// holiday night rate
	$scope.tataratelist.night.base = {
		min: 16.63,
		medium: 15.85,
		max: 15.32,
		max_no_food: 15.33
	};
	$scope.tataratelist.night.handicapped = {
		min: 20.45,
		medium: 20.45,
		max: 20.45,
		max_no_food: 20.45
	};
	
	$scope.showTataRateDailyEdit = false;
	$scope.showTataRateNightEdit = false;
	$scope.showTataRateHolidayEdit = false;
	$scope.showDetails = false;
	$scope.agencyId = "tataApp"; 	// TODO: pass the parameter from configuration file
	
	$scope.trueVal = true;
	$scope.falseVal = false;
	$scope.tata = null;
	
	// method used to show the new tata input form
	$scope.editTataRate = function(id){
		switch (id){
			case 1:
				$scope.showTataRateDailyEdit = true;
				$scope.showTataRateNightEdit = false;
				$scope.showTataRateHolidayEdit = false;
				break;
			case 2:
				$scope.showTataRateDailyEdit = false;
				$scope.showTataRateNightEdit = false;
				$scope.showTataRateHolidayEdit = true;
				break;
			case 3:
				$scope.showTataRateDailyEdit = false;
				$scope.showTataRateNightEdit = true;
				$scope.showTataRateHolidayEdit = false;
				break;
			default:
				break;
		}
	};
	
	// method used to create a new tata with the data passed in new tata form
	$scope.updateTataRate = function(form, tataRate){
		$scope.isInit = false;
		if(form.$valid){
				
			var corrTataRate = {
				hours: tataRate.hours,
				daily: tataRate.daily,
				holiday: tataRate.holiday,
				night: tataRate.night
			};
			$scope.tatarate = new TataRate(corrTataRate);
			console.log(JSON.stringify($scope.tatarate));
		    $scope.tatarate.$save(function() {
		    	console.log('Saved tatarate');
		    	$scope.tatalist = Tata.list();
		    });
		    
		    $scope.closeEditTataRateView();
		}
	};
	
	// method used to close the new tata input form without create a new tata
	$scope.closeEditTataRateView = function(){
		$scope.isInit = true;
		$scope.showTataRateDailyEdit = false;
		$scope.showTataRateNightEdit = false;
		$scope.showTataRateHolidayEdit = false;
	};
	
	// method used to close the edit tata input form without edit ad existing tata
	$scope.closeEditTataView = function(){
		$scope.isInit = false;
		$scope.showEditTataForm = false;
		$scope.showTataDetails = true;
	}
	
}])

.factory('TataRate', [ '$resource', function($resource) {
	return $resource('api/agency/:id/tatarate/:tid', {
		id : "tataApp",
		tid: '@id'
	}, {
		list : {
			isArray : true,
			method : 'get',
			transformResponse : function(data, headers) {
				return JSON.parse(data).content;
			}
		}
	});
} ])

.filter('euroVal', function() {
	return function(input){
		if(typeof input == "string"){
			if(input.indexOf(",") > -1){
				input = input.replace(",", ".");
			}
			/*if(input.indexOf(".") > -1){
				input = input.replace(".", ",");
			}*/
		}
		input = parseFloat(input);
		return "" + parseFloat(Math.round(input * 100) / 100).toFixed(2);
	};
})
