'use strict';

angular.module('try1App')
    .controller('ParentController', function ($scope, $state, Parent) {

        $scope.parents = [];
        $scope.loadAll = function() {
            Parent.query(function(result) {
               $scope.parents = result;
            });
        };
        $scope.loadAll();


        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.parent = {
                occupation: null,
                id: null
            };
        };
    });
