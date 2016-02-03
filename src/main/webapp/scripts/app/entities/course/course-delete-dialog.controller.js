'use strict';

angular.module('try1App')
	.controller('CourseDeleteController', function($scope, $uibModalInstance, entity, Course) {

        $scope.course = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            Course.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
