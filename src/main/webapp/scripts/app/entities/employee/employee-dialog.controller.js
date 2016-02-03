'use strict';

angular.module('try1App').controller('EmployeeDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'Employee', 'IrisUser',
        function($scope, $stateParams, $uibModalInstance, $q, entity, Employee, IrisUser) {

        $scope.employee = entity;
        $scope.irisusers = IrisUser.query({filter: 'employee-is-null'});
        $q.all([$scope.employee.$promise, $scope.irisusers.$promise]).then(function() {
            if (!$scope.employee.irisUser || !$scope.employee.irisUser.id) {
                return $q.reject();
            }
            return IrisUser.get({id : $scope.employee.irisUser.id}).$promise;
        }).then(function(irisUser) {
            $scope.irisusers.push(irisUser);
        });
        $scope.load = function(id) {
            Employee.get({id : id}, function(result) {
                $scope.employee = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('try1App:employeeUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.employee.id != null) {
                Employee.update($scope.employee, onSaveSuccess, onSaveError);
            } else {
                Employee.save($scope.employee, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.datePickerForLeaveFrom = {};

        $scope.datePickerForLeaveFrom.status = {
            opened: false
        };

        $scope.datePickerForLeaveFromOpen = function($event) {
            $scope.datePickerForLeaveFrom.status.opened = true;
        };
        $scope.datePickerForLeaveTill = {};

        $scope.datePickerForLeaveTill.status = {
            opened: false
        };

        $scope.datePickerForLeaveTillOpen = function($event) {
            $scope.datePickerForLeaveTill.status.opened = true;
        };
}]);
