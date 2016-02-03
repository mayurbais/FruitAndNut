'use strict';

angular.module('try1App').controller('SectionDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'Section', 'Course', 'Teacher', 'Room', 'TimeTable',
        function($scope, $stateParams, $uibModalInstance, $q, entity, Section, Course, Teacher, Room, TimeTable) {

        $scope.section = entity;
        $scope.courses = Course.query({filter: 'section-is-null'});
        $q.all([$scope.section.$promise, $scope.courses.$promise]).then(function() {
            if (!$scope.section.course || !$scope.section.course.id) {
                return $q.reject();
            }
            return Course.get({id : $scope.section.course.id}).$promise;
        }).then(function(course) {
            $scope.courses.push(course);
        });
        $scope.teachers = Teacher.query({filter: 'section-is-null'});
        $q.all([$scope.section.$promise, $scope.teachers.$promise]).then(function() {
            if (!$scope.section.teacher || !$scope.section.teacher.id) {
                return $q.reject();
            }
            return Teacher.get({id : $scope.section.teacher.id}).$promise;
        }).then(function(teacher) {
            $scope.teachers.push(teacher);
        });
        $scope.rooms = Room.query();
        $scope.timetables = TimeTable.query();
        $scope.load = function(id) {
            Section.get({id : id}, function(result) {
                $scope.section = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('try1App:sectionUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.section.id != null) {
                Section.update($scope.section, onSaveSuccess, onSaveError);
            } else {
                Section.save($scope.section, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
}]);
