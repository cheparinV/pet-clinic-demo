'use strict';

angular.module('paymentForm', ['ui.router'])
    .config(['$stateProvider', function ($stateProvider) {
        $stateProvider
            .state('paymentNew', {
                parent: 'app',
                url: '/items/:itemId/new-payment',
                template: '<payment-form></payment-form>'
            })
            .state('paymentEdit', {
                parent: 'app',
                url: '/items/:itemId/payments/:paymentId',
                template: '<payment-form></payment-form>'
            })
    }]);
