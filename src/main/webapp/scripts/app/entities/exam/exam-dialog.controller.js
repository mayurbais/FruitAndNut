'use strict';

angular.module('try1App').controller('ExamDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'Exam', 'Course', 'Section', 'ExamSubjects',
        function($scope, $stateParams, $uibModalInstance, entity, Exam, Course, Section, ExamSubjects) {

        $scope.exam = entity;
        $scope.courses = Course.query();
        $scope.sections = Section.query();
        $scope.examsubjectss = ExamSubjects.query();
        $scope.load = function(id) {
            Exam.get({id : id}, function(result) {
                $scope.exam = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('try1App:examUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.exam.id != null) {
                Exam.update($scope.exam, onSaveSuccess, onSaveError);
            } else {
                Exam.save($scope.exam, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.datePickerForStartDate = {};

        $scope.datePickerForStartDate.status = {
            opened: false
        };

        $scope.datePickerForStartDateOpen = function($event) {
            $scope.datePickerForStartDate.status.opened = true;
        };
        $scope.datePickerForEndDate = {};

        $scope.datePickerForEndDate.status = {
            opened: false
        };

        $scope.datePickerForEndDateOpen = function($event) {
            $scope.datePickerForEndDate.status.opened = true;
        };
}]);
