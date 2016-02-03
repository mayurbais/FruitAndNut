'use strict';

angular.module('try1App').controller('ElectiveSubjectDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'ElectiveSubject', 'Course',
        function($scope, $stateParams, $uibModalInstance, entity, ElectiveSubject, Course) {

        $scope.electiveSubject = entity;
        $scope.courses = Course.query();
        $scope.load = function(id) {
            ElectiveSubject.get({id : id}, function(result) {
                $scope.electiveSubject = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('try1App:electiveSubjectUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.electiveSubject.id != null) {
                ElectiveSubject.update($scope.electiveSubject, onSaveSuccess, onSaveError);
            } else {
                ElectiveSubject.save($scope.electiveSubject, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
}]);
