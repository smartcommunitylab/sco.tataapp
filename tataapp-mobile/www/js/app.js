angular.module('tataapp', [
    'ionic',
    'ionic.wizard',
    'ngCordova',
    'ngSanitize',
    'pascalprecht.translate',
    'tataapp.controllers.home',
    'tataapp.controllers.search',
    'tataapp.controllers.meet',
    'tataapp.controllers.fee',
    'tataapp.controllers.faq'
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

    .state('app.search', {
        url: '/search',
        cache: false,
        views: {
            'menuContent': {
                templateUrl: 'templates/search.html',
                controller: 'SearchCtrl'
            }
        }
    })

    .state('app.searchsummary', {
        url: '/search/summary',
        views: {
            'menuContent': {
                templateUrl: 'templates/searchsummary.html',
                controller: 'SearchSummaryCtrl'
            }
        }
    })

    .state('app.searchresults', {
        url: '/search/results',
        views: {
            'menuContent': {
                templateUrl: 'templates/searchresults.html',
                controller: 'SearchResultsCtrl'
            }
        }
    })

    .state('app.meet', {
        url: '/meet',
        views: {
            'menuContent': {
                templateUrl: 'templates/meet.html',
                controller: 'MeetCtrl'
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

    .state('app.fee_estimate', {
        url: '/fee/estimate',
        views: {
            'menuContent': {
                templateUrl: 'templates/fee_estimate.html',
                controller: 'FeeCtrl'
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
    })

    .state('app.faq', {
        url: '/faq',
        views: {
            'menuContent': {
                templateUrl: 'templates/faq.html',
                controller: 'FaqCtrl'
            }
        }
    });;

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
        previous: 'Indietro',
        next: 'Avanti',
        send: 'Invia',
        search: 'Cerca',
        deny: 'Nego',
        allow: 'Autorizzo',
        menu_home: 'Home',
        menu_search: 'Cerca Tata',
        menu_meet: 'Richiesta colloquio',
        menu_fee: 'Quanto costa',
        menu_how: 'Come funziona il servizio',
        menu_vouchers: 'Buoni di servizio PAT',
        menu_faq: 'FAQ',
        btn_info: 'Richiedi informazioni',
        btn_estimate: 'Calcola un preventivo',
        btn_estimatefee: 'Calcola',
        btn_meet: 'Richiedi un colloquio',
        popup_personaldata_title: 'Autorizzazione',
        popup_personaldata_content: 'Autorizzo il trattamento dei miei dati personali al sensi del D.lgs. 196 del 30 giugno 2003.',
        popup_search_when_reset_title: 'Elimina selezione',
        popup_search_when_reset_content: 'Desideri eliminare i giorni selezionati e le eventuali impostazioni di orario definite?',
        title_searchresults: 'Risultati ricerca',
        title_nannies: 'Lista Tate',
        title_fee: 'Calcola preventivo',
        mail_ask4info1: 'Richiesta informazioni',
        mail_ask4info2: 'Lorem ipsum dolor sit amet, ne dictas causae eam',
        text_searchresults1: 'Il servizio TataAPP ha a disposizione',
        text_searchresults2: 'che rispetta i requisiti da te richiesti',
        text_searchresults2n: 'che rispettano i requisiti da te richiesti',
        text_fee1: 'La tariffa oraria del nostro servizio dipende da diversi fattori, quali la durata del contratto, le ore settimanali di lavoro, la possibilità di usufruire dei buoni di servizio.',
        text_fee2: 'La tariffa massima è di 14,52€/h (IVA compresa) onnicomprensiva e viene moltiplicata esclusivamente per le ore effettivamente lavorate.',
        text_fee3: 'Sono previsti sconti alla tariffa a partire da 16 ore settimanali. Hai diritto ai buoni di servizio? Questa possibilità ti permette di risparmiare da 3,50 a 5,50€/h.',
        text_vouchers1: 'I Buoni di servizio sono titoli di spesa per la conciliazione tra impegno lavorativo e cura in ambito familiare erogati dalla Provincia autonoma di Trento mediante graduatorie periodiche e rilasciati dalla Struttura Multifunzionale Territoriale Ad Personam. I Buoni di servizio consentono ai titolari di acquisire, a fronte di un contributo finanziario personale pari ad almeno il 15% o 20% del valore nominale del buono (in funzione dell\'età del minore),  servizi di educazione e cura di minori con età fino a 14 anni (18 anni nel caso di minori portatori di handicap certificati ex L. n. 104/1992 o con difficoltà di apprendimento o situazioni di particolare disagio attestate da personale di competenza) in forma complementare ai servizi pubblici erogati allo stesso titolo sul territorio provinciale.',
        text_vouchers_source: 'Fonte',
        faq_1_q: 'È previsto un periodo di prova?',
        faq_1_a: '<p>Viene definito come da CCNL per la Categoria delle Imprese Fornitrici di Lavoro Temporaneo in 1 giorno di effettiva prestazione per ogni 15 giorni di calendario. In ogni caso il periodo di prova non potrà essere inferiore a 2 giorni e superiore a 10.</p>',
        faq_2_q: 'La tata è assicurata?',
        faq_2_a: '<p>Cooperjob occupa le lavoratrici/ori solo con contratti di lavoro regolari: ogni persona ha stipendio e orario di lavoro regolamentato dal contratto di lavoro collettivo nazionale ed è assicurata presso INPS e INAIL, riceve i contributi per una futura pensione ed è garantita nei periodi di malattia, di maternità o in caso di infortunio.</p>',
        faq_3_q: 'Parte del costo sostenuto per la tata è deducibile?',
        faq_3_a: '<p>Il familiare che sostiene la spesa può dedurre dal proprio reddito (deduzione inps) i contributi previdenziali INPS versati per un importo massimo di 1.549,37 euro.</p><p>Il costo del servizio è detraibile fiscalmente per la famiglia che ha diritto alla detrazione del 19% per le spese sostenute (retribuzione della lavoratrice) calcolata su un importo massimo di 2100 euro a patto che il proprio reddito complessivo lordo non superi i 40.000 euro ( la detrazione è possibile solo se presenti patologie certificate).</p><p>A questo scopo Cooperjob rilascia una dichiarazione dove attesta l\'importo dei contributi previdenziali versati per la baby sitter e l\'importo delle retribuzioni corrisposte al lavoratore nell\'anno. Per quelli che usufruiscono del Buono Servizio, il costo detraibile è calcolato sull\'importo delle retribuzioni corrisposte alla lavoratrice solo ricomprese nella quota di spesa a carico della famiglia.</p>',
        faq_4_q: 'Quali compiti può svolgere la tata?',
        faq_4_a: '<p>Il compito prioritario che viene assegnato alla tata è la cura e custodia dei propri bambini/ragazzi. In questo è compresa la preparazione dei pasti, secondo le indicazioni che verranno fornite dalla famiglia. Il contratto prevede anche lo svolgimento di piccoli lavori domestici, in particolare il riassetto della cucina dopo i pasti, e il riordino e la pulizia della camera del/dei bambini.</p>',
        faq_5_q: 'Può somministrare farmaci?',
        faq_5_a: '<p>Se il bambino è ammalato e occorre somministrare farmaci la famiglia deve istruire adeguatamente la tata compilare e fornire un’autorizzazione alla somministrazione.</p>',
        faq_6_q: 'La tata può portare i bambini in macchina?',
        faq_6_a: '<p>Incentiviamo i mezzi pubblici, ma per particolari esigenze (tragitti casa/scuola, accompagnamento ad impegni extra scolastici...) la tata (se è disponibile a mettere a disposizione la propria auto e viene fornita di appositi seggiolini per i bambini) può trasportare i bambini. È dovuto un rimborso chilometrico.</p>',
        faq_7_q: 'Se la tata si ammala?',
        faq_7_a: '<p>Si può chiedere una sostituzione.</p>',
        faq_8_q: 'Se ho bisogno di ore in più?',
        faq_8_a: '<p>Nessun problema, si concordano con la tata e si segnano a registro ore. Il costo orario resta invariato (salvo ore in fascia serale dopo le 22).</p>'
    });

    $translateProvider.preferredLanguage('it');
    $translateProvider.useSanitizeValueStrategy('sanitize');
});
