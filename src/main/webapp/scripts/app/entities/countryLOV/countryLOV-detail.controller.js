'use strict';

angular.module('try1App')
    .controller('CountryLOVDetailController', function ($scope, $rootScope, $stateParams, entity, CountryLOV) {
        $scope.countryLOV = entity;
        $scope.load = function (id) {
            CountryLOV.get({id: id}, function(result) {
                $scope.countryLOV = result;
            });
        };
        var unsubscribe = $rootScope.$on('try1App:countryLOVUpdate', function(event, result) {
            $scope.countryLOV = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
