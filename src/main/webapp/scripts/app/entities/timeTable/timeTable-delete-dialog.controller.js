'use strict';

angular.module('try1App')
	.controller('TimeTableDeleteController', function($scope, $uibModalInstance, entity, TimeTable) {

        $scope.timeTable = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            TimeTable.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
