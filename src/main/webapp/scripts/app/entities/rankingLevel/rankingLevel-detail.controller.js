'use strict';

angular.module('try1App')
    .controller('RankingLevelDetailController', function ($scope, $rootScope, $stateParams, entity, RankingLevel, Course) {
        $scope.rankingLevel = entity;
        $scope.load = function (id) {
            RankingLevel.get({id: id}, function(result) {
                $scope.rankingLevel = result;
            });
        };
        var unsubscribe = $rootScope.$on('try1App:rankingLevelUpdate', function(event, result) {
            $scope.rankingLevel = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
