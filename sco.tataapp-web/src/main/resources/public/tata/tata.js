'use strict';

angular.module('app.tata',[ 'ngRoute', 'ngResource', 'angularFileUpload'])

.config(['$routeProvider', function($routeProvider) {
  $routeProvider.when('/tata', {
    templateUrl: 'tata/tata.html',
    controller: 'TataCtrl'
  });
}])

.controller('TataCtrl', [ '$sce', '$rootScope', '$scope','$uibModal', 'Tata', 'FileUploader', 'Avatar', 'SharedData',
          				function($sce, $rootScope, $scope, $uibModal, Tata, FileUploader, Avatar, SharedData) {
	
	$scope.showNewTataForm = false;
	$scope.showTataDetails = false;
	$scope.showMaxImageSizeError = false;
	$scope.agencyId = "";
	$scope.mailPattern=/^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
	
	$scope.onErrorSrc = "images/empty_avatar.png";
	$scope.maxImgSize = 1048576; // bytes
	$scope.itaLang = "IT";
	$scope.engLang = "EN";
	$scope.gerLang = "DE";
	$scope.fraLang = "FR";
	$scope.spaLang = "ES";
	$scope.rusLang = "RU";	// to be added in form language list
		
	$scope.trueVal = true;
	$scope.falseVal = false;
	$scope.tata = null;
	
	$scope.uploader = new FileUploader({
		queueLimit: 1,
		alias: "tImage",
		removeAfterUpload: true
	});	// file upload element
	
	$scope.tataconf = {
		profileimage: {
			required : false
		},
		name: {
			required : true
		},
		surname: {
			required: true
		},
		birthdate: {
			required: true
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
		qualification: {
			required: true
		},
		languages: {
			required: true
		},
		description: {
			required: false
		},
		updates: {
			required: false
		},
		carowner: {
			required: true
		}
	};
	
	// Date picker functions -------------------------------------------------------------------------------------------------------------------------
	
	$scope.today = function(date) {
	    $scope.tata.birthdate = new Date(date);
	};
	//$scope.today();

	$scope.clear = function() {
	    $scope.tata.birthdate = null;
	};

	$scope.inlineOptions = {
	    customClass: getDayClass,
	    minDate: new Date(),
	    showWeeks: true
	};

	$scope.dateOptions = {
		datepickerMode:"year",	// default open to year
	    formatYear: 'yyyy',
	    //dateDisabled: disabled,
	    maxDate: new Date(2020, 5, 22),
	    minDate: new Date(),
	    startingDay: 1,
	    showWeeks: false
	};

	// Disable weekend selection
	function disabled(data) {
	    var date = data.date,
	    mode = data.mode;
	    return mode === 'day' && (date.getDay() === 0 || date.getDay() === 6);
	}

	$scope.toggleMin = function() {
	    $scope.inlineOptions.minDate = $scope.inlineOptions.minDate ? null : new Date();
	    $scope.dateOptions.minDate = $scope.inlineOptions.minDate;
	};
	$scope.toggleMin();

	$scope.open1 = function() {
	    $scope.popup1.opened = true;
	};

	$scope.setDate = function(year, month, day) {
	    $scope.dt = new Date(year, month, day);
	};

	$scope.format = 'shortDate';
	$scope.altInputFormats = ['M!/d!/yyyy'];

	$scope.popup1 = {
	    opened: false
	};

	var tomorrow = new Date();
	tomorrow.setDate(tomorrow.getDate() + 1);
	var afterTomorrow = new Date();
	afterTomorrow.setDate(tomorrow.getDate() + 1);
	$scope.events = [
	    {
	    	date: tomorrow,
	    	status: 'full'
	    },
	    {
	    	date: afterTomorrow,
	    	status: 'partially'
	    }
	];

	function getDayClass(data) {
	    var date = data.date,
	    mode = data.mode;
	    if (mode === 'day') {
	    	var dayToCheck = new Date(date).setHours(0,0,0,0);
	    	
	    	for (var i = 0; i < $scope.events.length; i++) {
	    		var currentDay = new Date($scope.events[i].date).setHours(0,0,0,0);

	    		if (dayToCheck === currentDay) {
	    			return $scope.events[i].status;
	    		}
	    	}
	    }
	    return '';
	}
	
	// ---------------------------------------------------------------------------------------------------------------------------------------------
	
	// Functions for modal -------------------------------------------------------------------------------------------------------------------------
	$scope.open = function (size, tata) {
	    var modalInstance = $uibModal.open({
	      animation: $scope.animationsEnabled,
	      templateUrl: 'tataDeleteConfirm.html',
	      controller: 'ModalInstanceCtrl',
	      size: size,
	      resolve: {
	        data: function () {
	          return tata;
	        }
	      }
	    });
	    
	    modalInstance.result.then(function (tata) {
	      $scope.selected = selectedItem;
	   }, function () {
	      $log.info('Modal dismissed at: ' + new Date());
	   });
	};
	
	
	// ---------------------------------------------------------------------------------------------------------------------------------------------
	
	// method clearSizeError: used to hide the image size error message
	$scope.clearSizeError = function(){
		$scope.showMaxImageSizeError = false;
	};
	
	// method getTataList: used to retrieve the tata list
	$scope.getTataList = function(){
		$scope.agencyId = SharedData.getAppId();	// here I init the agencyId readed from conf file
			$scope.q = {};
			Tata.list({aid: $scope.agencyId}, function(data){
				$scope.tatalist = data;
			});
		//});
	};
	
	// method showTata: used to show the tata details in the specific page/view
	$scope.showTata = function(tata){
		$scope.showTataDetails = true;
		$scope.vtata = tata;
		$scope.vtata.profileImage = $scope.composeTataImageUrl(tata.id);
		if(tata.calendarURL) {
			$scope.vtata.absCalendarURL = $sce.trustAsResourceUrl("https://calendar.google.com/calendar/embed?showTitle=0&amp;showPrint=0&amp;showCalendars=0&amp;showTz=0&amp;height=600&amp;wkst=2&amp;hl=it&amp;bgcolor=%23FFFFFF&src=" + tata.calendarURL + "&amp;color=%230F4B38&amp;ctz=Europe%2FRome");
		}
	};
	
	// method newTata: used to show the new tata input form
	$scope.newTata = function(){
		$scope.isInit = true;
		$scope.tata = new Tata();
		$scope.uploader.clearQueue();
		$scope.tata.boollanguage = [false,false,false,false,false];
		$scope.tata.languages = [];
		$scope.today("1/1/1970 00:00:00");	// init birthday value
		$scope.showNewTataForm = true;
	};
	
	// method editTata: used to show the edit tata input form (with existing data preloaded)
	$scope.editTata = function(tata){
		$scope.isInit = true;
		$scope.tata = tata;	//here I have to correct some data
		$scope.tata.boollanguage = $scope.fromLanguagesToCheck($scope.tata.languages);
		var completeBase64 = $scope.getBase64Image($scope.composeTataImageUrl(tata.id));
		$scope.showTataDetails = false;
		$scope.showEditTataForm = true;
	};
	
	// method getBase64Image: from an image url the method retrieve the base64 encoded string value
	$scope.getBase64Image = function(url) {
	    // Create an empty canvas element   
	    var img = new Image();
	    img.setAttribute('crossOrigin', 'anonymous');
	    img.onload = function () {
	        var canvas = document.createElement("canvas");
	        canvas.width =this.width;
	        canvas.height =this.height;

	        var ctx = canvas.getContext("2d");
	        ctx.drawImage(this, 0, 0);
	        var dataURL = canvas.toDataURL("image/png");
	        var file = $scope.dataURItoBlob(dataURL);
	        $scope.uploader.addToQueue(file);
	    };
	    img.src = url;
	};
	
	// method fromLanguagesToCheck: used to get the check languages value from the language string list
	$scope.fromLanguagesToCheck = function(languageStrings){
		var boolValues = [false, false, false, false, false];
		for(var i = 0; i < languageStrings.length; i++){
			switch(languageStrings[i]){
				case $scope.itaLang:
					boolValues[0] = true;
					break;
				case $scope.engLang:
					boolValues[1] = true;
					break;
				case $scope.gerLang:
					boolValues[2] = true;
					break;
				case $scope.fraLang:
					boolValues[3] = true;
					break;
				case $scope.spaLang:
					boolValues[4] = true;
					break;
				default: break;	
			}
		}
		return boolValues;
	};
	
	// method createTata: used to create a new tata with the data passed in new tata form
	$scope.createTata = function(form, tata){
		$scope.isInit = false;
		if(form.$valid){
			if(($scope.tataconf.profileimage.required && $scope.uploader.queue == null) || ($scope.tataconf.profileimage.required && $scope.uploader.queue.length == 0)){
				$scope.showNoProfileError = true;
			} else {
				$scope.showNoProfileError = false;
				var item = null;
		       	for(var i = 0; i < $scope.uploader.queue.length; i++){
					item = $scope.uploader.queue[i];
				}
		       	if(item && (item.file.size > $scope.maxImgSize)){
					$scope.showMaxImageSizeError = true;
				} else {
					$scope.showMaxImageSizeError = false;
					// correctLanguages form bool to string
					for(var i = 0; i < tata.boollanguage.length; i++){
						if(tata.boollanguage[i]){
							switch(i){
								case 0:
									tata.languages.push($scope.itaLang);
									break;
								case 1:
									tata.languages.push($scope.engLang);
									break;
								case 2:
									tata.languages.push($scope.gerLang);
									break;
								case 3:
									tata.languages.push($scope.fraLang);
									break;
								case 4:
									tata.languages.push($scope.spaLang);
									break;	
								default:
									break;
							}
						}
					}
					var correctedtata = {
						name: tata.name,
						surname: tata.surname,
						birthdate: $scope.castDateToMillis(tata.birthdate),
						email: tata.email,
						address: tata.address,
						city: tata.city,
						qualification: tata.qualification,
						languages: tata.languages,
						carOwner: tata.carOwner,
						agencyId: $scope.agencyId,
						description: tata.description,
						updates: tata.updates,
						calendarURL : tata.calendarURL
					};
					
					$scope.corrtata = new Tata(correctedtata);
					console.log(JSON.stringify($scope.corrtata));
				    $scope.corrtata.$save({aid: $scope.agencyId}, function(data) {
				       	console.log('Saved tata');
				       	Tata.list({aid: $scope.agencyId, noCache: new Date().getTime()}, function(data){
				       		$scope.tatalist = data;
				       	});
				       	// here I load the image
				       	if(item){
					       	for(var i = 0; i < $scope.uploader.queue.length; i++){
								item = $scope.uploader.queue[i];
								item.url = "console/api/agency/tataApp/tata/" + data.id + "/avatar";
							}
							//var canvas = angular.element(document).find('canvas');
							//var type = item.file.type;
							//pngUrl = canvas[0].toDataURL(type);	// I retrieve the data-url from canvas;
							$scope.uploader.uploadAll();	// here I force the uploading of the image;
				       	}
				    });
					$scope.showNewTataForm = false;
				}
			}
		}
	};
	
	// method updateTata: used to update tata object in db with the data inserted in the specific form 
	$scope.updateTata = function(form, tata){
		$scope.isInit = false;
		if(form.$valid){
			// correctLanguages form bool to string
			tata.languages = [];	// here I clear the array
			for(var i = 0; i < tata.boollanguage.length; i++){
				if(tata.boollanguage[i]){
					switch(i){
						case 0:
							if(!$scope.listContains(tata.languages, $scope.itaLang)){
								tata.languages.push($scope.itaLang);
							}
							break;
						case 1:
							if(!$scope.listContains(tata.languages, $scope.engLang)){
								tata.languages.push($scope.engLang);
							}
							break;
						case 2:
							if(!$scope.listContains(tata.languages, $scope.gerLang)){
								tata.languages.push($scope.gerLang);
							}
							break;
						case 3:
							if(!$scope.listContains(tata.languages, $scope.fraLang)){
								tata.languages.push($scope.fraLang);
							}
							break;
						case 4:
							if(!$scope.listContains(tata.languages, $scope.spaLang)){
								tata.languages.push($scope.spaLang);
							}
							break;	
						default:
							break;
					}
				}
			}
			// here I load the image
	       	var item = null;
			var pngUrl = null;
	       	for(var i = 0; i < $scope.uploader.queue.length; i++){
				item = $scope.uploader.queue[i];
				item.url = "console/api/agency/tataApp/tata/" + tata.id + "/avatar";
			}
	       	if(item && (item.file.size > $scope.maxImgSize)){
				$scope.showMaxImageSizeError = true;
			} else {
				$scope.showMaxImageSizeError = false;
				if(item)$scope.uploader.uploadAll();	// here I force the uploading of the image;
				
				var correctedtata = {
					id: tata.id,
					name: tata.name,
					surname: tata.surname,
					birthdate: $scope.castDateToMillis(tata.birthdate),
					email: tata.email,
					address: tata.address,
					city: tata.city,
					qualification: tata.qualification,
					languages: tata.languages,
					carOwner: tata.carOwner,
					agencyId: $scope.agencyId,
					description: tata.description,
					updates: tata.updates,
					calendarURL : tata.calendarURL
				};
				$scope.corrtata = new Tata(correctedtata);
				
				console.log(JSON.stringify($scope.corrtata));
		        $scope.corrtata.$save({aid: $scope.agencyId}, function() {
		        	console.log('Updated tata');
		        	Tata.list({aid: $scope.agencyId, noCache: new Date().getTime()}, function(data){
		        		 $scope.tatalist = data;
		        	});
		        });
				
				$scope.showEditTataForm = false;
				$scope.showTataDetails = false;
			}
		}
	};
	
	// method deleteTata: used to delete a tata after confirm the operation in a modal dialog
	$scope.deleteTata = function(tata, size){
		var modalInstance = $uibModal.open({
		    animation: $scope.animationsEnabled,
		    templateUrl: 'tataDeleteConfirm.html',
		    controller: 'ModalInstanceCtrl',
		    size: size,
		    resolve: {
		        data: function () {
		            return tata;
		        }
		    }
		});
		modalInstance.result.then(function (tata) {
			Tata.deleteTata({aid: $scope.agencyId, tid: tata.id});
			console.log("Tata " + tata.name + " successfully delected");
			Tata.list({aid: $scope.agencyId, noCache: new Date().getTime()}, function(data){
				$scope.tatalist = data;
			});
	    	$scope.showTataDetails = false;
		}, function () {
			// not deleted - canceled operation
		});
	};
	
	// method listContains: used to check if a list contains a specific element (used for language values)
	$scope.listContains = function(list, item){
		return (-1 !== list.indexOf(item));
	};
	
	// method composeTataImageUrl: used to compose the profile image url dynamically for a specific tata
	$scope.composeTataImageUrl = function(tataId){
		return "console/api/agency/" + $scope.agencyId + "/tata/" + tataId + "/avatar";
	};
	
	// method hideTata: used to hide tata details
	$scope.hideTata = function(){
		$scope.showTataDetails = false;
	};
	
	// method closeNewTataView: used to close the new tata input form without create a new tata
	$scope.closeNewTataView = function(){
		$scope.tata = null;
		$scope.isInit = true;
		$scope.showNewTataForm = false;
	};
	
	// method closeEditTataView: used to close the edit tata input form without edit ad existing tata
	$scope.closeEditTataView = function(){
		$scope.isInit = false;
		$scope.showEditTataForm = false;
		$scope.showTataDetails = true;
	}
	
	// method someSelectedLang: used to check if there some selected languages in the editing form
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
	
	// method castDateToMillis: used to cast a string date in a millisecond value (millis from 1-1-1970)
	$scope.castDateToMillis = function(date){
		//var date = new Date(date + " 00:00:00");
		if(date instanceof Date){
			return date.getTime();
		} else {
			return date;
		}
	};
	
	$scope.correctTextFormatting = function(text){
		var corr = [];
		if(text){
			//= text.replace(new RegExp('\n|\r', 'g'), '<br />');
			corr = text.split('\n');
			return corr;
		} else {
			corr.push("Dato non iserito");
			return corr;
		}
	}
	
	/**
	 * Converts data uri to Blob. Necessary for uploading.
	 * @see
	 *   http://stackoverflow.com/questions/4998908/convert-data-uri-to-file-then-append-to-formdata
	 * @param  {String} dataURI
	 * @return {Blob}
	 */
	$scope.dataURItoBlob = function(dataURI) {
	    var binary = atob(dataURI.split(',')[1]);
	    var mimeString = dataURI.split(',')[0].split(':')[1].split(';')[0];
	    var array = [];
	    for(var i = 0; i < binary.length; i++) {
	        array.push(binary.charCodeAt(i));
	    }
	    return new File([new Blob([new Uint8Array(array)], {type: mimeString})], 'profileAvatar.png', {type: mimeString});
	    //return new File([new Blob([btoa(arrString)], {type: mimeString})], 'profileAvatar.png', {type: mimeString});
	};
	
	
}])

.controller('ModalInstanceCtrl', function ($scope, $uibModalInstance, data) {
	$scope.tataName = data.name;
	$scope.tataSurname = data.surname;
	
	$scope.ok = function () {
		$uibModalInstance.close(data);
	};
	
	$scope.cancel = function () {
		$uibModalInstance.dismiss('cancel');
	};
})

.filter('toLanguageString', function (i18nmessages) {
    return function (input) {
        var defaultLangMessage = "tatapage.view.languagevalue.";
    	var langList = "";
    	if (!input) {
            return "nessuna lingua";
        } else {
        	var itaLang = "IT";
        	var engLang = "EN";
        	var gerLang = "DE";
        	var fraLang = "FR";
        	var spaLang = "ES";
        	var rusLang = "RU";
        	for(var i = 0; i < input.length; i++){
        		switch (input[i]){
        			case itaLang:
        				langList += i18nmessages[defaultLangMessage+"ita"] + ",";
        				break;
        			case engLang:
        				langList += i18nmessages[defaultLangMessage+"eng"] + ",";
        				break;
        			case gerLang:
        				langList += i18nmessages[defaultLangMessage+"ger"] + ",";
        				break;
        			case fraLang:
        				langList += i18nmessages[defaultLangMessage+"fra"] + ",";
        				break;
        			case spaLang:
        				langList += i18nmessages[defaultLangMessage+"spa"] + ",";
        				break;
        			case rusLang:
        				langList += i18nmessages[defaultLangMessage+"rus"] + ",";
        				break;	
        			default:
        				break;
        		}
        	}
        	if(langList.length > 0){
    			langList = langList.substring(0, langList.length - 1);
    		}
        }
        return langList;
    };
})

.factory('Tata', [ '$resource', function($resource) {
	return $resource('console/api/agency/:aid/tata/:tid', {
		aid : '@aid',
		tid: '@tid'
	}, {
		list : {
			isArray : true,
			method : 'get',
			transformResponse : function(data, headers) {
				return JSON.parse(data).content;
			}
		},
		deleteTata : {
			method : 'delete'
		}
	});
} ])

.factory('Avatar', [ '$resource', function($resource) {
	return $resource('console/api/agency/:aid/tata/:tid/avatar', {
		aid : '@aid',
		tid: '@tid',
		responseType: ''
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


.directive('onErrorSrc', function() {
    return {
        link: function(scope, element, attrs) {
          element.bind('error', function() {
            if (attrs.src != attrs.onErrorSrc) {
              attrs.$set('src', attrs.onErrorSrc);
            }
          });
        }
    }
})
.directive('ngThumb', ['$window', function($window) {
    var helper = {
        support: !!($window.FileReader && $window.CanvasRenderingContext2D),
        isFile: function(item) {
            return angular.isObject(item) && item instanceof $window.File;
        },
        isImage: function(file) {
            var type =  '|' + file.type.slice(file.type.lastIndexOf('/') + 1) + '|';
            return '|jpg|png|jpeg|bmp|gif|'.indexOf(type) !== -1;
        }
    };

    return {
        restrict: 'A',
        template: '<canvas/>',
        link: function(scope, element, attributes) {
            if (!helper.support) return;

            var params = scope.$eval(attributes.ngThumb);

            if (!helper.isFile(params.file)) return;
            if (!helper.isImage(params.file)) return;

            var canvas = element.find('canvas');
            var reader = new FileReader();

            reader.onload = onLoadFile;
            reader.readAsDataURL(params.file);

            function onLoadFile(event) {
                var img = new Image();
                img.onload = onLoadImage;
                img.src = event.target.result;
            }

            function onLoadImage() {
                var width = params.width || this.width / this.height * params.height;
                var height = params.height || this.height / this.width * params.width;
                canvas.attr({ width: width, height: height });
                canvas[0].getContext('2d').drawImage(this, 0, 0, width, height);
            }
        }
    };
}]);
