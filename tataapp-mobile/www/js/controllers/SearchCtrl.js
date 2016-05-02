angular.module('tataapp.controllers.search', [])

.controller('SearchCtrl', function ($scope, $filter, $ionicPopup, $state, ionicDatePicker, Config, Utils) {
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

    $scope.ageranges = [
        {
            label: $filter('translate')('form_agerange_whatever'),
            value: ''
        },
        {
            label: '20-30',
            value: '20-30'
        },
        {
            label: '30-40',
            value: '30-40'
        },
        {
            label: '40+',
            value: '40+'
        }
    ];

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
        agerange: '',
        languages: {
            it: false,
            en: false,
            de: false,
            other: ''
        },
        pickup: false,
        cook: false,
        homework: false,
        car: false,
        dateFrom: now.getTime(),
        dateTo: now.getTime(),
        days: {
            mon: false,
            tue: false,
            wed: false,
            thu: false,
            fri: false,
            sat: false,
            sun: false
        },
        timeSlots: {
            morning: false,
            afternoon: false
        }
    };

    var form2request = function (form) {
        // TODO this is how to convert searchform to search request
        /*
        List<String> langs;
        String rangeAge;
        boolean carOwner;
        long fromDate;
        long toDate;
        long fromTime;
        long toTime;
        String[] days;
        String agencyId;
        */
        var request = {};

        request.rangeAge = form.agerange;

        request.langs = [];
        angular.forEach(Object.keys(form.languages), function (lang) {
            if (form.languages[lang]) {
                request.langs.push(lang);
            }
        });

        request.carOwner = form.car;
        request.fromDate = form.dateFrom;
        request.toDate = form.dateTo;

        request.days = [];
        angular.forEach(Object.keys(form.days), function (day) {
            if (form.days[day]) {
                request.days.push(day);
            }
        });

        request.fromTime = 0;
        request.toTime = 0;
        var tmpDate = new Date();
        tmpDate.setMinutes(0);
        //tmpDate.setSeconds(0);
        //tmpDate.setMilliseconds(0);
        if (form.timeSlots.morning && form.timeSlots.afternoon) {
            tmpDate.setHours(8);
            request.fromTime = tmpDate.getTime()
            tmpDate.setHours(18);
            request.toTime = tmpDate.getTime();
        } else if (form.timeSlots.morning) {
            tmpDate.setHours(8);
            request.fromTime = tmpDate.getTime()
            tmpDate.setHours(12);
            request.toTime = tmpDate.getTime();
        } else if (form.timeSlots.afternoon) {
            tmpDate.setHours(12);
            request.fromTime = tmpDate.getTime()
            tmpDate.setHours(18);
            request.toTime = tmpDate.getTime();
        }

        return request;
    };

    $scope.search = function () {
        var request = form2request($scope.searchform);
        console.log(request);

        $state.go('app.searchsummary', {}, {
            reload: true
        });
    };

    /*
    $scope.$watch(function () {
        return $scope.searchform;
    }, function (newValue, oldValue) {
        console.log(newValue);
    }, true);
    */
})

.controller('SearchSummaryCtrl', function ($scope, $state) {
    $scope.seeSearchResultsList = function () {
        $state.go('app.searchresults', {}, {
            reload: true
        });
    };

    $scope.dummy = function (oh) {
        console.log(oh);
    };
})

.controller('SearchResultsCtrl', function ($scope) {
    $scope.dummy = function (oh) {
        console.log(oh);
    };
});
