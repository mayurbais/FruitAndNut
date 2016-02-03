'use strict';

angular.module('try1App')
    .controller('TimeTableController', function ($scope, $state, TimeTable) {

        $scope.timeTables = [];
        $scope.loadAll = function() {
            TimeTable.query(function(result) {
               $scope.timeTables = result;
            });
        };
        $scope.loadAll();


        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.timeTable = {
                day: null,
                startDate: null,
                endDate: null,
                id: null
            };
        };
    });
