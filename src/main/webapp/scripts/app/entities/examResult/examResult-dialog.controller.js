'use strict';

angular.module('try1App').controller('ExamResultDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'ExamResult', 'Exam',
        function($scope, $stateParams, $uibModalInstance, entity, ExamResult, Exam) {

        $scope.examResult = entity;
        $scope.exams = Exam.query();
        $scope.load = function(id) {
            ExamResult.get({id : id}, function(result) {
                $scope.examResult = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('try1App:examResultUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.examResult.id != null) {
                ExamResult.update($scope.examResult, onSaveSuccess, onSaveError);
            } else {
                ExamResult.save($scope.examResult, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
}]);
