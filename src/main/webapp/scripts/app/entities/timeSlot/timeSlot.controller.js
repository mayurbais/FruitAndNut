'use strict';

angular.module('try1App')
    .controller('TimeSlotController', function ($scope, $state, TimeSlot) {

        $scope.timeSlots = [];
        $scope.loadAll = function() {
            TimeSlot.query(function(result) {
               $scope.timeSlots = result;
            });
        };
        $scope.loadAll();


        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.timeSlot = {
                name: null,
                startTime: null,
                endTime: null,
                id: null
            };
        };
    });
