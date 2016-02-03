'use strict';

angular.module('try1App')
    .controller('CustomAdmissionDetailsController', function ($scope, $state, CustomAdmissionDetails) {

        $scope.customAdmissionDetailss = [];
        $scope.loadAll = function() {
            CustomAdmissionDetails.query(function(result) {
               $scope.customAdmissionDetailss = result;
            });
        };
        $scope.loadAll();


        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.customAdmissionDetails = {
                name: null,
                isActive: null,
                isMandatory: null,
                inputMethod: null,
                id: null
            };
        };
    });
