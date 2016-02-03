'use strict';

angular.module('try1App')
	.controller('LanguageLOVDeleteController', function($scope, $uibModalInstance, entity, LanguageLOV) {

        $scope.languageLOV = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            LanguageLOV.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
