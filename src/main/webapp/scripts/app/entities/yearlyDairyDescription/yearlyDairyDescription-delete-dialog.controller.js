'use strict';

angular.module('try1App')
	.controller('YearlyDairyDescriptionDeleteController', function($scope, $uibModalInstance, entity, YearlyDairyDescription) {

        $scope.yearlyDairyDescription = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            YearlyDairyDescription.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
