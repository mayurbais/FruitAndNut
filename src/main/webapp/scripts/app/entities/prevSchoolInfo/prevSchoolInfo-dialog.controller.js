'use strict';

angular.module('try1App').controller('PrevSchoolInfoDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'PrevSchoolInfo',
        function($scope, $stateParams, $uibModalInstance, entity, PrevSchoolInfo) {

        $scope.prevSchoolInfo = entity;
        $scope.load = function(id) {
            PrevSchoolInfo.get({id : id}, function(result) {
                $scope.prevSchoolInfo = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('try1App:prevSchoolInfoUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.prevSchoolInfo.id != null) {
                PrevSchoolInfo.update($scope.prevSchoolInfo, onSaveSuccess, onSaveError);
            } else {
                PrevSchoolInfo.save($scope.prevSchoolInfo, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
}]);

angular.module('try1App').controller('AdmissionPrevSchoolInfoDialogController',
	    ['$scope', '$stateParams',   'PrevSchoolInfo',
	        function($scope, $stateParams,   PrevSchoolInfo) {
	    	
	        $scope.prevSchoolInfo =   function () {
                return {
                    schoolName: null,
                    grade: null,
                    remarkBy: null,
                    remark: null,
                    contactOfRemark: null,
                    reasonForChange: null,
                    id: null
                };
            };
	        $scope.load = function(id) {
	            PrevSchoolInfo.get({id : id}, function(result) {
	                $scope.prevSchoolInfo = result;
	            });
	        };

	        var onSaveSuccess = function (result) {
	            $scope.$emit('try1App:prevSchoolInfoUpdate', result);
	            $uibModalInstance.close(result);
	            $scope.isSaving = false;
	        };

	        var onSaveError = function (result) {
	            $scope.isSaving = false;
	        };

	        $scope.save = function () {
	            $scope.isSaving = true;
	            if ($scope.prevSchoolInfo.id != null) {
	                PrevSchoolInfo.update($scope.prevSchoolInfo, onSaveSuccess, onSaveError);
	            } else {
	                PrevSchoolInfo.save($scope.prevSchoolInfo, onSaveSuccess, onSaveError);
	            }
	        };

	        $scope.clear = function() {
	            $uibModalInstance.dismiss('cancel');
	        };
	}]);
