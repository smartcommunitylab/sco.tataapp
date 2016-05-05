angular.module('tataapp.services.config', [])

.factory('Config', function ($http, $q) {
    var configService = {};

    configService.AGENCY_ID = 'progetto92';

    configService.SERVER_URL = 'https://dev.smartcommunitylab.it/tataapp';

    configService.dateFormat = 'dd MMMM yyyy';

    configService.timeFormat = 'HH:mm';

    configService.dateFormatNum = 'dd/MM/yyyy';

    configService.dateFormatMonth = 'MMMM';

    configService.HTTP_CONFIG = {
        timeout: 5000,
        headers: {
            'Content-Type': 'application/json'
        }
    };

    configService.getServerURL = function () {
        return this.SERVER_URL;
    };

    configService.getHTTPConfig = function () {
        return angular.copy(this.HTTP_CONFIG);
    };

    return configService;
});
