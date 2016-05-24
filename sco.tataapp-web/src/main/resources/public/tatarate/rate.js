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
	$scope.agencyId = "tataApp"; 	// TODO: pass the parameter from configuration file
	
	$scope.rateMinKey = "<15";
	$scope.rateMediumKey = "16-24";
	$scope.rateMaxKey = "25-40";
	$scope.rateMaxNoFoodKey = "25-40 no pasto";
	
	$scope.tataratelist = {
		hours: [],
		daily: {
			base: {
				min: null,
				medium: null,
				max: null,
				max_no_food: null
			},
			handicapped: {
				min: null,
				medium: null,
				max: null,
				max_no_food: null
			}
		},
		holiday: {
			base: {
				min: null,
				medium: null,
				max: null,
				max_no_food: null
			},
			handicapped: {
				min: null,
				medium: null,
				max: null,
				max_no_food: null
			}
		},
		night: {
			base: {
				min: null,
				medium: null,
				max: null,
				max_no_food: null
			},
			handicapped: {
				min: null,
				medium: null,
				max: null,
				max_no_food: null
			}
		}
	};
	
	$scope.showTataRateDailyEdit = false;
	$scope.showTataRateNightEdit = false;
	$scope.showTataRateHolidayEdit = false;
	$scope.showDetails = false;
	$scope.agencyId = "tataApp"; 	// TODO: pass the parameter from configuration file
	
	$scope.trueVal = true;
	$scope.falseVal = false;
	$scope.tata = null;
	
	$scope.getTataRate = function(){
		var tataratelistDB = TataRate.get({aid: $scope.agencyId}, function(){
			$scope.tataratelist.hours = {
				min: $scope.rateMinKey,
				medium: $scope.rateMediumKey,
				max: $scope.rateMaxKey,
				max_no_food: $scope.rateMaxNoFoodKey
			};
			
			if(tataratelistDB){
				$scope.tataratelist.id = tataratelistDB.id;
				// working daily rate
				for(var i = 0; i < tataratelistDB.daily.length; i++){
					if(tataratelistDB.daily[i].hourRange == $scope.rateMinKey){
						$scope.tataratelist.daily.base.min = tataratelistDB.daily[i].rate;
					} else if(tataratelistDB.daily[i].hourRange == $scope.rateMediumKey){
						$scope.tataratelist.daily.base.medium = tataratelistDB.daily[i].rate;
					} else if(tataratelistDB.daily[i].hourRange == $scope.rateMaxKey){
						$scope.tataratelist.daily.base.max = tataratelistDB.daily[i].rate;
					} else if(tataratelistDB.daily[i].hourRange == $scope.rateMaxNoFoodKey){
						$scope.tataratelist.daily.base.max_no_food = tataratelistDB.daily[i].rate;
					}
				}
				for(var i = 0; i < tataratelistDB.dailyDisability.length; i++){
					if(tataratelistDB.dailyDisability[i].hourRange == $scope.rateMinKey){
						$scope.tataratelist.daily.handicapped.min = tataratelistDB.dailyDisability[i].rate;
					} else if(tataratelistDB.dailyDisability[i].hourRange == $scope.rateMediumKey){
						$scope.tataratelist.daily.handicapped.medium = tataratelistDB.dailyDisability[i].rate;
					} else if(tataratelistDB.dailyDisability[i].hourRange == $scope.rateMaxKey){
						$scope.tataratelist.daily.handicapped.max = tataratelistDB.dailyDisability[i].rate;
					} else if(tataratelistDB.dailyDisability[i].hourRange == $scope.rateMaxNoFoodKey){
						$scope.tataratelist.daily.handicapped.max_no_food = tataratelistDB.dailyDisability[i].rate;
					}
				}
				/*$scope.tataratelist.daily.base = {
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
				};*/
				
				// holiday daily rate
				for(var i = 0; i < tataratelistDB.festive.length; i++){
					if(tataratelistDB.festive[i].hourRange == $scope.rateMinKey){
						$scope.tataratelist.holiday.base.min = tataratelistDB.festive[i].rate;
					} else if(tataratelistDB.festive[i].hourRange == $scope.rateMediumKey){
						$scope.tataratelist.holiday.base.medium = tataratelistDB.festive[i].rate;
					} else if(tataratelistDB.festive[i].hourRange == $scope.rateMaxKey){
						$scope.tataratelist.holiday.base.max = tataratelistDB.festive[i].rate;
					} else if(tataratelistDB.festive[i].hourRange == $scope.rateMaxNoFoodKey){
						$scope.tataratelist.holiday.base.max_no_food = tataratelistDB.festive[i].rate;
					}
				}
				for(var i = 0; i < tataratelistDB.festiveDisability.length; i++){
					if(tataratelistDB.festiveDisability[i].hourRange == $scope.rateMinKey){
						$scope.tataratelist.holiday.handicapped.min = tataratelistDB.festiveDisability[i].rate;
					} else if(tataratelistDB.festiveDisability[i].hourRange == $scope.rateMediumKey){
						$scope.tataratelist.holiday.handicapped.medium = tataratelistDB.festiveDisability[i].rate;
					} else if(tataratelistDB.festiveDisability[i].hourRange == $scope.rateMaxKey){
						$scope.tataratelist.holiday.handicapped.max = tataratelistDB.festiveDisability[i].rate;
					} else if(tataratelistDB.festiveDisability[i].hourRange == $scope.rateMaxNoFoodKey){
						$scope.tataratelist.holiday.handicapped.max_no_food = tataratelistDB.festiveeDisability[i].rate;
					}
				}
				/*$scope.tataratelist.holiday.base = {
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
				};*/
				
				// holiday night rate
				for(var i = 0; i < tataratelistDB.nighttime.length; i++){
					if(tataratelistDB.nighttime[i].hourRange == $scope.rateMinKey){
						$scope.tataratelist.night.base.min = tataratelistDB.nighttime[i].rate;
					} else if(tataratelistDB.nighttime[i].hourRange == $scope.rateMediumKey){
						$scope.tataratelist.night.base.medium = tataratelistDB.nighttime[i].rate;
					} else if(tataratelistDB.nighttime[i].hourRange == $scope.rateMaxKey){
						$scope.tataratelist.night.base.max = tataratelistDB.nighttime[i].rate;
					} else if(tataratelistDB.nighttime[i].hourRange == $scope.rateMaxNoFoodKey){
						$scope.tataratelist.night.base.max_no_food = tataratelistDB.nighttime[i].rate;
					}
				}
				for(var i = 0; i < tataratelistDB.nighttimeDisability.length; i++){
					if(tataratelistDB.nighttimeDisability[i].hourRange == $scope.rateMinKey){
						$scope.tataratelist.night.handicapped.min = tataratelistDB.nighttimeDisability[i].rate;
					} else if(tataratelistDB.nighttimeDisability[i].hourRange == $scope.rateMediumKey){
						$scope.tataratelist.night.handicapped.medium = tataratelistDB.nighttimeDisability[i].rate;
					} else if(tataratelistDB.nighttimeDisability[i].hourRange == $scope.rateMaxKey){
						$scope.tataratelist.night.handicapped.max = tataratelistDB.nighttimeDisability[i].rate;
					} else if(tataratelistDB.nighttimeDisability[i].hourRange == $scope.rateMaxNoFoodKey){
						$scope.tataratelist.night.handicapped.max_no_food = tataratelistDB.nighttimeDisability[i].rate;
					}
				}
				/*$scope.tataratelist.night.base = {
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
				};*/
				
				//Check to show or hide the the 'max_no_food' part
				if(!$scope.tataratelist.daily.base.max_no_food){
					$scope.tataratelist.hours.max_no_food = null;
				}
			}
		});
	}
	
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
				id: tataRate.id,
				daily: $scope.correctRateObjectToDbObject(tataRate.daily.base),
				dailyDisability: $scope.correctRateObjectToDbObject(tataRate.daily.handicapped),
				festive: $scope.correctRateObjectToDbObject(tataRate.holiday.base),
				festiveDisability: $scope.correctRateObjectToDbObject(tataRate.holiday.handicapped),
				nighttime: $scope.correctRateObjectToDbObject(tataRate.night.base),
				nighttimeDisability: $scope.correctRateObjectToDbObject(tataRate.night.handicapped),
				agencyId: $scope.agencyId
			};
			$scope.tatarate = new TataRate(corrTataRate);
			console.log(JSON.stringify($scope.tatarate));
		    $scope.tatarate.$save({aid: $scope.agencyId}, function() {
		    	console.log('Saved tatarate');
		    	$scope.tataratelist = tataRate;
		    });
		    
		    $scope.closeEditTataRateView();
		}
	};
	
	$scope.correctRateObjectToDbObject = function(rateObject){
		var corrDbObjList = [];
		var corrDbObjRateMin = {
			hourRange: "",
			rate: null
		};
		var corrDbObjRateMedium = {
			hourRange: "",
			rate: null
		};
		var corrDbObjRateMax = {
			hourRange: "",
			rate: null
		};
		var corrDbObjRateMaxNoFood = {
			hourRange: "",
			rate: null
		};
		
		if(rateObject.min){
			corrDbObjRateMin.hourRange = $scope.rateMinKey;
			corrDbObjRateMin.rate = (typeof rateObject.min == "string") ? parseFloat(rateObject.min) : rateObject.min;
			corrDbObjList.push(corrDbObjRateMin);
		}
		if(rateObject.medium){
			corrDbObjRateMedium.hourRange = $scope.rateMediumKey;
			corrDbObjRateMedium.rate = rateObject.medium
			corrDbObjList.push(corrDbObjRateMedium);
		}
		if(rateObject.max){
			corrDbObjRateMax.hourRange = $scope.rateMaxKey;
			corrDbObjRateMax.rate = rateObject.max
			corrDbObjList.push(corrDbObjRateMax);
		}
		if(rateObject.max_no_food){
			corrDbObjRateMaxNoFood.hourRange = $scope.rateMaxNoFoodKey;
			corrDbObjRateMaxNoFood.rate = rateObject.max_no_food
			corrDbObjList.push(corrDbObjRateMaxNoFood);
		}
		return corrDbObjList;
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
	return $resource('console/api/agency/:aid/pricelist', {
		aid: '@aid'
	}, {
		list : {
			isArray : true,
			method : 'get',
			transformResponse : function(data, headers) {
				if(data){
					return JSON.parse(data).content;
				} else {
					return data;
				}
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
