'use strict';

angular.module('try1App')
	.controller('ExamResultDeleteController', function($scope, $uibModalInstance, entity, ExamResult) {

        $scope.examResult = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            ExamResult.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
