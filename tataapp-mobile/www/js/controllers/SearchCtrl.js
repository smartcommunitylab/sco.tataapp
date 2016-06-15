angular.module('tataapp.controllers.search', [])

.controller('SearchCtrl', function ($scope, $filter, $ionicPopup, $state, $stateParams, ionicDatePicker, Config, Utils, BackendSrv) {
    $scope.dateFormat = Config.dateFormat;
    var now = new Date();
    $scope.allDays = false;

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

    var defaultSearchform = {
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
            sun: false,
            allDays: false
        },
        timeslots: {
            morning: false,
            afternoon: false,
            evening: false,
            night: false
        }
    };

    $scope.searchform = angular.copy(defaultSearchform);

    $scope.changeDoW = function () {
        for (var i in $scope.searchform.days) {
            if (!$scope.searchform.days[i]) {
                $scope.searchform.days.allDays = false;
            }
        }
    };

    $scope.selectAllDoW = function () {
        if (!!$scope.searchform.days.allDays) {
            for (var i in $scope.searchform.days) {
                $scope.searchform.days[i] = true;
            }
        } else {
            for (var i in $scope.searchform.days) {
                $scope.searchform.days[i] = false;
            }
        }
    };

    //    $scope.selectAllDoW();

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
                request.langs.push(lang.toUpperCase());
            }
        });

        request.carOwner = form.car;

        request.days = [];
        angular.forEach(Object.keys(form.days), function (day) {
            if (form.days[day] && 'allDays' != day) {
                request.days.push(day);
            }
        });

        request.timeSlots = [];
        angular.forEach(Object.keys(form.timeslots), function (timeslot) {
            if (form.timeslots[timeslot]) {
                request.timeSlots.push(timeslot);
            }
        });

        request.fromDate = moment($scope.searchform.dateFrom).startOf('date').valueOf();
        request.toDate = moment($scope.searchform.dateTo).endOf('date').valueOf();


        /*
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
        } else if (form.timeSlots.evening) {
            tmpDate.setHours(12);
            request.fromTime = tmpDate.getTime()
            tmpDate.setHours(18);
            request.toTime = tmpDate.getTime();
        } else if (form.timeSlots.night) {
            tmpDate.setHours(12);
            request.fromTime = tmpDate.getTime()
            tmpDate.setHours(18);
            request.toTime = tmpDate.getTime();
        }
        */

        return request;
    };

    $scope.search = function () {
        var request = form2request($scope.searchform);
        console.log(request);

        BackendSrv.searchTate(request).then(
            function (results) {
                console.log(results);

                /*
                $state.go('app.searchsummary', {}, {
                    reload: true
                });
                */
                if (results.content == null) results.content = [];
                results.content.forEach(function (tata) {
                    tata.avatar = Config.getServerURL() + '/api/agency/' + Config.AGENCY_ID + '/tata/' + tata.id + '/avatar';
                });

                $state.go('app.searchresults', {
                    'searchResults': results
                });
            },
            function (reason) {
                console.log(reason);
            }
        );
    };
})

.controller('SearchSummaryCtrl', function ($scope, $state, $stateParams) {
    $scope.seeSearchResultsList = function () {
        $state.go('app.searchresults', {
            'searchResults': $stateParams['searchResults']
        });
    };
})

.controller('SearchResultsCtrl', function ($scope, $state, $stateParams) {
    $scope.nannies = $stateParams['searchResults'].content;

    $scope.seeSearchResult = function (nanny) {
        $state.go('app.nanny', {
            'nannyId': nanny.id,
            'nanny': nanny
        });
    };
});
