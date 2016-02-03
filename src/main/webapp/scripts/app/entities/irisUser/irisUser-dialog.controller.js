'use strict';

angular.module('try1App').controller('IrisUserDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'IrisUser', 'User', 'LanguageLOV', 'CountryLOV', 'Address',
        function($scope, $stateParams, $uibModalInstance, $q, entity, IrisUser, User, LanguageLOV, CountryLOV, Address) {

        $scope.irisUser = entity;
        $scope.users = User.query();
        $scope.languagelovs = LanguageLOV.query({filter: 'irisuser-is-null'});
        $q.all([$scope.irisUser.$promise, $scope.languagelovs.$promise]).then(function() {
            if (!$scope.irisUser.languageLOV || !$scope.irisUser.languageLOV.id) {
                return $q.reject();
            }
            return LanguageLOV.get({id : $scope.irisUser.languageLOV.id}).$promise;
        }).then(function(languageLOV) {
            $scope.languagelovs.push(languageLOV);
        });
        $scope.countrylovs = CountryLOV.query({filter: 'irisuser-is-null'});
        $q.all([$scope.irisUser.$promise, $scope.countrylovs.$promise]).then(function() {
            if (!$scope.irisUser.countryLOV || !$scope.irisUser.countryLOV.id) {
                return $q.reject();
            }
            return CountryLOV.get({id : $scope.irisUser.countryLOV.id}).$promise;
        }).then(function(countryLOV) {
            $scope.countrylovs.push(countryLOV);
        });
        $scope.addresss = Address.query({filter: 'irisuser-is-null'});
        $q.all([$scope.irisUser.$promise, $scope.addresss.$promise]).then(function() {
            if (!$scope.irisUser.address || !$scope.irisUser.address.id) {
                return $q.reject();
            }
            return Address.get({id : $scope.irisUser.address.id}).$promise;
        }).then(function(address) {
            $scope.addresss.push(address);
        });
        $scope.load = function(id) {
            IrisUser.get({id : id}, function(result) {
                $scope.irisUser = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('try1App:irisUserUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.irisUser.id != null) {
                IrisUser.update($scope.irisUser, onSaveSuccess, onSaveError);
            } else {
                IrisUser.save($scope.irisUser, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.datePickerForDateOfBirth = {};

        $scope.datePickerForDateOfBirth.status = {
            opened: false
        };

        $scope.datePickerForDateOfBirthOpen = function($event) {
            $scope.datePickerForDateOfBirth.status.opened = true;
        };
}]);

angular.module('try1App').controller('AdmissionIrisUserDialogController',
	    ['$scope', '$stateParams',  '$q',  'IrisUser', 'User', 'LanguageLOV', 'CountryLOV', 'Address',
	        function($scope, $stateParams, $q,  IrisUser, User, LanguageLOV, CountryLOV, Address) {

	        $scope.irisUser = function () {
                return {
                    firstName: null,
                    lastName: null,
                    middleName: null,
                    dateOfBirth: null,
                    gender: null,
                    bloodGroup: null,
                    birthPlace: null,
                    religion: null,
                    photo: null,
                    phone: null,
                    id: null
                };
            };
	        $scope.users = User.query();
	        $scope.languagelovs = LanguageLOV.query({filter: 'irisuser-is-null'});
	        $q.all([$scope.irisUser.$promise, $scope.languagelovs.$promise]).then(function() {
	            if (!$scope.irisUser.languageLOV || !$scope.irisUser.languageLOV.id) {
	                return $q.reject();
	            }
	            return LanguageLOV.get({id : $scope.irisUser.languageLOV.id}).$promise;
	        }).then(function(languageLOV) {
	            $scope.languagelovs.push(languageLOV);
	        });
	        $scope.countrylovs = CountryLOV.query({filter: 'irisuser-is-null'});
	        $q.all([$scope.irisUser.$promise, $scope.countrylovs.$promise]).then(function() {
	            if (!$scope.irisUser.countryLOV || !$scope.irisUser.countryLOV.id) {
	                return $q.reject();
	            }
	            return CountryLOV.get({id : $scope.irisUser.countryLOV.id}).$promise;
	        }).then(function(countryLOV) {
	            $scope.countrylovs.push(countryLOV);
	        });
	        $scope.addresss = Address.query({filter: 'irisuser-is-null'});
	        $q.all([$scope.irisUser.$promise, $scope.addresss.$promise]).then(function() {
	            if (!$scope.irisUser.address || !$scope.irisUser.address.id) {
	                return $q.reject();
	            }
	            return Address.get({id : $scope.irisUser.address.id}).$promise;
	        }).then(function(address) {
	            $scope.addresss.push(address);
	        });
	        $scope.load = function(id) {
	            IrisUser.get({id : id}, function(result) {
	                $scope.irisUser = result;
	            });
	        };

	        var onSaveSuccess = function (result) {
	            $scope.$emit('try1App:irisUserUpdate', result);
	            $uibModalInstance.close(result);
	            $scope.isSaving = false;
	        };

	        var onSaveError = function (result) {
	            $scope.isSaving = false;
	        };

	        $scope.save = function () {
	            $scope.isSaving = true;
	            if ($scope.irisUser.id != null) {
	                IrisUser.update($scope.irisUser, onSaveSuccess, onSaveError);
	            } else {
	                IrisUser.save($scope.irisUser, onSaveSuccess, onSaveError);
	            }
	        };

	        $scope.clear = function() {
	            $uibModalInstance.dismiss('cancel');
	        };
	        $scope.datePickerForDateOfBirth = {};

	        $scope.datePickerForDateOfBirth.status = {
	            opened: false
	        };

	        $scope.datePickerForDateOfBirthOpen = function($event) {
	            $scope.datePickerForDateOfBirth.status.opened = true;
	        };
	}]);