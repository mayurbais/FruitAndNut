'use strict';

angular.module('try1App').controller('YearlyDairyDescriptionDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'YearlyDairyDescription', 'SchoolDetails',
        function($scope, $stateParams, $uibModalInstance, $q, entity, YearlyDairyDescription, SchoolDetails) {

        $scope.yearlyDairyDescription = entity;
        $scope.schooldetailss = SchoolDetails.query({filter: 'yearlydairydescription-is-null'});
        $q.all([$scope.yearlyDairyDescription.$promise, $scope.schooldetailss.$promise]).then(function() {
            if (!$scope.yearlyDairyDescription.schoolDetails || !$scope.yearlyDairyDescription.schoolDetails.id) {
                return $q.reject();
            }
            return SchoolDetails.get({id : $scope.yearlyDairyDescription.schoolDetails.id}).$promise;
        }).then(function(schoolDetails) {
            $scope.schooldetailss.push(schoolDetails);
        });
        $scope.load = function(id) {
            YearlyDairyDescription.get({id : id}, function(result) {
                $scope.yearlyDairyDescription = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('try1App:yearlyDairyDescriptionUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.yearlyDairyDescription.id != null) {
                YearlyDairyDescription.update($scope.yearlyDairyDescription, onSaveSuccess, onSaveError);
            } else {
                YearlyDairyDescription.save($scope.yearlyDairyDescription, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.datePickerForYear = {};

        $scope.datePickerForYear.status = {
            opened: false
        };

        $scope.datePickerForYearOpen = function($event) {
            $scope.datePickerForYear.status.opened = true;
        };
}]);
