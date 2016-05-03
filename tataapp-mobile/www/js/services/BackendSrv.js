angular.module('tataapp.services.backend', [])

.factory('BackendSrv', function ($rootScope, $http, $q, Config) {
    var backend = {};

    /* Get all tate */
    backend.getAllTate = function (agencyId) {
        var deferred = $q.defer();

        $http.get(Config.getServerURL() + '/api/agency/' + agencyId + '/tata', Config.getHTTPConfig())

        .then(
            function (response) {
                deferred.resolve(response.data);
            },
            function (reason) {
                deferred.reject(reason.data ? reason.data.errorMessage : reason);
            }
        );

        return deferred.promise;
    };

    /* Get a single tata */
    backend.getATata = function (agencyId, babysitterId) {
        var deferred = $q.defer();

        $http.get(Config.getServerURL() + '/api/agency/' + agencyId + '/tata/' + babysitterId, Config.getHTTPConfig())

        .then(
            function (response) {
                deferred.resolve(response.data);
            },
            function (reason) {
                deferred.reject(reason.data ? reason.data.errorMessage : reason);
            }
        );

        return deferred.promise;
    };

    /* Search tate */
    backend.deleteTata = function (agencyId, tate) {
        var deferred = $q.defer();

        $http.post(Config.getServerURL() + '/api/agency/' + agencyId + '/tata/search', tate, Config.getHTTPConfig())

        .then(
            function (response) {
                deferred.resolve(response.data);
            },
            function (reason) {
                deferred.reject(reason.data ? reason.data.errorMessage : reason);
            }
        );

        return deferred.promise;
    };

    /* Get tariffario */
    backend.getTariffario = function (agencyId) {
        var deferred = $q.defer();

        $http.get(Config.getServerURL() + '/api/agency/' + agencyId + '/pricelist', Config.getHTTPConfig())

        .then(
            function (response) {
                deferred.resolve(response.data);
            },
            function (reason) {
                deferred.reject(reason.data ? reason.data.errorMessage : reason);
            }
        );

        return deferred.promise;
    };

    /* Calcolo preventivo */
    backend.getPreventivo = function (agencyId, preventivo) {
        var deferred = $q.defer();

        $http.post(Config.getServerURL() + '/api/agency/' + agencyId + '/estimation', preventivo, Config.getHTTPConfig())

        .then(
            function (response) {
                deferred.resolve(response.data);
            },
            function (reason) {
                deferred.reject(reason.data ? reason.data.errorMessage : reason);
            }
        );progetto92

        return deferred.promise;
    };

    /* Richiesta colloquio */
    backend.getPreventivo = function (agencyId, colloquio) {
        var deferred = $q.defer();

        $http.post(Config.getServerURL() + '/api/agency/' + agencyId + '/meeting', colloquio, Config.getHTTPConfig())

        .then(
            function (response) {
                deferred.resolve(response.data);
            },
            function (reason) {
                deferred.reject(reason.data ? reason.data.errorMessage : reason);
            }
        );

        return deferred.promise;
    };

    /* Get all tatapoint */
    backend.getAllTatapoint = function (agencyId) {
        var deferred = $q.defer();

        $http.get(Config.getServerURL() + '/api/agency/' + agencyId + '/tatapoint', Config.getHTTPConfig())

        .then(
            function (response) {
                deferred.resolve(response.data);
            },
            function (reason) {
                deferred.reject(reason.data ? reason.data.errorMessage : reason);
            }
        );

        return deferred.promise;
    };

    /* Get a tatapoint */
    backend.getATatapoint = function (agencyId, tatapointId) {
        var deferred = $q.defer();

        $http.get(Config.getServerURL() + '/api/agency/' + agencyId + '/tatapoint/', tatapointId, Config.getHTTPConfig())

        .then(
            function (response) {
                deferred.resolve(response.data);
            },
            function (reason) {
                deferred.reject(reason.data ? reason.data.errorMessage : reason);
            }
        );

        return deferred.promise;
    };

    return backend;
});
