'use strict';

angular.module('try1App')
    .controller('AcedemicSessionDetailController', function ($scope, $rootScope, $stateParams, entity, AcedemicSession, SchoolDetails) {
        $scope.acedemicSession = entity;
        $scope.load = function (id) {
            AcedemicSession.get({id: id}, function(result) {
                $scope.acedemicSession = result;
            });
        };
        var unsubscribe = $rootScope.$on('try1App:acedemicSessionUpdate', function(event, result) {
            $scope.acedemicSession = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
