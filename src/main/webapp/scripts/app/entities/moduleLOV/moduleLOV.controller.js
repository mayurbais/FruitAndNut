'use strict';

angular.module('try1App')
    .controller('ModuleLOVController', function ($scope, $state, ModuleLOV) {

        $scope.moduleLOVs = [];
        $scope.loadAll = function() {
            ModuleLOV.query(function(result) {
               $scope.moduleLOVs = result;
            });
        };
        $scope.loadAll();


        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.moduleLOV = {
                type: null,
                name: null,
                value: null,
                id: null
            };
        };
    });
