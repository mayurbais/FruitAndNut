'use strict';

angular.module('try1App')
    .controller('EndDateController', function ($scope, $state, EndDate) {

        $scope.endDates = [];
        $scope.loadAll = function() {
            EndDate.query(function(result) {
               $scope.endDates = result;
            });
        };
        $scope.loadAll();


        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.endDate = {
                day: null,
                startDate: null,
                endDate: null,
                id: null
            };
        };
    });
