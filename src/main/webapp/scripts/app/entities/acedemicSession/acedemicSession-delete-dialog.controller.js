'use strict';

angular.module('try1App')
	.controller('AcedemicSessionDeleteController', function($scope, $uibModalInstance, entity, AcedemicSession) {

        $scope.acedemicSession = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            AcedemicSession.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
