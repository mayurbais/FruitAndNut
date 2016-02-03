'use strict';

angular.module('try1App')
	.controller('GradingLevelDeleteController', function($scope, $uibModalInstance, entity, GradingLevel) {

        $scope.gradingLevel = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            GradingLevel.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
