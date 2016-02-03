'use strict';

angular.module('try1App')
	.controller('NoticeDeleteController', function($scope, $uibModalInstance, entity, Notice) {

        $scope.notice = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            Notice.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
