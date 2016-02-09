'use strict';

angular.module('try1App')
    .controller('YearlyDairyDescriptionController', function ($scope, $state, YearlyDairyDescription) {

        $scope.yearlyDairyDescriptions = [];
        $scope.loadAll = function() {
            YearlyDairyDescription.query(function(result) {
               $scope.yearlyDairyDescriptions = result;
            });
        };
        $scope.loadAll();


        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.yearlyDairyDescription = {
                year: null,
                theme: null,
                summerDressCode: null,
                winterDressCode: null,
                isEnabled: null,
                id: null
            };
        };
    });
