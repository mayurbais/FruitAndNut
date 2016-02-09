'use strict';

angular.module('try1App')
    .controller('DairyDescriptionDetailController', function ($scope, $rootScope, $stateParams, entity, DairyDescription, SchoolDetails) {
        $scope.dairyDescription = entity;
        $scope.load = function (id) {
            DairyDescription.get({id: id}, function(result) {
                $scope.dairyDescription = result;
            });
        };
        var unsubscribe = $rootScope.$on('try1App:dairyDescriptionUpdate', function(event, result) {
            $scope.dairyDescription = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
