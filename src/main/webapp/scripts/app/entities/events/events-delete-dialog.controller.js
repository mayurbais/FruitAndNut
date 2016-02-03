'use strict';

angular.module('try1App')
	.controller('EventsDeleteController', function($scope, $uibModalInstance, entity, Events) {

        $scope.events = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            Events.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
