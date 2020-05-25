'use strict';

angular.module('itemDetails', ['ui.router'])
    .config(['$stateProvider', function ($stateProvider) {
        $stateProvider
            .state('itemDetails', {
                parent: 'app',
                url: '/items/details/:itemId',
                template: '<item-details></item-details>'
            })
    }]);
