angular.module('tataapp.services.config', [])

.factory('Config', function ($http, $q) {
    var configService = {};

    configService.dateFormat = 'dd MMMM yyyy';

    return configService;
});
