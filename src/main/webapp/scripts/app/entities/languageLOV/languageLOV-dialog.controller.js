'use strict';

angular.module('try1App').controller('LanguageLOVDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'LanguageLOV',
        function($scope, $stateParams, $uibModalInstance, entity, LanguageLOV) {

        $scope.languageLOV = entity;
        $scope.load = function(id) {
            LanguageLOV.get({id : id}, function(result) {
                $scope.languageLOV = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('try1App:languageLOVUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.languageLOV.id != null) {
                LanguageLOV.update($scope.languageLOV, onSaveSuccess, onSaveError);
            } else {
                LanguageLOV.save($scope.languageLOV, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
}]);
