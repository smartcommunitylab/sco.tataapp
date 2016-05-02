angular.module('tataapp.services.backend', [])

.factory('BackendSrv', function ($rootScope, $http, $q, Config) {
    var backend = {};

    /*Get all tate*/
    backend.getAllTate = function (agencyId) {
        var deferred = $q.defer();

        $http.get(ConfigSrv.getServerURL() + '/api/agency/'+agencyId+'/tata', ConfigSrv.getHTTPConfig())

        .then(
            function (response) {
                deferred.resolve(response.data);
            },
            function (responseError) {
                deferred.reject(responseError.data ? responseError.data.errorMessage : responseError);
            }
        );

        return deferred.promise;
    };

    /*Get a single tata*/
    backend.getATata = function (agencyId, babysitterId) {
        var deferred = $q.defer();

        $http.get(ConfigSrv.getServerURL() + '/api/agency/' + agencyId + '/tata/' + babysitterId, ConfigSrv.getHTTPConfig())

        .then(
            function (response) {
                deferred.resolve(response.data);
            },
            function (responseError) {
                deferred.reject(responseError.data ? responseError.data.errorMessage : responseError);
            }
        );

        return deferred.promise;
    };

    /*Save a single tata*/
    backend.saveTata = function (agencyId, tata) {
        var deferred = $q.defer();

        $http.post(ConfigSrv.getServerURL() + '/api/agency/' + agencyId, tata, ConfigSrv.getHTTPConfig())

        .then(
            function (response) {
                deferred.resolve(response.data);
            },
            function (responseError) {
                deferred.reject(responseError.data ? responseError.data.errorMessage : responseError);
            }
        );

        return deferred.promise;
    };

    /*Delete a single tata*/
    backend.deleteTata = function (agencyId, tata) {
        var deferred = $q.defer();

        $http.delete(ConfigSrv.getServerURL() + '/api/agency/' + agencyId + '/tata/' + babysitterId, ConfigSrv.getHTTPConfig())

        .then(
            function (response) {
                deferred.resolve(response.data);
            },
            function (responseError) {
                deferred.reject(responseError.data ? responseError.data.errorMessage : responseError);
            }
        );

        return deferred.promise;
    };

    /*Search tate*/
    backend.deleteTata = function (agencyId, tate) {
        var deferred = $q.defer();

        $http.post(ConfigSrv.getServerURL() + '/api/agency/' + agencyId + '/tata/search', tate, ConfigSrv.getHTTPConfig())

        .then(
            function (response) {
                deferred.resolve(response.data);
            },
            function (responseError) {
                deferred.reject(responseError.data ? responseError.data.errorMessage : responseError);
            }
        );

        return deferred.promise;
    };

    /*Get tariffario*/
    backend.getTariffario = function (agencyId) {
        var deferred = $q.defer();

        $http.get(ConfigSrv.getServerURL() + '/api/agency/' + agencyId + '/pricelist', ConfigSrv.getHTTPConfig())

        .then(
            function (response) {
                deferred.resolve(response.data);
            },
            function (responseError) {
                deferred.reject(responseError.data ? responseError.data.errorMessage : responseError);
            }
        );

        return deferred.promise;
    };

    /*Calcolo preventivo*/
    backend.getPreventivo = function (agencyId, preventivo) {
        var deferred = $q.defer();

        $http.post(ConfigSrv.getServerURL() + '/api/agency/' + agencyId + '/estimation', preventivo, ConfigSrv.getHTTPConfig())

        .then(
            function (response) {
                deferred.resolve(response.data);
            },
            function (responseError) {
                deferred.reject(responseError.data ? responseError.data.errorMessage : responseError);
            }
        );

        return deferred.promise;
    };

    /*Richiesta colloquio*/
    backend.getPreventivo = function (agencyId, colloquio) {
        var deferred = $q.defer();

        $http.post(ConfigSrv.getServerURL() + '/api/agency/' + agencyId + '/meeting', colloquio, ConfigSrv.getHTTPConfig())

        .then(
            function (response) {
                deferred.resolve(response.data);
            },
            function (responseError) {
                deferred.reject(responseError.data ? responseError.data.errorMessage : responseError);
            }
        );

        return deferred.promise;
    };

    /*Get all tatapoint*/
    backend.getAllTatapoint = function (agencyId) {
        var deferred = $q.defer();

        $http.get(ConfigSrv.getServerURL() + '/api/agency/' + agencyId + '/tatapoint', ConfigSrv.getHTTPConfig())

        .then(
            function (response) {
                deferred.resolve(response.data);
            },
            function (responseError) {
                deferred.reject(responseError.data ? responseError.data.errorMessage : responseError);
            }
        );

        return deferred.promise;
    };

    /*Get a tatapoint*/
    backend.getATatapoint = function (agencyId, tatapointId) {
        var deferred = $q.defer();

        $http.get(ConfigSrv.getServerURL() + '/api/agency/' + agencyId + '/tatapoint/', tatapointId, ConfigSrv.getHTTPConfig())

        .then(
            function (response) {
                deferred.resolve(response.data);
            },
            function (responseError) {
                deferred.reject(responseError.data ? responseError.data.errorMessage : responseError);
            }
        );

        return deferred.promise;
    };

    /*Crea un tatapoint*/
    backend.createATatapoint = function (agencyId, tatapoint) {
        var deferred = $q.defer();

        $http.post(ConfigSrv.getServerURL() + '/api/agency/' + agencyId + '/tatapoint', tatapoint, ConfigSrv.getHTTPConfig())

        .then(
            function (response) {
                deferred.resolve(response.data);
            },
            function (responseError) {
                deferred.reject(responseError.data ? responseError.data.errorMessage : responseError);
            }
        );

        return deferred.promise;
    };

    /*Elimina un tatapoint*/
    backend.deleteATatapoint = function (agencyId, tatapointId) {
        var deferred = $q.defer();

        $http.delete(ConfigSrv.getServerURL() + '/api/agency/' + agencyId + '/tatapoint/', tatapointId, ConfigSrv.getHTTPConfig())

        .then(
            function (response) {
                deferred.resolve(response.data);
            },
            function (responseError) {
                deferred.reject(responseError.data ? responseError.data.errorMessage : responseError);
            }
        );

        return deferred.promise;
    };

    return backend;
});
