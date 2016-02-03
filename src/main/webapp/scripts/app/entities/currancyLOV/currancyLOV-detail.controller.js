'use strict';

angular.module('try1App')
    .controller('CurrancyLOVDetailController', function ($scope, $rootScope, $stateParams, entity, CurrancyLOV) {
        $scope.currancyLOV = entity;
        $scope.load = function (id) {
            CurrancyLOV.get({id: id}, function(result) {
                $scope.currancyLOV = result;
            });
        };
        var unsubscribe = $rootScope.$on('try1App:currancyLOVUpdate', function(event, result) {
            $scope.currancyLOV = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
