'use strict';

angular.module('try1App').controller('RoomDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'Room', 'Building',
        function($scope, $stateParams, $uibModalInstance, entity, Room, Building) {

        $scope.room = entity;
        $scope.buildings = Building.query();
        $scope.load = function(id) {
            Room.get({id : id}, function(result) {
                $scope.room = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('try1App:roomUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.room.id != null) {
                Room.update($scope.room, onSaveSuccess, onSaveError);
            } else {
                Room.save($scope.room, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
}]);
