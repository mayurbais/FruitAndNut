'use strict';

angular.module('try1App').controller('SmsSettingDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'SmsSetting',
        function($scope, $stateParams, $uibModalInstance, entity, SmsSetting) {

        $scope.smsSetting = entity;
        $scope.load = function(id) {
            SmsSetting.get({id : id}, function(result) {
                $scope.smsSetting = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('try1App:smsSettingUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.smsSetting.id != null) {
                SmsSetting.update($scope.smsSetting, onSaveSuccess, onSaveError);
            } else {
                SmsSetting.save($scope.smsSetting, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
}]);
