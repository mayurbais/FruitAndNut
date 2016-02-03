'use strict';

angular.module('try1App')
	.controller('ElectiveSubjectDeleteController', function($scope, $uibModalInstance, entity, ElectiveSubject) {

        $scope.electiveSubject = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            ElectiveSubject.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
