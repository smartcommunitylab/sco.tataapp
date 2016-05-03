angular.module('tataapp.controllers.meet', [])

.controller('MeetCtrl', function ($scope, $filter, $ionicPopup, $ionicScrollDelegate) {
    var child = {
        age: 0,
        disability: false
    };

    $scope.meetform = {
        surname: '',
        name: '',
        municipality: '',
        phone: '',
        email: '',
        children: [angular.copy(child)]
    };

    $scope.addChild = function () {
        $scope.meetform.children.push(angular.copy(child));
        $ionicScrollDelegate.resize();
        $ionicScrollDelegate.scrollBottom(true);
    };

    $scope.removeChild = function () {
        $scope.meetform.children.pop();
        $ionicScrollDelegate.resize();
        $ionicScrollDelegate.scrollBottom(true);
    };

    $scope.makeOlder = function (child) {
        child.age++;
    };

    $scope.makeYounger = function (child) {
        child.age--;
    };



    $scope.showPersonalDataPopup = function () {
        var popup = $ionicPopup.confirm({
            title: $filter('translate')('popup_personaldata_title'), // String. The title of the popup.
            cssClass: '',
            template: $filter('translate')('popup_personaldata_content'),
            cancelText: $filter('translate')('deny'),
            cancelType: 'button-stable', // (default: 'button-default')
            okText: $filter('translate')('allow'),
            okType: 'button-positive' // (default: 'button-positive')
        });

        popup.then(function (ok) {
            if (ok) {
                console.log('Auth allowed');
            } else {
                console.log('Auth denied');
            }
        });
    };

    /*
    $scope.meetform = {
        surname: '',
        name: '',
        municipality: '',
        phone: '',
        email: '',
        children: [angular.copy(child)]
    };
    */

    /*
    Representive familyRepresentive;
		String email;
		String phone;
		String name;
		String surname;
		String city;
	List<Child> children;
		long birthDate;
		boolean disability;
	String babysitterId;
	long creationTs;
	String agencyId;
    */

    var form2request = function (form) {
        var request = {
            familyRepresentive: {
                email: form.email,
                phone: form.phone,
                name: form.name,
                surname: form.surname,
                city: form.municipality
            },
            children: []
        };

        angular.forEach(form.children, function (child) {
            // TODO
        });

        return request;
    };

    $scope.send = function () {
        $scope.popupPersonalData();
    };
});
