'use strict';

angular.module('try1App')
    .controller('StudentCategoryDetailController', function ($scope, $rootScope, $stateParams, entity, StudentCategory) {
        $scope.studentCategory = entity;
        $scope.load = function (id) {
            StudentCategory.get({id: id}, function(result) {
                $scope.studentCategory = result;
            });
        };
        var unsubscribe = $rootScope.$on('try1App:studentCategoryUpdate', function(event, result) {
            $scope.studentCategory = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
