'use strict';

angular.module('try1App')
    .controller('PrevSchoolInfoDetailController', function ($scope, $rootScope, $stateParams, entity, PrevSchoolInfo) {
        $scope.prevSchoolInfo = entity;
        $scope.load = function (id) {
            PrevSchoolInfo.get({id: id}, function(result) {
                $scope.prevSchoolInfo = result;
            });
        };
        var unsubscribe = $rootScope.$on('try1App:prevSchoolInfoUpdate', function(event, result) {
            $scope.prevSchoolInfo = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
