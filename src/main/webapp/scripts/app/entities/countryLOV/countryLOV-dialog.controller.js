'use strict';

angular.module('try1App').controller('CountryLOVDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'CountryLOV',
        function($scope, $stateParams, $uibModalInstance, entity, CountryLOV) {

        $scope.countryLOV = entity;
        $scope.load = function(id) {
            CountryLOV.get({id : id}, function(result) {
                $scope.countryLOV = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('try1App:countryLOVUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.countryLOV.id != null) {
                CountryLOV.update($scope.countryLOV, onSaveSuccess, onSaveError);
            } else {
                CountryLOV.save($scope.countryLOV, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
}]);
