'use strict';

angular.module('try1App')
	.controller('ClassRoomSessionDeleteController', function($scope, $uibModalInstance, entity, ClassRoomSession) {

        $scope.classRoomSession = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            ClassRoomSession.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
