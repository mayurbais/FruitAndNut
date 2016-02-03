'use strict';

angular.module('try1App').controller('CustomAdmissionDetailsDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'CustomAdmissionDetails', 'SchoolDetails',
        function($scope, $stateParams, $uibModalInstance, $q, entity, CustomAdmissionDetails, SchoolDetails) {

        $scope.customAdmissionDetails = entity;
        $scope.schooldetailss = SchoolDetails.query({filter: 'customadmissiondetails-is-null'});
        $q.all([$scope.customAdmissionDetails.$promise, $scope.schooldetailss.$promise]).then(function() {
            if (!$scope.customAdmissionDetails.schoolDetails || !$scope.customAdmissionDetails.schoolDetails.id) {
                return $q.reject();
            }
            return SchoolDetails.get({id : $scope.customAdmissionDetails.schoolDetails.id}).$promise;
        }).then(function(schoolDetails) {
            $scope.schooldetailss.push(schoolDetails);
        });
        $scope.load = function(id) {
            CustomAdmissionDetails.get({id : id}, function(result) {
                $scope.customAdmissionDetails = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('try1App:customAdmissionDetailsUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.customAdmissionDetails.id != null) {
                CustomAdmissionDetails.update($scope.customAdmissionDetails, onSaveSuccess, onSaveError);
            } else {
                CustomAdmissionDetails.save($scope.customAdmissionDetails, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
}]);
