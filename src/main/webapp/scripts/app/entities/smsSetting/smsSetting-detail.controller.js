'use strict';

angular.module('try1App')
    .controller('SmsSettingDetailController', function ($scope, $rootScope, $stateParams, entity, SmsSetting) {
        $scope.smsSetting = entity;
        $scope.load = function (id) {
            SmsSetting.get({id: id}, function(result) {
                $scope.smsSetting = result;
            });
        };
        var unsubscribe = $rootScope.$on('try1App:smsSettingUpdate', function(event, result) {
            $scope.smsSetting = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
