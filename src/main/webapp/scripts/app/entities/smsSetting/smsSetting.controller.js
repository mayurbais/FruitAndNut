'use strict';

angular.module('try1App')
    .controller('SmsSettingController', function ($scope, $state, SmsSetting) {

        $scope.smsSettings = [];
        $scope.loadAll = function() {
            SmsSetting.query(function(result) {
               $scope.smsSettings = result;
            });
        };
        $scope.loadAll();


        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.smsSetting = {
                name: null,
                code: null,
                isEnabled: null,
                id: null
            };
        };
    });
