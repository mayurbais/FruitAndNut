'use strict';

angular.module('try1App').controller('SubjectExamResultDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'SubjectExamResult', 'ExamSubjects',
        function($scope, $stateParams, $uibModalInstance, entity, SubjectExamResult, ExamSubjects) {

        $scope.subjectExamResult = entity;
        $scope.examsubjectss = ExamSubjects.query();
        $scope.load = function(id) {
            SubjectExamResult.get({id : id}, function(result) {
                $scope.subjectExamResult = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('try1App:subjectExamResultUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.subjectExamResult.id != null) {
                SubjectExamResult.update($scope.subjectExamResult, onSaveSuccess, onSaveError);
            } else {
                SubjectExamResult.save($scope.subjectExamResult, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
}]);
