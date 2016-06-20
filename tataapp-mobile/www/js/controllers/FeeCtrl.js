angular.module('tataapp.controllers.fee', [])

.controller('FeeCtrl', function ($scope, BackendSrv) {
    $scope.pricelist = {};

    $scope.d2d = function (num) {
        return parseFloat(Math.round(num * 100) / 100).toFixed(2);
    };

    BackendSrv.getPricelist().then(
        function (pricelist) {
            $scope.pricelist = pricelist;
        }
    );

    BackendSrv.getVouchersList().then(
        function (vouchersList) {
            $scope.vouchersList = vouchersList;
        }
    );
})

.controller('FeeEstimateCtrl', function ($scope, $state, $filter, $ionicPopup, $ionicScrollDelegate, ionicDatePicker, Config, Utils, BackendSrv) {
    var minDurationMonths = 3;
    $scope.dateFormat = Config.dateFormat;
    var now = new Date();
    var nowMinTo = angular.copy(now);
    nowMinTo.setMonth(nowMinTo.getMonth() + minDurationMonths);

    $scope.estimateform = {
        dateFrom: now.getTime(),
        dateTo: nowMinTo.getTime(),
        disability: false,
        bonusAssignee: false,
        bonusType: 'type1',
        weeklyHour: null,
        types: {
            'type1': $filter('translate')('type1'),
            'type2': $filter('translate')('type2'),
            'type3': $filter('translate')('type3')
        }
    };
    //'type3': $filter('translate')('type3') + ' *'

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
            dpo.inputDate = new Date($scope.estimateform.dateFrom);
            dpo.callback = function (val) {
                $scope.estimateform.dateFrom = val;
                var minDateTo = new Date($scope.estimateform.dateFrom);
                minDateTo.setMonth(minDateTo.getMonth() + minDurationMonths);
                if ($scope.estimateform.dateTo < minDateTo.getTime()) {
                    $scope.estimateform.dateTo = minDateTo.getTime();
                }
            };
        } else if (field === 'to') {
            dpo.inputDate = new Date($scope.estimateform.dateTo);
            dpo.callback = function (val) {
                $scope.estimateform.dateTo = val;
                var minDateTo = new Date();
                minDateTo.setMonth(minDateTo.getMonth() + minDurationMonths);
                if ($scope.estimateform.dateTo < minDateTo.getTime()) {
                    /*
                    $scope.estimateform.dateFrom = now;
                    $scope.estimateform.dateTo = minDateTo.getTime();
                    */

                    $ionicPopup.alert({
                        title: $filter('translate')('popup_title_attention', 'uppercase'), // String. The title of the popup.
                        template: $filter('translate')('popup_fee_minduration'),
                        okText: $filter('translate')('ok'),
                        okType: 'button-positive' // (default: 'button-positive')
                    }).then(function () {
                        var minDateTo = new Date($scope.estimateform.dateFrom);
                        minDateTo.setMonth(minDateTo.getMonth() + minDurationMonths);
                        $scope.estimateform.dateTo = minDateTo.getTime();
                    });
                }
            };
        }

        ionicDatePicker.openDatePicker(dpo);
    };

    var showSentPopup = function () {
        return $ionicPopup.alert({
            title: $filter('translate')('popup_title_attention', 'uppercase'), // String. The title of the popup.
            template: $filter('translate')('popup_fee_attention'),
            okText: $filter('translate')('popup_button_understand'),
            okType: 'button-positive' // (default: 'button-positive')
        });
    };

    var form2request = function (form) {
        // this is how to convert estimateform to search request
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
        request.disability = form.disability;
        request.bonusAssignee = form.bonusAssignee;
        request.bonusType = form.bonusType;
        request.weeklyHour = form.weeklyHour;

        return request;
    };

    $scope.estimate = function () {
        $scope.estimation = 0;

        showSentPopup().then(
            function () {
                var request = form2request($scope.estimateform);

                BackendSrv.getEstimation(request).then(
                    function (results) {
                        $scope.estimation = {
                            bonusRate: Math.round(results.bonusRate * 100) / 100,
                            estimation: Math.round(results.estimation * 100) / 100,
                            totalRate: Math.round(results.totalRate * 100) / 100
                        };
                        $ionicScrollDelegate.resize();
                        $ionicScrollDelegate.scrollBottom(true);
                    },
                    function (reason) {
                        console.log(reason);
                    }
                );
            }
        );
    };

});
