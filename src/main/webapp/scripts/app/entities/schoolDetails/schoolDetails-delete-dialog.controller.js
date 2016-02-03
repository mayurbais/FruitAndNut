'use strict';

angular.module('try1App')
	.controller('SchoolDetailsDeleteController', function($scope, $uibModalInstance, entity, SchoolDetails) {

        $scope.schoolDetails = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            SchoolDetails.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
