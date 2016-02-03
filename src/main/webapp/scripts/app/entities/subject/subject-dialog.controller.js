'use strict';

angular.module('try1App').controller('SubjectDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'Subject', 'Course', 'Teacher',
        function($scope, $stateParams, $uibModalInstance, entity, Subject, Course, Teacher) {

        $scope.subject = entity;
        $scope.courses = Course.query();
        $scope.teachers = Teacher.query();
        $scope.load = function(id) {
            Subject.get({id : id}, function(result) {
                $scope.subject = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('try1App:subjectUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.subject.id != null) {
                Subject.update($scope.subject, onSaveSuccess, onSaveError);
            } else {
                Subject.save($scope.subject, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
}]);
