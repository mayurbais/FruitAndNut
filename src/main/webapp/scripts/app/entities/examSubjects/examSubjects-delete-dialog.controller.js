'use strict';

angular.module('try1App')
	.controller('ExamSubjectsDeleteController', function($scope, $uibModalInstance, entity, ExamSubjects) {

        $scope.examSubjects = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            ExamSubjects.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
