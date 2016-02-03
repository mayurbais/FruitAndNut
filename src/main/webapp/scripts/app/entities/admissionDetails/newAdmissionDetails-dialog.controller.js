'use strict';

angular.module('try1App').controller('NewAdmissionDetailsDialogController',
    ['$scope', '$rootScope','$stateParams',  '$q',  'AdmissionDetails', 'Student', 'PrevSchoolInfo','Parent',
        function($scope, $rootScope, $stateParams,  $q,  AdmissionDetails, Student, PrevSchoolInfo,Parent) {
    	
        

        var onSaveSuccess = function (result) {
            $scope.$emit('try1App:admissionDetailsUpdate', result);
            $scope.isSaving = false;
        };
        
        var onParentSaveSuccess = function(result){
        	$scope.student.parents = [{id:result.id}]
        	Student.save($scope.student, onStudentSaveSuccess, onSaveError);
        }
        
        var onStudentSaveSuccess = function (result) {
        	$scope.student.id = result.id;
        	PrevSchoolInfo.save($scope.prevSchoolInfo,onPrevSchoolSaveSuccess,onSaveError);
        };
        
        var onPrevSchoolSaveSuccess = function (result) {
        	$scope.admissionDetails.student = $scope.student;
        	$scope.admissionDetails.prevSchoolDetail = result;
        	AdmissionDetails.save($scope.admissionDetails, onSaveSuccess, onSaveError);

        };
        
      
        
        $scope.parent={};
        $scope.student={};
        $scope.mayur={};
        $scope.prevSchoolInfo={};
        $scope.admissionDetails={};
        

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
          //  alert($scope.parent.occupation);
         //   alert($scope.parent.irisUser.firstName);
            
         //   alert($scope.student.rollNo);
            
        //    alert($scope.student.irisUser.firstName);
            
          //  alert($scope.admissionDetails.admissionNo);
            
          //  alert($scope.prevSchoolInfo.schoolName);
            
            $scope.newAdmissionObj = {};
            $scope.newAdmissionObj.parent = $scope.parent;
            $scope.newAdmissionObj.student = $scope.student;
            $scope.newAdmissionObj.prevSchoolInfo = $scope.prevSchoolInfo;
            $scope.newAdmissionObj.admissionDetails = $scope.admissionDetails;
            $scope.student.id = null;
            
            if ($scope.admissionDetails.id != null) {
                AdmissionDetails.update($scope.admissionDetails, onSaveSuccess, onSaveError);
            } else {
            	 Parent.save($scope.parent, onParentSaveSuccess, onSaveError);
            	
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.datePickerForAdmissionDate = {};

        $scope.datePickerForAdmissionDate.status = {
            opened: false
        };

        $scope.datePickerForAdmissionDateOpen = function($event) {
            $scope.datePickerForAdmissionDate.status.opened = true;
        };
}]);


angular.module('try1App').controller('newStudentController',
	    ['$scope', '$rootScope','$stateParams',  '$q',  'AdmissionDetails', 'Student', 'PrevSchoolInfo',
	        function($scope, $stateParams,  $rootScope , $q,  AdmissionDetails, Student, PrevSchoolInfo) {
	    	
	    	$rootScope.studentUser = $scope.irisUser={};
	    	
	    }]);