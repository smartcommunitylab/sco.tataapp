'use strict';

angular.module('app.tatapoint', [ 'ngRoute', 'ngResource' ])

.config([ '$routeProvider', function($routeProvider) {
	$routeProvider.when('/tatapoint', {
		templateUrl : 'tatapoint/view.html',
		controller : 'TatapointCtrl'
	});
} ])

.controller(
		'TatapointCtrl',
		[ '$rootScope', '$scope','$uibModal', 'Tatapoint',
				function($rootScope, $scope, $uibModal, Tatapoint) {
					$scope.locations = Tatapoint.list();
					
					
					 $scope.openAddDialog = function() {
						    var openDialog = $uibModal.open({
						      templateUrl: 'addTatapoint.html',
						      controller: 'AddModalInstanceCtrl',
						    });

						    openDialog.result.then(
						    function() {
						      console.log("Data submitted");
						    },
						    function() {
						      console.log("Dialog dismiss");
						    });
						  };


				} ])

.controller('AddModalInstanceCtrl',['$scope', '$uibModalInstance','Tatapoint', '$rootScope',function($scope, $uibModalInstance, Tatapoint, $rootScope) {

  $scope.tatapoint = new Tatapoint();
	
  $scope.cancel = function() {
   $uibModalInstance.dismiss();
  };


  $scope.add = function() {
      if(angular.isUndefined($scope.tatapoint.name) || $scope.tatapoint.name.length == 0) {
        $scope.errorMsg = "Il nome del tatapoint e' obbligatorio";
      } else {
      }

      if(($scope.error = angular.isDefined($scope.errorMsg))) {

      } else {
        console.log(JSON.stringify($scope.tatapoint));
        $scope.tatapoint.$save(function() {
        	console.log('Saved tatapoint');
        });
        /*var i = new Item();
        i.name = $scope.item;
        i.quantity = $scope.quantity;
        i.$save(function(){
			console.log("SAVED");
          if($rootScope.items === undefined) {
			$rootScope.items = [];
		  }
		  $rootScope.items.push(i);
        }); */
        $uibModalInstance.close();
      }
  };
 }])

.factory('Tatapoint', [ '$resource', function($resource) {
	return $resource('api/agency/:id/tatapoint/:tid', {
		id : "tataApp"
	}, {
		list : {
			isArray : true,
			method : 'get',
			transformResponse : function(data, headers) {
				return JSON.parse(data).content;
			}
		}
	}

	);
} ]);