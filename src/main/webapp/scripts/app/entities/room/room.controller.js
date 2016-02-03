'use strict';

angular.module('try1App')
    .controller('RoomController', function ($scope, $state, Room) {

        $scope.rooms = [];
        $scope.loadAll = function() {
            Room.query(function(result) {
               $scope.rooms = result;
            });
        };
        $scope.loadAll();


        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.room = {
                name: null,
                id: null
            };
        };
    });
