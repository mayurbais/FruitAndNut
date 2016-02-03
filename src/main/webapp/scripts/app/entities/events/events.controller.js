'use strict';

angular.module('try1App')
    .controller('EventsController', function ($scope, $state, Events) {

        $scope.eventss = [];
        $scope.loadAll = function() {
            Events.query(function(result) {
               $scope.eventss = result;
            });
        };
        $scope.loadAll();


        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.events = {
                title: null,
                description: null,
                startDate: null,
                endDate: null,
                isHoliday: null,
                isCommonToAll: null,
                id: null
            };
        };
    });
