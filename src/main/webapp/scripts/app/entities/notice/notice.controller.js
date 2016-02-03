'use strict';

angular.module('try1App')
    .controller('NoticeController', function ($scope, $state, Notice) {

        $scope.notices = [];
        $scope.loadAll = function() {
            Notice.query(function(result) {
               $scope.notices = result;
            });
        };
        $scope.loadAll();


        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.notice = {
                noticeType: null,
                priority: null,
                sensitivity: null,
                sendDate: null,
                isRead: null,
                subject: null,
                sentBy: null,
                sentTo: null,
                message: null,
                id: null
            };
        };
    });
