'use strict';

angular.module('try1App')
    .controller('TimeSlotDetailController', function ($scope, $rootScope, $stateParams, entity, TimeSlot, TimeTable) {
        $scope.timeSlot = entity;
        $scope.load = function (id) {
            TimeSlot.get({id: id}, function(result) {
                $scope.timeSlot = result;
            });
        };
        var unsubscribe = $rootScope.$on('try1App:timeSlotUpdate', function(event, result) {
            $scope.timeSlot = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
