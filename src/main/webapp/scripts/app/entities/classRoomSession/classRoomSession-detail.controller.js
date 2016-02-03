'use strict';

angular.module('try1App')
    .controller('ClassRoomSessionDetailController', function ($scope, $rootScope, $stateParams, entity, ClassRoomSession, Employee, Section, Room, TimeTable) {
        $scope.classRoomSession = entity;
        $scope.load = function (id) {
            ClassRoomSession.get({id: id}, function(result) {
                $scope.classRoomSession = result;
            });
        };
        var unsubscribe = $rootScope.$on('try1App:classRoomSessionUpdate', function(event, result) {
            $scope.classRoomSession = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
