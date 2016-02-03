'use strict';

angular.module('try1App')
    .controller('BuildingDetailController', function ($scope, $rootScope, $stateParams, entity, Building, Room) {
        $scope.building = entity;
        $scope.load = function (id) {
            Building.get({id: id}, function(result) {
                $scope.building = result;
            });
        };
        var unsubscribe = $rootScope.$on('try1App:buildingUpdate', function(event, result) {
            $scope.building = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
