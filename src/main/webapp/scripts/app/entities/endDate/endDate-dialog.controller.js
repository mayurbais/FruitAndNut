'use strict';

angular.module('try1App').controller('EndDateDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'EndDate', 'TimeSlot', 'ClassRoomSession', 'Section',
        function($scope, $stateParams, $uibModalInstance, entity, EndDate, TimeSlot, ClassRoomSession, Section) {

        $scope.endDate = entity;
        $scope.timeslots = TimeSlot.query();
        $scope.classroomsessions = ClassRoomSession.query();
        $scope.sections = Section.query();
        $scope.load = function(id) {
            EndDate.get({id : id}, function(result) {
                $scope.endDate = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('try1App:endDateUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.endDate.id != null) {
                EndDate.update($scope.endDate, onSaveSuccess, onSaveError);
            } else {
                EndDate.save($scope.endDate, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.datePickerForStartDate = {};

        $scope.datePickerForStartDate.status = {
            opened: false
        };

        $scope.datePickerForStartDateOpen = function($event) {
            $scope.datePickerForStartDate.status.opened = true;
        };
        $scope.datePickerForEndDate = {};

        $scope.datePickerForEndDate.status = {
            opened: false
        };

        $scope.datePickerForEndDateOpen = function($event) {
            $scope.datePickerForEndDate.status.opened = true;
        };
}]);
