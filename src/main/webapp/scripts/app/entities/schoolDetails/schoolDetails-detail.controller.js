'use strict';

angular.module('try1App')
    .controller('SchoolDetailsDetailController', function ($scope, $rootScope, $stateParams, entity, SchoolDetails, User, LanguageLOV, CurrancyLOV, CountryLOV) {
        $scope.schoolDetails = entity;
        $scope.load = function (id) {
            SchoolDetails.get({id: id}, function(result) {
                $scope.schoolDetails = result;
            });
        };
        var unsubscribe = $rootScope.$on('try1App:schoolDetailsUpdate', function(event, result) {
            $scope.schoolDetails = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
