'use strict';

angular.module('try1App')
	.controller('IrisUserDeleteController', function($scope, $uibModalInstance, entity, IrisUser) {

        $scope.irisUser = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            IrisUser.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
