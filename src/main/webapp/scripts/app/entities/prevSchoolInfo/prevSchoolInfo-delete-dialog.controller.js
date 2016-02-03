'use strict';

angular.module('try1App')
	.controller('PrevSchoolInfoDeleteController', function($scope, $uibModalInstance, entity, PrevSchoolInfo) {

        $scope.prevSchoolInfo = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            PrevSchoolInfo.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
