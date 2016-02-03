'use strict';

angular.module('try1App')
    .controller('GradingLevelDetailController', function ($scope, $rootScope, $stateParams, entity, GradingLevel, Course) {
        $scope.gradingLevel = entity;
        $scope.load = function (id) {
            GradingLevel.get({id: id}, function(result) {
                $scope.gradingLevel = result;
            });
        };
        var unsubscribe = $rootScope.$on('try1App:gradingLevelUpdate', function(event, result) {
            $scope.gradingLevel = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
