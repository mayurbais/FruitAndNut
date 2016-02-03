'use strict';

angular.module('try1App')
    .controller('SessionController', function ($scope, $state, Session) {

        $scope.sessions = [];
        $scope.loadAll = function() {
            Session.query(function(result) {
               $scope.sessions = result;
            });
        };
        $scope.loadAll();


        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.session = {
                sessionName: null,
                startTime: null,
                endTime: null,
                isBreak: null,
                id: null
            };
        };
    });
