'use strict';

angular.module('try1App').controller('EventsDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'Events', 'Section',
        function($scope, $stateParams, $uibModalInstance, entity, Events, Section) {

        $scope.events = entity;
        $scope.sections = Section.query();
        $scope.load = function(id) {
            Events.get({id : id}, function(result) {
                $scope.events = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('try1App:eventsUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.events.id != null) {
                Events.update($scope.events, onSaveSuccess, onSaveError);
            } else {
                Events.save($scope.events, onSaveSuccess, onSaveError);
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
