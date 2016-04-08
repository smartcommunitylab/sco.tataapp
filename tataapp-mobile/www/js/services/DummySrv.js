angular.module('carpooling.services.dummy', [])

.factory('DummySrv', function ($http, $q) {
    var dummyService = {};

    dummyService.doSomething = function () {
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

    return dummyService;
});
