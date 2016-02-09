'use strict';

angular.module('try1App').controller('BusDetailsDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'BusDetails', 'SchoolDetails', 'Student',
        function($scope, $stateParams, $uibModalInstance, $q, entity, BusDetails, SchoolDetails, Student) {

        $scope.busDetails = entity;
        $scope.schooldetailss = SchoolDetails.query({filter: 'busdetails-is-null'});
        $q.all([$scope.busDetails.$promise, $scope.schooldetailss.$promise]).then(function() {
            if (!$scope.busDetails.schoolDetails || !$scope.busDetails.schoolDetails.id) {
                return $q.reject();
            }
            return SchoolDetails.get({id : $scope.busDetails.schoolDetails.id}).$promise;
        }).then(function(schoolDetails) {
            $scope.schooldetailss.push(schoolDetails);
        });
        $scope.students = Student.query();
        $scope.load = function(id) {
            BusDetails.get({id : id}, function(result) {
                $scope.busDetails = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('try1App:busDetailsUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.busDetails.id != null) {
                BusDetails.update($scope.busDetails, onSaveSuccess, onSaveError);
            } else {
                BusDetails.save($scope.busDetails, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
}]);
