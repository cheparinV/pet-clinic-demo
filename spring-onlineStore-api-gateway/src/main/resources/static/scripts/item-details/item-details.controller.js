'use strict';

angular.module('itemDetails')
    .controller('ItemsDetailsController', ['$http', '$stateParams', function ($http, $stateParams) {
        var self = this;

        $http.get('api/gateway/items/' + $stateParams.itemId).then(function (resp) {
            self.item = resp.data;
        });
    }]);
