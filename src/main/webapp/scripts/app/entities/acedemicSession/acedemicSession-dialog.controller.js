'use strict';

angular.module('try1App').controller('AcedemicSessionDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'AcedemicSession', 'SchoolDetails',
        function($scope, $stateParams, $uibModalInstance, entity, AcedemicSession, SchoolDetails) {

        $scope.acedemicSession = entity;
        $scope.schooldetailss = SchoolDetails.query();
        $scope.load = function(id) {
            AcedemicSession.get({id : id}, function(result) {
                $scope.acedemicSession = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('try1App:acedemicSessionUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.acedemicSession.id != null) {
                AcedemicSession.update($scope.acedemicSession, onSaveSuccess, onSaveError);
            } else {
                AcedemicSession.save($scope.acedemicSession, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.datePickerForSessionStartDate = {};

        $scope.datePickerForSessionStartDate.status = {
            opened: false
        };

        $scope.datePickerForSessionStartDateOpen = function($event) {
            $scope.datePickerForSessionStartDate.status.opened = true;
        };
        $scope.datePickerForSessionEndDate = {};

        $scope.datePickerForSessionEndDate.status = {
            opened: false
        };

        $scope.datePickerForSessionEndDateOpen = function($event) {
            $scope.datePickerForSessionEndDate.status.opened = true;
        };
}]);
