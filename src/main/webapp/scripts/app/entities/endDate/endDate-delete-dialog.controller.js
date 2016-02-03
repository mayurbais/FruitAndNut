'use strict';

angular.module('try1App')
	.controller('EndDateDeleteController', function($scope, $uibModalInstance, entity, EndDate) {

        $scope.endDate = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            EndDate.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
