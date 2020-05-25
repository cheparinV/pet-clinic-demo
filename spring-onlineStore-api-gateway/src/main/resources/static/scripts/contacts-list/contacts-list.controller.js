'use strict';

angular.module('contactsList')
    .controller('ContactsListController', ['$http', function ($http) {
        var self = this;

        $http.get('api/contact/contacts').then(function (resp) {
            self.contactsList = resp.data;
        });
    }]);
