'use strict';

angular.module('paymentForm')
    .controller('PaymentFormController', ['$http', '$state', '$stateParams', function ($http, $state, $stateParams) {
        var self = this;
        var itemId = $stateParams.itemId || 0;

        $http.get('api/inventory/petTypes').then(function (resp) {
            self.types = resp.data;
        }).then(function () {

            var petId = $stateParams.petId || 0;

            if (petId) { // edit
                $http.get("api/inventory/items/" + itemId + "/pets/" + petId).then(function (resp) {
                    self.pet = resp.data;
                    self.pet.birthDate = new Date(self.pet.birthDate);
                    self.petTypeId = "" + self.pet.type.id;
                });
            } else {
                $http.get('api/inventory/items/' + itemId).then(function (resp) {
                    self.pet = {
                        owner: resp.data.name + " " + resp.data.price
                    };
                    self.petTypeId = "1";
                })

            }
        });

        self.submit = function () {
            var id = self.pet.id || 0;

            var data = {
                id: id,
                name: self.pet.name,
                birthDate: self.pet.birthDate,
                typeId: self.petTypeId
            };

            var req;
            if (id) {
                req = $http.put("api/inventory/items/" + itemId + "/pets/" + id, data);
            } else {
                req = $http.post("api/inventory/items/" + itemId + "/pets", data);
            }

            req.then(function () {
                $state.go("items", {itemId: itemId});
            }, function (response) {
                var error = response.data;
                error.errors = error.errors || [];
                alert(error.error + "\r\n" + error.errors.map(function (e) {
                        return e.field + ": " + e.defaultMessage;
                    }).join("\r\n"));
            });
        };
    }]);
