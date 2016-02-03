'use strict';

angular.module('try1App')
    .controller('TimeTableDetailController', function ($scope, $rootScope, $stateParams, entity, TimeTable, TimeSlot, ClassRoomSession, Section) {
        $scope.timeTable = entity;
        $scope.load = function (id) {
            TimeTable.get({id: id}, function(result) {
                $scope.timeTable = result;
            });
        };
        var unsubscribe = $rootScope.$on('try1App:timeTableUpdate', function(event, result) {
            $scope.timeTable = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
