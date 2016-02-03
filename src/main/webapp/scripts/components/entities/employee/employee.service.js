'use strict';

angular.module('try1App')
    .factory('Employee', function ($resource, DateUtils) {
        return $resource('api/employees/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    data.leaveFrom = DateUtils.convertDateTimeFromServer(data.leaveFrom);
                    data.leaveTill = DateUtils.convertDateTimeFromServer(data.leaveTill);
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    });
