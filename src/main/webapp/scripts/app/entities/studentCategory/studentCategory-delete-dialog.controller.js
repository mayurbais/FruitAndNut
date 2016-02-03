'use strict';

angular.module('try1App')
	.controller('StudentCategoryDeleteController', function($scope, $uibModalInstance, entity, StudentCategory) {

        $scope.studentCategory = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            StudentCategory.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
