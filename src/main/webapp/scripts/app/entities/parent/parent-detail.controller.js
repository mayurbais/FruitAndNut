'use strict';

angular.module('try1App')
    .controller('ParentDetailController', function ($scope, $rootScope, $stateParams, entity, Parent, IrisUser) {
        $scope.parent = entity;
        $scope.load = function (id) {
            Parent.get({id: id}, function(result) {
                $scope.parent = result;
            });
        };
        var unsubscribe = $rootScope.$on('try1App:parentUpdate', function(event, result) {
            $scope.parent = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
