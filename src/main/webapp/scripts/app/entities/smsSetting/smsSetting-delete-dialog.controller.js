'use strict';

angular.module('try1App')
	.controller('SmsSettingDeleteController', function($scope, $uibModalInstance, entity, SmsSetting) {

        $scope.smsSetting = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            SmsSetting.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
