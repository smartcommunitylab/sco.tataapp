'use strict';

angular.module('app.tata',[ 'ngRoute', 'ngResource' ])

.config(['$routeProvider', function($routeProvider) {
  $routeProvider.when('/tata', {
    templateUrl: 'tata/view.html',
    controller: 'TataCtrl'
  });
}])

.controller('TataCtrl', [ '$rootScope', '$scope','$uibModal', 'Tata',
          				function($rootScope, $scope, $uibModal, Tata) {
	
	$scope.tatalist = Tata.list();
	$scope.showNewTataForm = false;
	$scope.showTataDetails = false;
	$scope.agencyId = "tataApp"; 	// TODO: pass the parameter from configuration file
	$scope.mailPattern=/^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
	
    /*// ------------------ Start datetimepicker section -----------------------
    $scope.today = function() {
        $scope.dt = new Date();
    };
    $scope.today();

    $scope.clear = function () {
        //$scope.dt = null;
    };

    // Disable weekend selection
    $scope.disabled = function(date, mode) {
         return ( mode === 'day' && ( date.getDay() === 0 || date.getDay() === 6 ) );
    };

    $scope.toggleMin = function() {
         $scope.minDate = $scope.minDate ? null : new Date();
    };
    $scope.toggleMin();

    $scope.dateOptions = {
    	//datepickerMode: "'year'",	// value setted in file lib/ui-bootstrap-tpls.min.js
        formatYear: 'yyyy',
        startingDay: 1,
        showWeeks: 'false'
    };

    $scope.initDate = new Date();
    $scope.formats = ['shortDate', 'dd/MM/yyyy','dd-MMMM-yyyy', 'yyyy/MM/dd', 'dd.MM.yyyy'];
    $scope.format = $scope.formats[0];*/
	
	/*
	String name;
	String surname;
	long birthdate;
	String email;
	String address;
	String city;
	List<String> languages;
	boolean carOwner;
	*/
	
	$scope.trueVal = true;
	$scope.falseVal = false;
	
	$scope.tataconf = {
		name: {
			required : true
		},
		surname: {
			required: true
		},
		birthdate: {
			required: false
		},
		email: {
			required: true
		},
		address: {
			required: true
		},
		city: {
			required: true
		},
		languages: {
			required: true
		},
		carowner: {
			required: true
		}
	};
	
	// method used to show the new tata input form
	$scope.newTata = function(){
		$scope.isInit = true;
		$scope.tata = new Tata();
		$scope.tata.boollanguage = [false,false,false,false,false];
		$scope.tata.languages = [];
		$scope.showNewTataForm = true;
	};
	
	// method used to create a new tata with the data passed in new tata form
	$scope.createTata = function(form, tata){
		$scope.isInit = false;
		if(form.$valid){
			// correctLanguages form bool to string
			for(var i = 0; i < tata.boollanguage.length; i++){
				if(tata.boollanguage[i]){
					switch(i){
						case 0:
							tata.languages.push("italiano");
							break;
						case 1:
							tata.languages.push("inglese");
							break;
						case 2:
							tata.languages.push("tedesco");
							break;
						case 3:
							tata.languages.push("francese");
							break;
						case 4:
							tata.languages.push("spagnolo");
							break;	
						default:
							break;
					}
				}
			}
			var correctedtata = {
				name: tata.name,
				surname: tata.surname,
				birthdate: tata.birthdate,
				email: tata.email,
				address: tata.address,
				city: tata.city,
				languages: tata.languages,
				carOwner: tata.carOwner,
				agencyId: $scope.agencyId
			};
			
			$scope.corrtata = new Tata(correctedtata);
			
			console.log(JSON.stringify($scope.corrtata));
	        $scope.corrtata.$save(function() {
	        	console.log('Saved tata');
	        	$scope.tatalist = Tata.list();
	        });
			
			$scope.showNewTataForm = false;
		}
	};
	
	$scope.showTata = function(tata){
		$scope.showTataDetails = true;
		$scope.vtata = tata;
	};
	
	$scope.hideTata = function(){
		$scope.showTataDetails = false;
	};
	
	$scope.getStrings = function(list) {
		var strings = "";
		for(var i = 0; i < list.length; i++){
			strings += list[i] + ", ";
		}
		return strings.substring(0, strings.length-2);
	}
	
	// method used to close the new tata input form without create a new tata
	$scope.closeNewTataView = function(){
		$scope.tata = null;
		$scope.isInit = true;
		$scope.showNewTataForm = false;
	};
	
	$scope.someSelectedLang = function(languages){
		var somes = false;
		if(languages){
			for(var i = 0; i < languages.length; i++){
				if(languages[i]){
					somes = true;
				}
			}
		}
		return somes;
	};
	
}])

.factory('Tata', [ '$resource', function($resource) {
	return $resource('api/agency/:id/tata/:tid', {
		id : "tataApp"
	}, {
		list : {
			isArray : true,
			method : 'get',
			transformResponse : function(data, headers) {
				return JSON.parse(data).content;
			}
		}
	});
} ]);
