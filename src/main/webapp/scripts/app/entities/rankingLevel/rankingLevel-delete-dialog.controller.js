'use strict';

angular.module('try1App')
	.controller('RankingLevelDeleteController', function($scope, $uibModalInstance, entity, RankingLevel) {

        $scope.rankingLevel = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            RankingLevel.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
