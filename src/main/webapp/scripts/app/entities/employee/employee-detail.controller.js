'use strict';

angular.module('try1App')
    .controller('EmployeeDetailController', function ($scope, $rootScope, $stateParams, entity, Employee, IrisUser) {
        $scope.employee = entity;
        $scope.load = function (id) {
            Employee.get({id: id}, function(result) {
                $scope.employee = result;
            });
        };
        var unsubscribe = $rootScope.$on('try1App:employeeUpdate', function(event, result) {
            $scope.employee = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
