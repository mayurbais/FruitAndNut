'use strict';

angular.module('try1App')
    .controller('CustomAdmissionDetailsDetailController', function ($scope, $rootScope, $stateParams, entity, CustomAdmissionDetails, SchoolDetails) {
        $scope.customAdmissionDetails = entity;
        $scope.load = function (id) {
            CustomAdmissionDetails.get({id: id}, function(result) {
                $scope.customAdmissionDetails = result;
            });
        };
        var unsubscribe = $rootScope.$on('try1App:customAdmissionDetailsUpdate', function(event, result) {
            $scope.customAdmissionDetails = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
