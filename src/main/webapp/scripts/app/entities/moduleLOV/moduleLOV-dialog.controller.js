'use strict';

angular.module('try1App').controller('ModuleLOVDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'ModuleLOV',
        function($scope, $stateParams, $uibModalInstance, entity, ModuleLOV) {

        $scope.moduleLOV = entity;
        $scope.load = function(id) {
            ModuleLOV.get({id : id}, function(result) {
                $scope.moduleLOV = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('try1App:moduleLOVUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.moduleLOV.id != null) {
                ModuleLOV.update($scope.moduleLOV, onSaveSuccess, onSaveError);
            } else {
                ModuleLOV.save($scope.moduleLOV, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
}]);
