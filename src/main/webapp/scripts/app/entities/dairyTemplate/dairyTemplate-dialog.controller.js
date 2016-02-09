'use strict';

angular.module('try1App').controller('DairyTemplateDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'DairyTemplate', 'Course', 'Student', 'Teacher',
        function($scope, $stateParams, $uibModalInstance, $q, entity, DairyTemplate, Course, Student, Teacher) {

        $scope.dairyTemplate = entity;
        $scope.courses = Course.query({filter: 'dairytemplate-is-null'});
        $q.all([$scope.dairyTemplate.$promise, $scope.courses.$promise]).then(function() {
            if (!$scope.dairyTemplate.course || !$scope.dairyTemplate.course.id) {
                return $q.reject();
            }
            return Course.get({id : $scope.dairyTemplate.course.id}).$promise;
        }).then(function(course) {
            $scope.courses.push(course);
        });
        $scope.students = Student.query({filter: 'dairytemplate-is-null'});
        $q.all([$scope.dairyTemplate.$promise, $scope.students.$promise]).then(function() {
            if (!$scope.dairyTemplate.student || !$scope.dairyTemplate.student.id) {
                return $q.reject();
            }
            return Student.get({id : $scope.dairyTemplate.student.id}).$promise;
        }).then(function(student) {
            $scope.students.push(student);
        });
        $scope.teachers = Teacher.query();
        $scope.load = function(id) {
            DairyTemplate.get({id : id}, function(result) {
                $scope.dairyTemplate = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('try1App:dairyTemplateUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.dairyTemplate.id != null) {
                DairyTemplate.update($scope.dairyTemplate, onSaveSuccess, onSaveError);
            } else {
                DairyTemplate.save($scope.dairyTemplate, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
}]);
