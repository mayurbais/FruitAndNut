'use strict';

angular.module('try1App')
	.controller('AttendanceDeleteController', function($scope, $uibModalInstance, entity, Attendance) {

        $scope.attendance = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            Attendance.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
