'use strict';

angular.module('try1App')
	.controller('CurrancyLOVDeleteController', function($scope, $uibModalInstance, entity, CurrancyLOV) {

        $scope.currancyLOV = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            CurrancyLOV.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
