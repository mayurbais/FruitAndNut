'use strict';

angular.module('try1App')
    .controller('PrevSchoolInfoController', function ($scope, $state, PrevSchoolInfo) {

        $scope.prevSchoolInfos = [];
        $scope.loadAll = function() {
            PrevSchoolInfo.query(function(result) {
               $scope.prevSchoolInfos = result;
            });
        };
        $scope.loadAll();


        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.prevSchoolInfo = {
                schoolName: null,
                grade: null,
                remarkBy: null,
                remark: null,
                contactOfRemark: null,
                reasonForChange: null,
                id: null
            };
        };
    });
