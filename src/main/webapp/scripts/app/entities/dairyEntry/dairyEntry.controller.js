'use strict';

angular.module('try1App')
    .controller('DairyEntryController', function ($scope, $state, DairyEntry) {

        $scope.dairyEntrys = [];
        $scope.loadAll = function() {
            DairyEntry.query(function(result) {
               $scope.dairyEntrys = result;
            });
        };
        $scope.loadAll();


        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.dairyEntry = {
                date: null,
                entryType: null,
                dairyDescription: null,
                isForAll: null,
                id: null
            };
        };
    });
