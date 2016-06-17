angular.module('tataapp.services.backend', [])

.factory('BackendSrv', function ($rootScope, $http, $q, Config) {
    var backend = {};

    /* Get all tate */
    backend.getAllTate = function () {
        var deferred = $q.defer();

        $http.get(Config.getServerURL() + '/api/agency/' + Config.AGENCY_ID + '/tata', Config.getHTTPConfig())

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
    backend.getATata = function (babysitterId) {
        var deferred = $q.defer();

        $http.get(Config.getServerURL() + '/api/agency/' + Config.AGENCY_ID + '/tata/' + babysitterId, Config.getHTTPConfig())

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

    /* Get a single tata avatar */
    backend.getTataAvatar = function (babysitterId) {
        var deferred = $q.defer();

        $http.get(Config.getServerURL() + '/api/agency/' + Config.AGENCY_ID + '/tata/' + babysitterId + '/avatar', Config.getHTTPConfig())

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
    backend.searchTate = function (request) {
        var deferred = $q.defer();

        $http.post(Config.getServerURL() + '/api/agency/' + Config.AGENCY_ID + '/tata/search', request, Config.getHTTPConfig())

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
    backend.getPricelist = function () {
        var deferred = $q.defer();

        $http.get(Config.getServerURL() + '/api/agency/' + Config.AGENCY_ID + '/pricelist', Config.getHTTPConfig())

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

    /* Get vouchers values */
    backend.getVouchersList = function () {
        var deferred = $q.defer();

        $http.get(Config.getServerURL() + '/api/agency/' + Config.AGENCY_ID + '/vouchers', Config.getHTTPConfig())

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
    backend.getEstimation = function (preventivo) {
        var deferred = $q.defer();

        $http.post(Config.getServerURL() + '/api/agency/' + Config.AGENCY_ID + '/estimation', preventivo, Config.getHTTPConfig())

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

    /* Richiesta colloquio */
    backend.requestMeeting = function (request) {
        var deferred = $q.defer();

        $http.post(Config.getServerURL() + '/api/agency/' + Config.AGENCY_ID + '/meeting', request, Config.getHTTPConfig())

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
    backend.getAllTatapoint = function () {
        var deferred = $q.defer();

        $http.get(Config.getServerURL() + '/api/agency/' + Config.AGENCY_ID + '/tatapoint', Config.getHTTPConfig())

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
    backend.getATatapoint = function (tatapointId) {
        var deferred = $q.defer();

        $http.get(Config.getServerURL() + '/api/agency/' + Config.AGENCY_ID + '/tatapoint/', tatapointId, Config.getHTTPConfig())

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

    /* Get zones */
    backend.getZones = function (tatapointId) {
        var deferred = $q.defer();

        $http.get(Config.getServerURL() + '/api/agency/' + Config.AGENCY_ID + '/office', Config.getHTTPConfig())

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
