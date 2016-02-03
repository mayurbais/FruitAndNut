'use strict';

angular.module('try1App').controller('TimeSlotDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'TimeSlot', 'TimeTable',
        function($scope, $stateParams, $uibModalInstance, entity, TimeSlot, TimeTable) {

        $scope.timeSlot = entity;
        $scope.timetables = TimeTable.query();
        $scope.load = function(id) {
            TimeSlot.get({id : id}, function(result) {
                $scope.timeSlot = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('try1App:timeSlotUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.timeSlot.id != null) {
                TimeSlot.update($scope.timeSlot, onSaveSuccess, onSaveError);
            } else {
                TimeSlot.save($scope.timeSlot, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
}]);
