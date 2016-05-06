angular.module('tataapp.services.utils', [])

.factory('Utils', function ($http, $q, $filter) {
    var utilsService = {};

    /* localStorage */
    utilsService.saveToLocalStorage = function (key, value) {
        localStorage[key] = JSON.stringify(value);
        return localStorage[key];
    };

    utilsService.getFromLocalStorage = function (key) {
        if (!!localStorage[key]) {
            return JSON.parse(localStorage[key]);
        }
        return null;
    };

    /*
     * Date utils
     */
    utilsService.getWeeksList = function () {
        return [$filter('translate')('date_weekslist_sunday'), $filter('translate')('date_weekslist_monday'), $filter('translate')('date_weekslist_tuesday'), $filter('translate')('date_weekslist_wednesday'), $filter('translate')('date_weekslist_thursday'), $filter('translate')('date_weekslist_friday'), $filter('translate')('date_weekslist_saturday')];
    };

    utilsService.getMonthsList = function () {
        return [$filter('translate')('date_monthslist_january'), $filter('translate')('date_monthslist_february'), $filter('translate')('date_monthslist_march'), $filter('translate')('date_monthslist_april'), $filter('translate')('date_monthslist_may'), $filter('translate')('date_monthslist_june'), $filter('translate')('date_monthslist_july'), $filter('translate')('date_monthslist_august'), $filter('translate')('date_monthslist_september'), $filter('translate')('date_monthslist_october'), $filter('translate')('date_monthslist_november'), $filter('translate')('date_monthslist_december')];
    };

    utilsService.doSomething = function () {
        var deferred = $q.defer();

        /*
        $http.get(url, httpConfig)

        .then(
            function (response) {
                deferred.resolve(response);
            },
            function (reason) {
                deferred.reject(reason);
            }
        );
        */

        return deferred.promise;
    };

    return utilsService;
});
