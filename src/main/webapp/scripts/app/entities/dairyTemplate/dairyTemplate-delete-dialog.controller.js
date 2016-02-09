'use strict';

angular.module('try1App')
	.controller('DairyTemplateDeleteController', function($scope, $uibModalInstance, entity, DairyTemplate) {

        $scope.dairyTemplate = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            DairyTemplate.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
