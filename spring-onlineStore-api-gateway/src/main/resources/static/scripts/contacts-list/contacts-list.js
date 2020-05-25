'use strict';

angular.module('contactsList', ['ui.router'])
    .config(['$stateProvider', function ($stateProvider) {
        $stateProvider
            .state('contacts', {
                parent: 'app',
                url: '/contacts',
                template: '<contacts-list></contacts-list>'
            })
    }]);
