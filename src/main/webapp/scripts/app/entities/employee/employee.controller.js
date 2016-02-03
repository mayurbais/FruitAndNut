'use strict';

angular.module('try1App')
    .controller('EmployeeController', function ($scope, $state, Employee) {

        $scope.employees = [];
        $scope.loadAll = function() {
            Employee.query(function(result) {
               $scope.employees = result;
            });
        };
        $scope.loadAll();


        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.employee = {
                category: null,
                isActive: null,
                isOnLeave: null,
                leaveFrom: null,
                leaveTill: null,
                id: null
            };
        };
    });
