'use strict';

angular.module('try1App').controller('SchoolDetailsDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'SchoolDetails', 'User', 'LanguageLOV', 'CurrancyLOV', 'CountryLOV',
        function($scope, $stateParams, $uibModalInstance, $q, entity, SchoolDetails, User, LanguageLOV, CurrancyLOV, CountryLOV) {

        $scope.schoolDetails = entity;
        $scope.users = User.query();
        $scope.languagelovs = LanguageLOV.query({filter: 'schooldetails-is-null'});
        $q.all([$scope.schoolDetails.$promise, $scope.languagelovs.$promise]).then(function() {
            if (!$scope.schoolDetails.languageLOV || !$scope.schoolDetails.languageLOV.id) {
                return $q.reject();
            }
            return LanguageLOV.get({id : $scope.schoolDetails.languageLOV.id}).$promise;
        }).then(function(languageLOV) {
            $scope.languagelovs.push(languageLOV);
        });
        $scope.currancylovs = CurrancyLOV.query({filter: 'schooldetails-is-null'});
        $q.all([$scope.schoolDetails.$promise, $scope.currancylovs.$promise]).then(function() {
            if (!$scope.schoolDetails.currancyLOV || !$scope.schoolDetails.currancyLOV.id) {
                return $q.reject();
            }
            return CurrancyLOV.get({id : $scope.schoolDetails.currancyLOV.id}).$promise;
        }).then(function(currancyLOV) {
            $scope.currancylovs.push(currancyLOV);
        });
        $scope.countrylovs = CountryLOV.query({filter: 'schooldetails-is-null'});
        $q.all([$scope.schoolDetails.$promise, $scope.countrylovs.$promise]).then(function() {
            if (!$scope.schoolDetails.countryLOV || !$scope.schoolDetails.countryLOV.id) {
                return $q.reject();
            }
            return CountryLOV.get({id : $scope.schoolDetails.countryLOV.id}).$promise;
        }).then(function(countryLOV) {
            $scope.countrylovs.push(countryLOV);
        });
        $scope.load = function(id) {
            SchoolDetails.get({id : id}, function(result) {
                $scope.schoolDetails = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('try1App:schoolDetailsUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.schoolDetails.id != null) {
                SchoolDetails.update($scope.schoolDetails, onSaveSuccess, onSaveError);
            } else {
                SchoolDetails.save($scope.schoolDetails, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.datePickerForFinancialStartDate = {};

        $scope.datePickerForFinancialStartDate.status = {
            opened: false
        };

        $scope.datePickerForFinancialStartDateOpen = function($event) {
            $scope.datePickerForFinancialStartDate.status.opened = true;
        };
        $scope.datePickerForFinancialEndDate = {};

        $scope.datePickerForFinancialEndDate.status = {
            opened: false
        };

        $scope.datePickerForFinancialEndDateOpen = function($event) {
            $scope.datePickerForFinancialEndDate.status.opened = true;
        };
}]);
