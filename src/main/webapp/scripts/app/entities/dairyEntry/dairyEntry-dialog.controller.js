'use strict';

angular.module('try1App').controller('DairyEntryDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'DairyEntry', 'Course', 'Teacher', 'Section', 'Student',
        function($scope, $stateParams, $uibModalInstance, $q, entity, DairyEntry, Course, Teacher, Section, Student) {

        $scope.dairyEntry = entity;
        $scope.courses = Course.query({filter: 'dairyentry-is-null'});
        $q.all([$scope.dairyEntry.$promise, $scope.courses.$promise]).then(function() {
            if (!$scope.dairyEntry.course || !$scope.dairyEntry.course.id) {
                return $q.reject();
            }
            return Course.get({id : $scope.dairyEntry.course.id}).$promise;
        }).then(function(course) {
            $scope.courses.push(course);
        });
        $scope.teachers = Teacher.query();
        $scope.sections = Section.query();
        $scope.students = Student.query({filter: 'dairyentry-is-null'});
        $q.all([$scope.dairyEntry.$promise, $scope.students.$promise]).then(function() {
            if (!$scope.dairyEntry.student || !$scope.dairyEntry.student.id) {
                return $q.reject();
            }
            return Student.get({id : $scope.dairyEntry.student.id}).$promise;
        }).then(function(student) {
            $scope.students.push(student);
        });
        $scope.load = function(id) {
            DairyEntry.get({id : id}, function(result) {
                $scope.dairyEntry = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('try1App:dairyEntryUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.dairyEntry.id != null) {
                DairyEntry.update($scope.dairyEntry, onSaveSuccess, onSaveError);
            } else {
                DairyEntry.save($scope.dairyEntry, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.datePickerForDate = {};

        $scope.datePickerForDate.status = {
            opened: false
        };

        $scope.datePickerForDateOpen = function($event) {
            $scope.datePickerForDate.status.opened = true;
        };
}]);
