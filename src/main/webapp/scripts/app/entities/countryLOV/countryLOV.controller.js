'use strict';

angular.module('try1App')
    .controller('CountryLOVController', function ($scope, $state, CountryLOV) {

        $scope.countryLOVs = [];
        $scope.loadAll = function() {
            CountryLOV.query(function(result) {
               $scope.countryLOVs = result;
            });
        };
        $scope.loadAll();


        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.countryLOV = {
                value: null,
                name: null,
                id: null
            };
        };
    });
