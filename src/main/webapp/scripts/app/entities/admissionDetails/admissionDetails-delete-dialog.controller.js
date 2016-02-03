'use strict';

angular.module('try1App')
	.controller('AdmissionDetailsDeleteController', function($scope, $uibModalInstance, entity, AdmissionDetails) {

        $scope.admissionDetails = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            AdmissionDetails.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
