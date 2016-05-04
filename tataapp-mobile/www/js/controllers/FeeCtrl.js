angular.module('tataapp.controllers.fee', [])

.controller('FeeCtrl', function ($scope, $filter, $ionicPopup, $state, ionicDatePicker, Config, Utils) {
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

    $scope.searchform = {
        food_stamps: false,
        dateFrom: now.getTime(),
        dateTo: now.getTime()
    };


});
