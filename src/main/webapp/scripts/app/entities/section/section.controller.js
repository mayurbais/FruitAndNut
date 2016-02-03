'use strict';

angular.module('try1App')
    .controller('SectionController', function ($scope, $state, Section) {

        $scope.sections = [];
        $scope.loadAll = function() {
            Section.query(function(result) {
               $scope.sections = result;
            });
        };
        $scope.loadAll();


        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.section = {
                name: null,
                code: null,
                strength: null,
                id: null
            };
        };
    });
