'use strict';

angular.module('try1App').controller('AdmissionDetailsDialogController',
    ['$scope', '$stateParams',  '$q',  'AdmissionDetails', 'Student', 'PrevSchoolInfo',
        function($scope, $stateParams,  $q,  AdmissionDetails, Student, PrevSchoolInfo) {

        $scope.admissionDetails =  function () {
            return {
                admissionNo: null,
                admissionDate: null,
                id: null
            };
        };
        $scope.students = Student.query({filter: 'admissiondetails-is-null'});
        $q.all([$scope.admissionDetails.$promise, $scope.students.$promise]).then(function() {
            if (!$scope.admissionDetails.student || !$scope.admissionDetails.student.id) {
                return $q.reject();
            }
            return Student.get({id : $scope.admissionDetails.student.id}).$promise;
        }).then(function(student) {
            $scope.students.push(student);
        });
        $scope.prevschooldetails = PrevSchoolInfo.query({filter: 'admissiondetails-is-null'});
        $q.all([$scope.admissionDetails.$promise, $scope.prevschooldetails.$promise]).then(function() {
            if (!$scope.admissionDetails.prevSchoolDetail || !$scope.admissionDetails.prevSchoolDetail.id) {
                return $q.reject();
            }
            return PrevSchoolInfo.get({id : $scope.admissionDetails.prevSchoolDetail.id}).$promise;
        }).then(function(prevSchoolDetail) {
            $scope.prevschooldetails.push(prevSchoolDetail);
        });
        $scope.load = function(id) {
            AdmissionDetails.get({id : id}, function(result) {
                $scope.admissionDetails = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('try1App:admissionDetailsUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.admissionDetails.id != null) {
                AdmissionDetails.update($scope.admissionDetails, onSaveSuccess, onSaveError);
            } else {
                AdmissionDetails.save($scope.admissionDetails, onSaveSuccess, onSaveError);
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
