'use strict';

angular.module('try1App')
	.controller('CountryLOVDeleteController', function($scope, $uibModalInstance, entity, CountryLOV) {

        $scope.countryLOV = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            CountryLOV.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
