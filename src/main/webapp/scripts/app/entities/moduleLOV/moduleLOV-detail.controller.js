'use strict';

angular.module('try1App')
    .controller('ModuleLOVDetailController', function ($scope, $rootScope, $stateParams, entity, ModuleLOV) {
        $scope.moduleLOV = entity;
        $scope.load = function (id) {
            ModuleLOV.get({id: id}, function(result) {
                $scope.moduleLOV = result;
            });
        };
        var unsubscribe = $rootScope.$on('try1App:moduleLOVUpdate', function(event, result) {
            $scope.moduleLOV = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
