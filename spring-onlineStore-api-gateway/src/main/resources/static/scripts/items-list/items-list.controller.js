'use strict';

angular.module('itemsList')
    .controller('ItemsListController', ['$http', function ($http) {
        var self = this;

        $http.get('api/inventory/items').then(function (resp) {
            self.items = resp.data;
        });
    }]);
