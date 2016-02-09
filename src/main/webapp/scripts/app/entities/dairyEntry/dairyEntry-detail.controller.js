'use strict';

angular.module('try1App')
    .controller('DairyEntryDetailController', function ($scope, $rootScope, $stateParams, entity, DairyEntry, Course, Teacher, Section) {
        $scope.dairyEntry = entity;
        $scope.load = function (id) {
            DairyEntry.get({id: id}, function(result) {
                $scope.dairyEntry = result;
            });
        };
        var unsubscribe = $rootScope.$on('try1App:dairyEntryUpdate', function(event, result) {
            $scope.dairyEntry = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
