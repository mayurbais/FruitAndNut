'use strict';

angular.module('try1App')
    .controller('GradingLevelController', function ($scope, $state, GradingLevel) {

        $scope.gradingLevels = [];
        $scope.loadAll = function() {
            GradingLevel.query(function(result) {
               $scope.gradingLevels = result;
            });
        };
        $scope.loadAll();


        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.gradingLevel = {
                name: null,
                minScore: null,
                description: null,
                id: null
            };
        };
    });
