'use strict';

angular.module('try1App')
	.controller('CustomAdmissionDetailsDeleteController', function($scope, $uibModalInstance, entity, CustomAdmissionDetails) {

        $scope.customAdmissionDetails = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            CustomAdmissionDetails.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
