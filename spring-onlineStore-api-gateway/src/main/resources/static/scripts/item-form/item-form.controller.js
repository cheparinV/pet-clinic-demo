'use strict';

angular.module('itemForm')
    .controller('ItemFormController', ["$http", '$state', '$stateParams', function ($http, $state, $stateParams) {
        var self = this;

        var itemId = $stateParams.itemId || 0;

        if (!itemId) {
            self.item = {};
        } else {
            $http.get("api/inventory/items/" + itemId).then(function (resp) {
                self.item = resp.data;
            });
        }

        self.submitItemForm = function () {
            var id = self.item.id;
            var req;
            if (id) {
                req = $http.put("api/inventory/items/" + id, self.item);
            } else {
                req = $http.post("api/inventory/items", self.item);
            }

            req.then(function () {
                $state.go('items');
            }, function (response) {
                var error = response.data;
                alert(error.error + "\r\n" + error.errors.map(function (e) {
                        return e.field + ": " + e.defaultMessage;
                    }).join("\r\n"));
            });
        };
    }]);
