'use strict';

angular.module('try1App')
    .controller('LanguageLOVDetailController', function ($scope, $rootScope, $stateParams, entity, LanguageLOV) {
        $scope.languageLOV = entity;
        $scope.load = function (id) {
            LanguageLOV.get({id: id}, function(result) {
                $scope.languageLOV = result;
            });
        };
        var unsubscribe = $rootScope.$on('try1App:languageLOVUpdate', function(event, result) {
            $scope.languageLOV = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
