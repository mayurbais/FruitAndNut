'use strict';

angular.module('try1App')
    .controller('BuildingController', function ($scope, $state, Building) {

        $scope.buildings = [];
        $scope.loadAll = function() {
            Building.query(function(result) {
               $scope.buildings = result;
            });
        };
        $scope.loadAll();


        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.building = {
                name: null,
                id: null
            };
        };
    });
