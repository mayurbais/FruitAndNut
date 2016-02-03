'use strict';

angular.module('try1App')
	.controller('StudentDeleteController', function($scope, $uibModalInstance, entity, Student) {

        $scope.student = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            Student.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
