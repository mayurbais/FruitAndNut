'use strict';

angular.module('try1App').controller('CurrancyLOVDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'CurrancyLOV',
        function($scope, $stateParams, $uibModalInstance, entity, CurrancyLOV) {

        $scope.currancyLOV = entity;
        $scope.load = function(id) {
            CurrancyLOV.get({id : id}, function(result) {
                $scope.currancyLOV = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('try1App:currancyLOVUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.currancyLOV.id != null) {
                CurrancyLOV.update($scope.currancyLOV, onSaveSuccess, onSaveError);
            } else {
                CurrancyLOV.save($scope.currancyLOV, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
}]);
