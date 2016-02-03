'use strict';

angular.module('try1App')
    .controller('RoomDetailController', function ($scope, $rootScope, $stateParams, entity, Room, Building) {
        $scope.room = entity;
        $scope.load = function (id) {
            Room.get({id: id}, function(result) {
                $scope.room = result;
            });
        };
        var unsubscribe = $rootScope.$on('try1App:roomUpdate', function(event, result) {
            $scope.room = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
