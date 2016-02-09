'use strict';

angular.module('try1App')
    .controller('DairyTemplateController', function ($scope, $state, DairyTemplate) {

        $scope.dairyTemplates = [];
        $scope.loadAll = function() {
            DairyTemplate.query(function(result) {
               $scope.dairyTemplates = result;
            });
        };
        $scope.loadAll();


        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.dairyTemplate = {
                date: null,
                entryType: null,
                id: null
            };
        };
    });
