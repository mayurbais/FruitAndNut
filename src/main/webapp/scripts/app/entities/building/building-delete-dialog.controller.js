'use strict';

angular.module('try1App')
	.controller('BuildingDeleteController', function($scope, $uibModalInstance, entity, Building) {

        $scope.building = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            Building.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
