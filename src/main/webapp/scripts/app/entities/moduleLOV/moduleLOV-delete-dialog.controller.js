'use strict';

angular.module('try1App')
	.controller('ModuleLOVDeleteController', function($scope, $uibModalInstance, entity, ModuleLOV) {

        $scope.moduleLOV = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            ModuleLOV.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
