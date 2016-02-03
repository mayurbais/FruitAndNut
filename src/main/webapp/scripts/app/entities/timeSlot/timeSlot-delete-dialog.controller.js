'use strict';

angular.module('try1App')
	.controller('TimeSlotDeleteController', function($scope, $uibModalInstance, entity, TimeSlot) {

        $scope.timeSlot = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            TimeSlot.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
