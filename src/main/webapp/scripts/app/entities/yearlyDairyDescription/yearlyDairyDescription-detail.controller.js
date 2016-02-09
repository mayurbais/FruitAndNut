'use strict';

angular.module('try1App')
    .controller('YearlyDairyDescriptionDetailController', function ($scope, $rootScope, $stateParams, entity, YearlyDairyDescription, SchoolDetails) {
        $scope.yearlyDairyDescription = entity;
        $scope.load = function (id) {
            YearlyDairyDescription.get({id: id}, function(result) {
                $scope.yearlyDairyDescription = result;
            });
        };
        var unsubscribe = $rootScope.$on('try1App:yearlyDairyDescriptionUpdate', function(event, result) {
            $scope.yearlyDairyDescription = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
