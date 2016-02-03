'use strict';

angular.module('try1App')
	.controller('SubjectExamResultDeleteController', function($scope, $uibModalInstance, entity, SubjectExamResult) {

        $scope.subjectExamResult = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            SubjectExamResult.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
