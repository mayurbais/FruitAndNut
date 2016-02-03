'use strict';

angular.module('try1App')
	.controller('SectionDeleteController', function($scope, $uibModalInstance, entity, Section) {

        $scope.section = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            Section.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
