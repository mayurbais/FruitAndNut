'use strict';

angular.module('try1App').controller('ExamSubjectsDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'ExamSubjects', 'Exam', 'Subject', 'Teacher', 'Room',
        function($scope, $stateParams, $uibModalInstance, $q, entity, ExamSubjects, Exam, Subject, Teacher, Room) {

        $scope.examSubjects = entity;
        $scope.exams = Exam.query();
        $scope.subjects = Subject.query({filter: 'examsubjects-is-null'});
        $q.all([$scope.examSubjects.$promise, $scope.subjects.$promise]).then(function() {
            if (!$scope.examSubjects.subject || !$scope.examSubjects.subject.id) {
                return $q.reject();
            }
            return Subject.get({id : $scope.examSubjects.subject.id}).$promise;
        }).then(function(subject) {
            $scope.subjects.push(subject);
        });
        $scope.teachers = Teacher.query({filter: 'examsubjects-is-null'});
        $q.all([$scope.examSubjects.$promise, $scope.teachers.$promise]).then(function() {
            if (!$scope.examSubjects.teacher || !$scope.examSubjects.teacher.id) {
                return $q.reject();
            }
            return Teacher.get({id : $scope.examSubjects.teacher.id}).$promise;
        }).then(function(teacher) {
            $scope.teachers.push(teacher);
        });
        $scope.rooms = Room.query({filter: 'examsubjects-is-null'});
        $q.all([$scope.examSubjects.$promise, $scope.rooms.$promise]).then(function() {
            if (!$scope.examSubjects.room || !$scope.examSubjects.room.id) {
                return $q.reject();
            }
            return Room.get({id : $scope.examSubjects.room.id}).$promise;
        }).then(function(room) {
            $scope.rooms.push(room);
        });
        $scope.load = function(id) {
            ExamSubjects.get({id : id}, function(result) {
                $scope.examSubjects = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('try1App:examSubjectsUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.examSubjects.id != null) {
                ExamSubjects.update($scope.examSubjects, onSaveSuccess, onSaveError);
            } else {
                ExamSubjects.save($scope.examSubjects, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.datePickerForStartTime = {};

        $scope.datePickerForStartTime.status = {
            opened: false
        };

        $scope.datePickerForStartTimeOpen = function($event) {
            $scope.datePickerForStartTime.status.opened = true;
        };
        $scope.datePickerForEndTime = {};

        $scope.datePickerForEndTime.status = {
            opened: false
        };

        $scope.datePickerForEndTimeOpen = function($event) {
            $scope.datePickerForEndTime.status.opened = true;
        };
        $scope.datePickerForConductingDate = {};

        $scope.datePickerForConductingDate.status = {
            opened: false
        };

        $scope.datePickerForConductingDateOpen = function($event) {
            $scope.datePickerForConductingDate.status.opened = true;
        };
}]);
