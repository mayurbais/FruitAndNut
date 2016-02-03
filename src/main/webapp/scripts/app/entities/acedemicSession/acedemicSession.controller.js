'use strict';

angular.module('try1App')
    .controller('AcedemicSessionController', function ($scope, $state, AcedemicSession) {

        $scope.acedemicSessions = [];
        $scope.loadAll = function() {
            AcedemicSession.query(function(result) {
               $scope.acedemicSessions = result;
            });
        };
        $scope.loadAll();


        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.acedemicSession = {
                sessionStartDate: null,
                sessionEndDate: null,
                id: null
            };
        };
    });
