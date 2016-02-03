'use strict';

angular.module('try1App')
    .controller('ClassRoomSessionController', function ($scope, $state, ClassRoomSession) {

        $scope.classRoomSessions = [];
        $scope.loadAll = function() {
            ClassRoomSession.query(function(result) {
               $scope.classRoomSessions = result;
            });
        };
        $scope.loadAll();


        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.classRoomSession = {
                sessionName: null,
                startTime: null,
                endTime: null,
                isBreak: null,
                attribute: null,
                id: null
            };
        };
    });
