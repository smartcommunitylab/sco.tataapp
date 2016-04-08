angular.module('tataapp', [
    'ionic',
    'pascalprecht.translate',
    'tataapp.controllers.home'
])

.run(function ($ionicPlatform) {
    $ionicPlatform.ready(function () {
        // Hide the accessory bar by default (remove this to show the accessory bar above the keyboard
        // for form inputs)
        if (window.cordova && window.cordova.plugins.Keyboard) {
            cordova.plugins.Keyboard.hideKeyboardAccessoryBar(true);
            cordova.plugins.Keyboard.disableScroll(true);

        }
        if (window.StatusBar) {
            // org.apache.cordova.statusbar required
            StatusBar.styleDefault();
        }
    });
})

.config(function ($stateProvider, $urlRouterProvider) {
    $stateProvider.state('app', {
        url: '/app',
        abstract: true,
        templateUrl: 'templates/menu.html',
        controller: 'AppCtrl'
    })

    .state('app.home', {
        url: '/home',
        views: {
            'menuContent': {
                templateUrl: 'templates/home.html',
                controller: 'HomeCtrl'
            }
        }
    })

    .state('app.fee', {
        url: '/fee',
        views: {
            'menuContent': {
                templateUrl: 'templates/fee.html'
            }
        }
    })

    .state('app.vouchers', {
        url: '/vouchers',
        views: {
            'menuContent': {
                templateUrl: 'templates/vouchers.html'
            }
        }
    });

    /*
    .state('app.single', {
        url: '/playlists/:playlistId',
        views: {
            'menuContent': {
                templateUrl: 'templates/playlist.html',
                controller: 'PlaylistCtrl'
            }
        }
    });
    */

    // if none of the above states are matched, use this as the fallback
    $urlRouterProvider.otherwise('/app/home');
})

.config(function ($translateProvider) {
    $translateProvider.translations('it', {
        app_name: 'Tata APP',
        cancel: 'Annulla',
        yes: 'Sì',
        no: 'No',
        ok: 'OK',
        add: 'Aggiungi',
        edit: 'Modifica',
        menu_home: 'Home',
        menu_search: 'Cerca Tata',
        menu_meet: 'Richiesta colloquio',
        menu_fee: 'Quanto costa',
        menu_how: 'Come funziona il servizio',
        menu_vouchers: 'Buoni di servizio PAT',
        menu_faq: 'FAQ',
        btn_info: 'Richiedi informazioni',
        btn_estimate: 'Calcola un preventivo',
        mail_ask4info1: 'Richiesta informazioni',
        mail_ask4info2: 'Lorem ipsum dolor sit amet, ne dictas causae eam',
        text_fee1: 'La tariffa oraria del nostro servizio dipende da diversi fattori, quali la durata del contratto, le ore settimanali di lavoro, la possibilità di usufruire dei buoni di servizio.',
        text_fee2: 'La tariffa massima è di 14,52€/h (IVA compresa) onnicomprensiva e viene moltiplicata esclusivamente per le ore effettivamente lavorate.',
        text_fee3: 'Sono previsti sconti alla tariffa a partire da 16 ore settimanali. Hai diritto ai buoni di servizio? Questa possibilità ti permette di risparmiare da 3,50 a 5,50€/h.',
        text_vouchers1: 'I Buoni di servizio sono titoli di spesa per la conciliazione tra impegno lavorativo e cura in ambito familiare erogati dalla Provincia autonoma di Trento mediante graduatorie periodiche e rilasciati dalla Struttura Multifunzionale Territoriale Ad Personam. I Buoni di servizio consentono ai titolari di acquisire, a fronte di un contributo finanziario personale pari ad almeno il 15% o 20% del valore nominale del buono (in funzione dell’età del minore),  servizi di educazione e cura di minori con età fino a 14 anni (18 anni nel caso di minori portatori di handicap certificati ex L. n. 104/1992 o con difficoltà di apprendimento o situazioni di particolare disagio attestate da personale di competenza) in forma complementare ai servizi pubblici erogati allo stesso titolo sul territorio provinciale.',
        text_vouchers_source: 'Fonte'
    });

    $translateProvider.preferredLanguage('it');
    $translateProvider.useSanitizeValueStrategy('escape');
});
