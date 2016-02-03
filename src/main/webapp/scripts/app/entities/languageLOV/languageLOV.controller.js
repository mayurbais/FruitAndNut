'use strict';

angular.module('try1App')
    .controller('LanguageLOVController', function ($scope, $state, LanguageLOV) {

        $scope.languageLOVs = [];
        $scope.loadAll = function() {
            LanguageLOV.query(function(result) {
               $scope.languageLOVs = result;
            });
        };
        $scope.loadAll();


        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.languageLOV = {
                name: null,
                value: null,
                id: null
            };
        };
    });
