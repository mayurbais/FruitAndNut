'use strict';

angular.module('try1App')
	.controller('BusDetailsDeleteController', function($scope, $uibModalInstance, entity, BusDetails) {

        $scope.busDetails = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            BusDetails.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
