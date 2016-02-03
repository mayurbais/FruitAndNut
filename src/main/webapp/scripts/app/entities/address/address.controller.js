'use strict';

angular.module('try1App')
    .controller('AddressController', function ($scope, $state, Address) {

        $scope.addresss = [];
        $scope.loadAll = function() {
            Address.query(function(result) {
               $scope.addresss = result;
            });
        };
        $scope.loadAll();


        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.address = {
                addressLineOne: null,
                addressLineTwo: null,
                city: null,
                state: null,
                pinCode: null,
                country: null,
                phone: null,
                id: null
            };
        };
    });
