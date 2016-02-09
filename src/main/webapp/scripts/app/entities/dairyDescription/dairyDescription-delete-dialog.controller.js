'use strict';

angular.module('try1App')
	.controller('DairyDescriptionDeleteController', function($scope, $uibModalInstance, entity, DairyDescription) {

        $scope.dairyDescription = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            DairyDescription.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
