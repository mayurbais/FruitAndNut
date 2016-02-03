'use strict';

angular.module('try1App').controller('SessionDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'Session', 'Employee', 'Section', 'TimeTable',
        function($scope, $stateParams, $uibModalInstance, $q, entity, Session, Employee, Section, TimeTable) {

        $scope.session = entity;
        $scope.employees = Employee.query({filter: 'session-is-null'});
        $q.all([$scope.session.$promise, $scope.employees.$promise]).then(function() {
            if (!$scope.session.employee || !$scope.session.employee.id) {
                return $q.reject();
            }
            return Employee.get({id : $scope.session.employee.id}).$promise;
        }).then(function(employee) {
            $scope.employees.push(employee);
        });
        $scope.sections = Section.query();
        $scope.timetables = TimeTable.query();
        $scope.load = function(id) {
            Session.get({id : id}, function(result) {
                $scope.session = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('try1App:sessionUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.session.id != null) {
                Session.update($scope.session, onSaveSuccess, onSaveError);
            } else {
                Session.save($scope.session, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
}]);
