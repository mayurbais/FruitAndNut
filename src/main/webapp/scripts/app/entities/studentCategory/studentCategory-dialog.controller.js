'use strict';

angular.module('try1App').controller('StudentCategoryDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'StudentCategory',
        function($scope, $stateParams, $uibModalInstance, entity, StudentCategory) {

        $scope.studentCategory = entity;
        $scope.load = function(id) {
            StudentCategory.get({id : id}, function(result) {
                $scope.studentCategory = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('try1App:studentCategoryUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.studentCategory.id != null) {
                StudentCategory.update($scope.studentCategory, onSaveSuccess, onSaveError);
            } else {
                StudentCategory.save($scope.studentCategory, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
}]);
