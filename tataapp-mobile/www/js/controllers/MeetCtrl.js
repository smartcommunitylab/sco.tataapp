angular.module('tataapp.controllers.meet', [])

.controller('MeetCtrl', function ($scope, $state, $filter, $ionicPopup, $ionicScrollDelegate, Config, BackendSrv) {
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

    /*
    Representive familyRepresentive;
		String email;
		String phone;
		String name;
		String surname;
		String city;
	List<Child> children;
		int age;
		boolean disability;
	String babysitterId;
	long creationTs;
	String agencyId;
    */

    var form2request = function (form) {
        var request = {
            agencyId: Config.AGENCY_ID,
            familyRepresentive: {
                email: form.email,
                phone: form.phone,
                name: form.name,
                surname: form.surname,
                city: form.municipality
            },
            children: form.children
        };

        return request;
    };

    var showPersonalDataPopup = function () {
        return $ionicPopup.confirm({
            title: $filter('translate')('popup_personaldata_title'), // String. The title of the popup.
            cssClass: '',
            template: $filter('translate')('popup_personaldata_content'),
            cancelText: $filter('translate')('deny'),
            cancelType: 'button-stable', // (default: 'button-default')
            okText: $filter('translate')('allow'),
            okType: 'button-positive' // (default: 'button-positive')
        });
    };

    var showSentPopup = function () {
        return $ionicPopup.alert({
            title: $filter('translate')('popup_requestsent_title'), // String. The title of the popup.
            cssClass: '',
            template: $filter('translate')('popup_requestsent_content'),
            okText: $filter('translate')('back_home'),
            okType: 'button-positive' // (default: 'button-positive')
        });
    };

    $scope.send = function () {
        showPersonalDataPopup().then(function (ok) {
            if (ok) {
                var request = form2request($scope.meetform);
                console.log(JSON.stringify(request));
                BackendSrv.requestMeeting(request).then(
                    function (response) {
                        showSentPopup().then(function () {
                            $state.go('app.home', {}, {
                                reload: true
                            });
                        });
                    },
                    function (reason) {
                        // TODO handle error
                    }
                );
            } else {
                console.log('Auth denied');
            }
        });
    };
});
