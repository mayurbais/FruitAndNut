'use strict';

angular.module('try1App')
    .controller('CurrancyLOVController', function ($scope, $state, CurrancyLOV) {

        $scope.currancyLOVs = [];
        $scope.loadAll = function() {
            CurrancyLOV.query(function(result) {
               $scope.currancyLOVs = result;
            });
        };
        $scope.loadAll();


        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.currancyLOV = {
                value: null,
                name: null,
                id: null
            };
        };
    });
