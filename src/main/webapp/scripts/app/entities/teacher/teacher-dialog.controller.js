'use strict';

angular.module('try1App').controller('TeacherDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'Teacher', 'Subject', 'Employee',
        function($scope, $stateParams, $uibModalInstance, $q, entity, Teacher, Subject, Employee) {

        $scope.teacher = entity;
        $scope.subjects = Subject.query();
        $scope.employees = Employee.query({filter: 'teacher-is-null'});
        $q.all([$scope.teacher.$promise, $scope.employees.$promise]).then(function() {
            if (!$scope.teacher.employee || !$scope.teacher.employee.id) {
                return $q.reject();
            }
            return Employee.get({id : $scope.teacher.employee.id}).$promise;
        }).then(function(employee) {
            $scope.employees.push(employee);
        });
        $scope.load = function(id) {
            Teacher.get({id : id}, function(result) {
                $scope.teacher = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('try1App:teacherUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.teacher.id != null) {
                Teacher.update($scope.teacher, onSaveSuccess, onSaveError);
            } else {
                Teacher.save($scope.teacher, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
}]);
