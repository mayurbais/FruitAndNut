'use strict';

angular.module('try1App').controller('TimeTableDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'TimeTable', 'TimeSlot', 'ClassRoomSession', 'Section',
        function($scope, $stateParams, $uibModalInstance, entity, TimeTable, TimeSlot, ClassRoomSession, Section) {

        $scope.timeTable = entity;
        $scope.timeslots = TimeSlot.query();
        $scope.classroomsessions = ClassRoomSession.query();
        $scope.sections = Section.query();
        $scope.load = function(id) {
            TimeTable.get({id : id}, function(result) {
                $scope.timeTable = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('try1App:timeTableUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.timeTable.id != null) {
                TimeTable.update($scope.timeTable, onSaveSuccess, onSaveError);
            } else {
                TimeTable.save($scope.timeTable, onSaveSuccess, onSaveError);
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
