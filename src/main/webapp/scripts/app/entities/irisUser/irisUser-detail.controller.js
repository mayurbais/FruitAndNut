'use strict';

angular.module('try1App')
    .controller('IrisUserDetailController', function ($scope, $rootScope, $stateParams, entity, IrisUser, User, LanguageLOV, CountryLOV, Address) {
        $scope.irisUser = entity;
        $scope.load = function (id) {
            IrisUser.get({id: id}, function(result) {
                $scope.irisUser = result;
            });
        };
        var unsubscribe = $rootScope.$on('try1App:irisUserUpdate', function(event, result) {
            $scope.irisUser = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
