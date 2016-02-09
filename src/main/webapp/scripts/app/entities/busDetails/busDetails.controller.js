'use strict';

angular.module('try1App')
    .controller('BusDetailsController', function ($scope, $state, BusDetails) {

        $scope.busDetailss = [];
        $scope.loadAll = function() {
            BusDetails.query(function(result) {
               $scope.busDetailss = result;
            });
        };
        $scope.loadAll();


        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.busDetails = {
                busNo: null,
                route: null,
                timing: null,
                driverName: null,
                driverContactNo: null,
                id: null
            };
        };
    });
