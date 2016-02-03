'use strict';

angular.module('try1App')
    .controller('RankingLevelController', function ($scope, $state, RankingLevel) {

        $scope.rankingLevels = [];
        $scope.loadAll = function() {
            RankingLevel.query(function(result) {
               $scope.rankingLevels = result;
            });
        };
        $scope.loadAll();


        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.rankingLevel = {
                name: null,
                percentageLimit: null,
                description: null,
                id: null
            };
        };
    });
