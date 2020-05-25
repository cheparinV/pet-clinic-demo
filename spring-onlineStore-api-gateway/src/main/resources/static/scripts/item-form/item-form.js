'use strict';

angular.module('itemForm', ['ui.router'])
    .config(['$stateProvider', function ($stateProvider) {
        $stateProvider
            .state('itemEdit', {
                parent: 'app',
                url: '/items/:itemId/edit',
                template: '<item-form></item-form>'
            })
    }]);
