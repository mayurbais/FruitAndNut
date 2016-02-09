'use strict';

angular.module('try1App').controller('DairyDescriptionDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'DairyDescription', 'SchoolDetails',
        function($scope, $stateParams, $uibModalInstance, $q, entity, DairyDescription, SchoolDetails) {

        $scope.dairyDescription = entity;
        $scope.schooldetailss = SchoolDetails.query({filter: 'dairydescription-is-null'});
        $q.all([$scope.dairyDescription.$promise, $scope.schooldetailss.$promise]).then(function() {
            if (!$scope.dairyDescription.schoolDetails || !$scope.dairyDescription.schoolDetails.id) {
                return $q.reject();
            }
            return SchoolDetails.get({id : $scope.dairyDescription.schoolDetails.id}).$promise;
        }).then(function(schoolDetails) {
            $scope.schooldetailss.push(schoolDetails);
        });
        $scope.load = function(id) {
            DairyDescription.get({id : id}, function(result) {
                $scope.dairyDescription = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('try1App:dairyDescriptionUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.dairyDescription.id != null) {
                DairyDescription.update($scope.dairyDescription, onSaveSuccess, onSaveError);
            } else {
                DairyDescription.save($scope.dairyDescription, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
}]);
