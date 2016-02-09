'use strict';

angular.module('try1App')
    .controller('DairyDescriptionController', function ($scope, $state, DairyDescription) {

        $scope.dairyDescriptions = [];
        $scope.loadAll = function() {
            DairyDescription.query(function(result) {
               $scope.dairyDescriptions = result;
            });
        };
        $scope.loadAll();


        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.dairyDescription = {
                rules: null,
                contactNoOfManagment: null,
                mission: null,
                objective: null,
                declaration: null,
                id: null
            };
        };
    });
