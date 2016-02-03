'use strict';

angular.module('try1App').controller('ParentDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'Parent', 'IrisUser',
        function($scope, $stateParams, $uibModalInstance, $q, entity, Parent, IrisUser) {

        $scope.parent = entity;
        $scope.irisusers = IrisUser.query({filter: 'parent-is-null'});
        $q.all([$scope.parent.$promise, $scope.irisusers.$promise]).then(function() {
            if (!$scope.parent.irisUser || !$scope.parent.irisUser.id) {
                return $q.reject();
            }
            return IrisUser.get({id : $scope.parent.irisUser.id}).$promise;
        }).then(function(irisUser) {
            $scope.irisusers.push(irisUser);
        });
        $scope.load = function(id) {
            Parent.get({id : id}, function(result) {
                $scope.parent = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('try1App:parentUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.parent.id != null) {
                Parent.update($scope.parent, onSaveSuccess, onSaveError);
            } else {
                Parent.save($scope.parent, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
}]);

angular.module('try1App').controller('AdmissionParentDialogController',
	    ['$scope', '$stateParams',  '$q',  'Parent', 'IrisUser',
	        function($scope, $stateParams,  $q,  Parent, IrisUser) {

	        $scope.parent = function () {
                return {
                    occupation: null,
                    id: null
                };
            }
	        $scope.irisusers = IrisUser.query({filter: 'parent-is-null'});
	        $q.all([$scope.parent.$promise, $scope.irisusers.$promise]).then(function() {
	            if (!$scope.parent.irisUser || !$scope.parent.irisUser.id) {
	                return $q.reject();
	            }
	            return IrisUser.get({id : $scope.parent.irisUser.id}).$promise;
	        }).then(function(irisUser) {
	            $scope.irisusers.push(irisUser);
	        });
	        $scope.load = function(id) {
	            Parent.get({id : id}, function(result) {
	                $scope.parent = result;
	            });
	        };

	        var onSaveSuccess = function (result) {
	            $scope.$emit('try1App:parentUpdate', result);
	            $uibModalInstance.close(result);
	            $scope.isSaving = false;
	        };

	        var onSaveError = function (result) {
	            $scope.isSaving = false;
	        };

	        $scope.save = function () {
	            $scope.isSaving = true;
	            if ($scope.parent.id != null) {
	                Parent.update($scope.parent, onSaveSuccess, onSaveError);
	            } else {
	                Parent.save($scope.parent, onSaveSuccess, onSaveError);
	            }
	        };

	        $scope.clear = function() {
	            $uibModalInstance.dismiss('cancel');
	        };
	}])
