'use strict';

angular.module('try1App')
	.controller('ExamDeleteController', function($scope, $uibModalInstance, entity, Exam) {

        $scope.exam = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            Exam.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
