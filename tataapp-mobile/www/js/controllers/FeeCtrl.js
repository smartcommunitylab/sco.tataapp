angular.module('tataapp.controllers.fee', [])

.controller('FeeCtrl', function ($scope, $filter, $ionicPopup, $state, ionicDatePicker, Config, Utils, BackendSrv) {
    $scope.dateFormat = Config.dateFormat;
    var now = new Date();

    var datePickerOptions = {
        setLabel: $filter('translate')('set'),
        todayLabel: $filter('translate')('today'),
        closeLabel: $filter('translate')('close'),
        mondayFirst: true,
        weeksList: Utils.getWeeksList(),
        monthsList: Utils.getMonthsList(),
        templateType: 'popup', // or 'popup
        from: new Date(),
        showTodayButton: true,
        dateFormat: Config.dateFormat,
        closeOnSelect: false
    };

    $scope.pickDate = function (field) {
        var dpo = angular.copy(datePickerOptions);

        if (field === 'from') {
            dpo.inputDate = new Date($scope.searchform.dateFrom);
            dpo.callback = function (val) {
                $scope.searchform.dateFrom = val;
                if ($scope.searchform.dateTo < val) {
                    $scope.searchform.dateTo = val;
                }
            };
        } else if (field === 'to') {
            dpo.inputDate = new Date($scope.searchform.dateTo);
            dpo.callback = function (val) {
                $scope.searchform.dateTo = val;
                if ($scope.searchform.dateTo < $scope.searchform.dateFrom) {
                    $scope.searchform.dateFrom = val;
                }
            };
        }

        ionicDatePicker.openDatePicker(dpo);
    };

    var showSentPopup = function () {
        return $ionicPopup.alert({
            title: $filter('translate')('popup_title_attention','uppercase'), // String. The title of the popup.
            cssClass: 'attentionPopup',
            template: $filter('translate')('popup_fee_attention'),
            okText: $filter('translate')('popup_button_understand'),
            okType: 'button-positive' // (default: 'button-positive')
        });
    };

    $scope.searchform = {
        dateFrom: now.getTime(),
        dateTo: now.getTime(),
        bonusAssignee: false,
        bonusType: null,
        weeklyHour: null,
        types: {
            'type1': $filter('translate')('type1'),
            'type2': $filter('translate')('type2'),
            'type3': $filter('translate')('type3')
        }
    };

    var form2request = function (form) {
        // this is how to convert searchform to search request
        /*
        long startDate;
        long endDate;
        boolean bonusAssignee;
        String bonusType;
        double weeklyHour;
        */
        var request = {};

        request.startDate = form.dateFrom;
        request.endDate = form.dateTo;
        request.bonusAssignee = form.bonusAssignee;
        request.bonusType = form.bonusType;
        request.weeklyHour = form.weeklyHour;

        return request;
    };

    $scope.search = function () {
        showSentPopup();
        var request = form2request($scope.searchform);
        console.log(request);
        BackendSrv.getPreventivo(request).then(
            function (results) {
                console.log(results);
            },
            function (reason) {
                console.log(reason);
            }
        );
    };

});
