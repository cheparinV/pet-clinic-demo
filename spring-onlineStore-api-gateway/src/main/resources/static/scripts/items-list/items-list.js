'use strict';

angular.module('itemsList', ['ui.router'])
    .config(['$stateProvider', function ($stateProvider) {
        $stateProvider
            .state('items', {
                parent: 'app',
                url: '/items',
                template: '<items-list></items-list>'
            })
    }]);
