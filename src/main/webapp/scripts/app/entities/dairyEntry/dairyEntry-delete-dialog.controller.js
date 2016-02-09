'use strict';

angular.module('try1App')
	.controller('DairyEntryDeleteController', function($scope, $uibModalInstance, entity, DairyEntry) {

        $scope.dairyEntry = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            DairyEntry.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
