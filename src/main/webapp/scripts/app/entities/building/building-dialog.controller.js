'use strict';

angular.module('try1App').controller('BuildingDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'Building', 'Room',
        function($scope, $stateParams, $uibModalInstance, entity, Building, Room) {

        $scope.building = entity;
        $scope.rooms = Room.query();
        $scope.load = function(id) {
            Building.get({id : id}, function(result) {
                $scope.building = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('try1App:buildingUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.building.id != null) {
                Building.update($scope.building, onSaveSuccess, onSaveError);
            } else {
                Building.save($scope.building, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
}]);
